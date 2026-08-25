package com.tcgportfolio.companion

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// Pokémon-Deckbau-Regel-Metadaten (02.08., Phase 3 Deckbuilding, Nutzer-
// Vorgabe "Pokémon als Pilot-Spiel") - siehe CONCEPT.md "Deckbuilding".
// Läuft über die kostenlose, unauthentifizierte api.pokemontcg.io/v2/cards -
// eine ZWEITE, unabhängige Quelle neben der Alt-Katalog-API (das ist eine reine
// Preis-API ohne Kartentyp-/Legalitäts-Daten, siehe CardmarketPriceSync.kt-
// Kommentar zum Unterschied). Bekanntes Risiko, bewusst in Kauf genommen
// (Nutzer-Entscheidung 02.08.): pokemontcg.io wird laut eigener Webseite
// Richtung "Scrydex" verlagert (kostenpflichtig, kein Gratis-Tarif mehr),
// deren /sets-Endpunkt lieferte beim Testen bereits HTTP 500. Nur /cards
// wurde verwendet und mehrfach live verifiziert (funktioniert weiterhin,
// Stand 02.08.). Was nicht zugeordnet werden kann, bleibt NULL -
// PokemonDeckRules.kt behandelt NULL als "keine Prüfung möglich", nicht als
// Regelverstoß.

@Serializable
data class PokemonLegalities(
    val standard: String? = null,
    val expanded: String? = null,
    val unlimited: String? = null
)

@Serializable
data class PokemonRuleCard(
    val name: String,
    val number: String,
    val supertype: String? = null,
    val subtypes: List<String> = emptyList(),
    val legalities: PokemonLegalities? = null
)

@Serializable
data class PokemonCardSearchResponse(
    val data: List<PokemonRuleCard> = emptyList(),
    val totalCount: Int = 0
)

private const val POKEMON_RULES_BASE_URL = "https://api.pokemontcg.io/v2/cards"

// Entfernt das Ären-Präfix ("SV05: ", "SWSH10: ", "SM: ", "XY: ", "ME02: ")
// aus unserem CardSet.name, um an pokemontcg.io's schlichteren set.name
// ("Temporal Forces" statt unserem "SV05: Temporal Forces") heranzukommen -
// an echten Sets verifiziert (z.B. "SV05: Temporal Forces" -> "Temporal
// Forces", "SWSH12: Silver Tempest" -> "Silver Tempest").
private val eraPrefixRegex = Regex("""^[A-Z]{2,5}\d{0,2}:\s*""")

internal fun stripEraPrefix(setName: String): String = eraPrefixRegex.replace(setName, "").trim()

// pokemontcg.io's number-Feld ist die reine Zahl ohne führende Nullen und
// ohne "/total"-Anhängsel (z.B. "1" statt unserem "001/162")
internal fun stripNumberForRulesMatch(number: String): String {
    val beforeSlash = number.substringBefore("/")
    return beforeSlash.trimStart('0').ifEmpty { "0" }
}

// Lädt alle Regel-Karten für EIN Set (paginiert, pokemontcg.io liefert
// maximal 250 pro Seite) - wird pro unserem eigenen CardSet aufgerufen,
// siehe refreshPokemonCardRulesIfStale() unten.
suspend fun fetchPokemonRuleCardsForSet(setName: String): List<PokemonRuleCard> {
    val client = HttpClient { install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) } }
    try {
        val cleanName = stripEraPrefix(setName)
        val allCards = mutableListOf<PokemonRuleCard>()
        var page = 1
        while (true) {
            val response: PokemonCardSearchResponse = client.get(POKEMON_RULES_BASE_URL) {
                url {
                    parameters.append("q", "set.name:\"$cleanName\"")
                    parameters.append("page", page.toString())
                    parameters.append("pageSize", "250")
                }
            }.body()
            if (response.data.isEmpty()) break
            allCards += response.data
            if (allCards.size >= response.totalCount) break
            page++
        }
        return allCards
    } finally {
        client.close()
    }
}

// Läuft höchstens 1x pro Monat (anders als der tägliche Cardmarket-
// Preisabgleich) - Kartentyp/Untertyp ändern sich nach dem Druck nie mehr,
// nur die Legalität (Standard-Rotation, Bans) ändert sich gelegentlich, ein
// paar Mal im Jahr. Seltenerer Abgleich schont außerdem die ohnehin schon
// wacklige kostenlose API (siehe Kommentar oben). Best-effort pro Set -
// ein einzelnes fehlgeschlagenes Set (Namens-Mismatch, Netzwerkfehler) darf
// die anderen nicht blockieren.
private const val THIRTY_DAYS_MILLIS = 30L * 24 * 60 * 60 * 1000

suspend fun refreshPokemonCardRulesIfStale(repository: PortfolioRepository) {
    val lastUpdated = repository.getSetting("pokemonRulesUpdatedAt")?.toLongOrNull() ?: 0L
    if (currentTimeMillis() - lastUpdated < THIRTY_DAYS_MILLIS) return
    val pokemonSets = repository.getCardSets().filter { it.game == "Pokemon" }
    for (set in pokemonSets) {
        try {
            val ruleCards = fetchPokemonRuleCardsForSet(set.name)
            if (ruleCards.isNotEmpty()) {
                repository.applyPokemonRuleMetadata(set.id, ruleCards)
            }
        } catch (e: Exception) {
            // Best-effort, siehe Kommentar oben
        }
    }
    repository.setSetting("pokemonRulesUpdatedAt", currentTimeMillis().toString())
}
