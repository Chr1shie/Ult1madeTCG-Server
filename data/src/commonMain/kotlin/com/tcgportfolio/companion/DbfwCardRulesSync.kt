package com.tcgportfolio.companion

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// DBFW-Deckbau-Regel-Metadaten (03.08., Phase 3 Deckbuilding, Nutzer-Vorgabe
// "auch für DBFW hinbekommen") - siehe CONCEPT.md "Deckbuilding". Anders als
// bei Pokémon (api.pokemontcg.io, pro Set abgefragt, siehe
// PokemonCardRulesSync.kt) kommt die Regel-Metadaten hier aus einem
// statischen, offenen GitHub-Datensatz
// (github.com/apitcg/dragon-ball-fusion-tcg-data, ein JSON-Export pro
// Set-Datei unter cards/en/) - kein API-Key, kein enges Rate-Limit im
// praktischen Sinn (läuft ohnehin höchstens 1x/30 Tage, siehe unten).
//
// Matching ist einfacher als bei Pokémon: unser CardCatalogEntity.number
// (z.B. "FB01-050") entspricht bereits deren "code"-Feld (bis auf den
// "-pN"-Parallel-Suffix bei Alt-Arts, der hier abgeschnitten wird) - keine
// Namens-/Set-Zuordnung nötig, EIN globaler Abgleich über alle Sets hinweg
// reicht (siehe applyDbfwRuleMetadata() in PortfolioRepository.kt).
//
// Bekannte Lücke, live geprüft (03.08.): das Repo führt nur die
// Haupt-Boosterset-Dateien fb01.json-fb06.json unter genau diesem
// Namensschema - unsere Sets FB07 aufwärts sowie unsere eigenen Alt-Art-/
// Release-Event-/Promo-Untersets (FBxx-AAR/-RE/-PR, FB-PROMO, FB-TOURN)
// bleiben unmatched (die Karten sind meist Reprints mit identischer
// Kartennummer wie im Hauptset - matchen dadurch teils trotzdem automatisch
// mit, aber nicht garantiert). Best-effort wie beim Pokémon-Abgleich:
// unmatched bleibt NULL, DbfwDeckRules.kt behandelt NULL als "keine Prüfung
// möglich", nie als Regelverstoß.
//
// Wiederverwendet bewusst dieselben CardCatalogEntity-Spalten wie Pokémon
// (ruleSupertype/ruleSubtypes) statt neuer DBFW-spezifischer Spalten -
// ruleSupertype trägt hier den cardType (LEADER/BATTLE/EXTRA), ruleSubtypes
// eine einelementige Liste mit der Farbe (leer bei "-"/farblos, siehe
// applyDbfwRuleMetadata()). ruleLegalStandard/-Expanded bleiben bei DBFW
// ungenutzt (NULL) - die Datenquelle liefert keine Formatlegalität.

@Serializable
data class DbfwRuleCard(
    val code: String,
    val color: String? = null,
    val cardType: String? = null
)

@Serializable
private data class GitHubDirEntry(
    val name: String,
    @SerialName("download_url") val downloadUrl: String? = null
)

private const val DBFW_RULES_DIR_URL = "https://api.github.com/repos/apitcg/dragon-ball-fusion-tcg-data/contents/cards/en"

private val dbfwParallelSuffixRegex = Regex("""-p\d+$""")

// "FB01-001-p1" -> "FB01-001" (Parallel-Drucke teilen sich denselben Karten-
// "code" wie der Basisdruck, siehe Kommentar oben)
internal fun stripDbfwParallelSuffix(code: String): String = dbfwParallelSuffixRegex.replace(code, "")

// Lädt und mergt ALLE verfügbaren Set-Dateien zu einer einzigen, globalen
// Zuordnung (Kartennummer -> Regelkarte) - best-effort pro Datei, eine
// einzelne fehlgeschlagene/unerwartete Datei darf die anderen nicht
// blockieren.
private val dbfwJson = Json { ignoreUnknownKeys = true }

suspend fun fetchAllDbfwRuleCards(): Map<String, DbfwRuleCard> {
    // Bewusst OHNE ContentNegotiation-Plugin, dafür manuelles bodyAsText() +
    // Json.decodeFromString() - sowohl api.github.com als auch
    // raw.githubusercontent.com liefern hier "text/plain" statt
    // "application/json" (live geprüft, 03.08.), was Ktors automatische
    // Content-Type-Erkennung sonst als "keine passende Transformation"
    // ablehnt (erster Versuch scheiterte genau daran, 0 Treffer trotz
    // erfolgreicher Anfragen).
    val client = HttpClient()
    try {
        // GitHubs REST-API (api.github.com) verlangt zwingend einen
        // User-Agent-Header, sonst 403 - live geprüft (03.08.). Ohne Header
        // schlug der Verzeichnis-Abruf still fehl (vom Best-effort-catch
        // unten geschluckt, 0 Treffer statt eines sichtbaren Fehlers) - der
        // Header hier behebt genau das. raw.githubusercontent.com (die
        // eigentlichen Set-Dateien) braucht ihn nicht, schadet aber nicht.
        val dirResponseText = client.get(DBFW_RULES_DIR_URL) {
            header("User-Agent", "Ult1madeTCG")
        }.bodyAsText()
        val entries: List<GitHubDirEntry> = dbfwJson.decodeFromString(dirResponseText)
        val result = mutableMapOf<String, DbfwRuleCard>()
        entries.forEach { entry ->
            val url = entry.downloadUrl ?: return@forEach
            try {
                val fileText = client.get(url) { header("User-Agent", "Ult1madeTCG") }.bodyAsText()
                val cards: List<DbfwRuleCard> = dbfwJson.decodeFromString(fileText)
                cards.forEach { card -> result[stripDbfwParallelSuffix(card.code)] = card }
            } catch (e: Exception) {
                // Best-effort pro Datei, siehe Kommentar oben
            }
        }
        return result
    } finally {
        client.close()
    }
}

// Läuft höchstens 1x/Monat, wie beim Pokémon-Regelabgleich (Kartentyp/Farbe
// ändern sich nach dem Druck nie mehr) - eigene Einstellung
// ("dbfwRulesUpdatedAt"), damit ein Fehlschlag bei einem Spiel den Abgleich
// des anderen nicht verzögert.
private const val DBFW_THIRTY_DAYS_MILLIS = 30L * 24 * 60 * 60 * 1000

suspend fun refreshDbfwCardRulesIfStale(repository: PortfolioRepository) {
    val lastUpdated = repository.getSetting("dbfwRulesUpdatedAt")?.toLongOrNull() ?: 0L
    if (currentTimeMillis() - lastUpdated < DBFW_THIRTY_DAYS_MILLIS) return
    try {
        val ruleCards = fetchAllDbfwRuleCards()
        if (ruleCards.isNotEmpty()) {
            repository.applyDbfwRuleMetadata(ruleCards)
        }
    } catch (e: Exception) {
        // Best-effort, siehe Kommentar oben
    }
    repository.setSetting("dbfwRulesUpdatedAt", currentTimeMillis().toString())
}
