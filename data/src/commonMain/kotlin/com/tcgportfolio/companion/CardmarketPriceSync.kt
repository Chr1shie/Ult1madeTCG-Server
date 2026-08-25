package com.tcgportfolio.companion

import com.tcgportfolio.companion.db.CardCatalogEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// Cardmarket-Preisabgleich (28.07., Nutzer-Vorgabe) - Cardmarket macht seit
// 05.06.2024 Preisverzeichnis + Produktkatalog für alle Spiele öffentlich
// downloadbar (Ersatz für den seitdem abgeschafften API-Endpunkt, siehe
// CONCEPT.md "Offene Fragen"). Läuft über öffentliche, unauthentifizierte
// S3-Downloads (verifiziert: kein Bot-Schutz, direkter curl funktioniert),
// deshalb komplett ohne eigenen Server möglich - App und Server rufen
// unabhängig voneinander dieselbe Logik auf, wie schon beim Katalog-Seeding.
//
// Zunächst nur Dragon Ball Fusion World eingebaut und mit echten Downloads
// verifiziert (game-id 13, "wir testen das jetzt wie immer mit Dragonball").
// Ab 30./31.07. auf alle anderen TCGs der App ausgeweitet (Nutzer hat die
// game-ids aller Cardmarket-Preisverzeichnisse geliefert) - siehe
// CARDMARKET_GAME_IDS unten. Weiterhin nur Einzelkarten, kein Vault/Sealed.

@Serializable
data class CardmarketPriceEntry(
    val idProduct: Long,
    val idCategory: Long,
    val avg: Double? = null,
    val low: Double? = null,
    val trend: Double? = null
)

@Serializable
data class CardmarketPriceGuideFile(
    val version: Int,
    val createdAt: String,
    val priceGuides: List<CardmarketPriceEntry>
)

@Serializable
data class CardmarketProductEntry(
    val idProduct: Long,
    val name: String,
    val idCategory: Long,
    val categoryName: String,
    val idExpansion: Long,
    val idMetacard: Long,
    val dateAdded: String
)

@Serializable
data class CardmarketProductCatalogFile(
    val version: Int,
    val createdAt: String,
    val products: List<CardmarketProductEntry>
)

data class CardmarketSyncResult(
    val matched: Int,
    val unmatched: Int,
    val cardmarketCardsSeen: Int
)

private const val CARDMARKET_DBFW_GAME_ID = 13
private const val ONE_DAY_MILLIS = 24L * 60 * 60 * 1000

// Cardmarket-game-ids für alle TCGs der App (30./31.07., vom Nutzer geliefert).
// Reihenfolge = Verarbeitungsreihenfolge in refreshCardmarketPricesIfStale().
internal val CARDMARKET_GAME_IDS: Map<String, Int> = linkedMapOf(
    "DBFW" to CARDMARKET_DBFW_GAME_ID,
    "OnePiece" to 18,
    "Digimon" to 17,
    "FinalFantasy" to 9,
    "MTG" to 1,
    "Pokemon" to 6,
    "YuGiOh" to 3,
    "Riftbound" to 22,
    "Lorcana" to 19,
    "FleshAndBlood" to 16,
    // Star Wars: Unlimited (13.08.) - game-id 21, live über die Produktliste
    // ermittelt (categoryName "Star Wars Unlimited Single"/"...Display"/...
    // enthielt den Klarnamen, siehe StarWarsUnlimitedSealedCatalog.kt-
    // Kommentar). Altered bewusst NOCH nicht hier drin - id nicht ermittelt.
    "StarWarsUnlimited" to 21
)

// Öffentlicher Zugriff für :shared (04.08., manuelle Cardmarket-Zuordnung,
// siehe App.kt) - CARDMARKET_GAME_IDS selbst bleibt internal, das ist die
// einzige Stelle außerhalb dieses Moduls, die die game-id kennen muss
fun cardmarketGameIdFor(game: String): Int? = CARDMARKET_GAME_IDS[game]

// Extrahiert den eingebetteten Kartencode ("FB05-010", "FS01-01", ...) aus
// dem Cardmarket-Produktnamen - nur für als Fusion World markierte Einträge,
// Cardmarket führt die alte "Dragon Ball Super Card Game" und Fusion World
// unter derselben Kategorie, unterscheidbar nur über diesen Namenszusatz.
private val cardCodeRegex = Regex("""\(([A-Z]{2,3}\d{2}-\d{2,4})\)""")

internal fun extractFusionWorldCardCode(cardmarketName: String): String? {
    if ("[Fusion World]" !in cardmarketName) return null
    return cardCodeRegex.find(cardmarketName)?.groupValues?.get(1)
}

// Generischer Kartencode am Namensende in Klammern, z.B. "Roronoa Zoro
// (OP01-001)" (OnePiece), "Yokomon (BT1-001)" (Digimon), "Auron (1-001)"
// (FinalFantasy) - anders als bei DBFW gibt es hier keinen zweiten
// Kartenspiel-Zusatz unter derselben game-id, der ausgefiltert werden müsste.
private val trailingParenCodeRegex = Regex("""\(([A-Za-z0-9\-/]+)\)\s*$""")

internal fun extractTrailingParenCode(cardmarketName: String): String? =
    trailingParenCodeRegex.find(cardmarketName)?.groupValues?.get(1)

// FinalFantasy: unsere eigene number-Spalte hängt einen Rarity-Buchstaben an
// ("26-083R"), den Cardmarkets Code nicht kennt ("26-083") - und Nachdrucke
// tragen bei uns einen Doppel-Code ("PR-010/1-108"), von dem meist nur die
// zweite Hälfte bei Cardmarket als Code auftaucht. Erst dieser
// Buchstaben-Abschnitt entfernen, dann beide "/"-Hälften einzeln probieren
// (verifiziert an echten Downloads: 98,3% Trefferquote statt 0,5% ohne
// diese beiden Schritte, siehe CONCEPT.md).
private val trailingLettersRegex = Regex("""[A-Za-z]+$""")

private fun stripTrailingLetters(code: String): String = trailingLettersRegex.replace(code, "")

// Generische Namensbereinigung für alle TCGs ohne eingebetteten Kartencode
// im Cardmarket-Namen (MTG, Pokemon, YuGiOh, Riftbound, Lorcana,
// FleshAndBlood) - Zuordnung läuft dort über den (bereinigten) Kartennamen
// statt über einen Code. Entfernt bei uns übliche Zusätze, die Cardmarkets
// Namen nicht kennt: angehängte Sammelnummer ("Meganium - 001" bei Pokemon),
// eckige Klammern (unsere "[Staff]"-Zusätze GENAUSO wie Cardmarkets
// "[Movename]"-Zusätze bei Pokemon) und runde Klammern (z.B. unser
// "(Pokemon Center Exclusive)"). Auf beide Seiten (unser Katalog UND
// Cardmarkets Produktname) angewendet - an echten Downloads verifiziert,
// dass das die Trefferquote deutlich verbessert (z.B. YuGiOh 77,8% -> 97,7%,
// Lorcana 85,3% -> 97,5%, siehe CONCEPT.md).
private val trailingNumberSuffixRegex = Regex("""\s*-\s*\d+[A-Za-z]*\s*$""")
private val trailingBracketRegex = Regex("""\s*\[[^]]*]\s*$""")
private val trailingParenRegex = Regex("""\s*\([^)]*\)\s*$""")

internal fun cleanCardName(name: String): String {
    var n = trailingNumberSuffixRegex.replace(name, "")
    n = trailingBracketRegex.replace(n, "")
    n = trailingParenRegex.replace(n, "")
    return n.trim()
}

// Der "Join-Key", unter dem Cardmarket-Preise für ein Produkt gepoolt werden
// - pro Spiel unterschiedlich, siehe applyCardmarketCardPrices() in
// PortfolioRepository.kt für die Verwendung.
internal fun extractCardmarketMatchKey(game: String, cardmarketName: String): String? = when (game) {
    "DBFW" -> extractFusionWorldCardCode(cardmarketName)
    "OnePiece", "Digimon", "FinalFantasy" -> extractTrailingParenCode(cardmarketName)
    else -> cleanCardName(cardmarketName).takeIf { it.isNotBlank() }
}

// Kandidaten-Join-Keys für eine unserer eigenen Katalogzeilen, in
// Prioritätsreihenfolge - der erste, der unter den gefundenen Cardmarket-
// Keys existiert, gewinnt (siehe applyCardmarketCardPrices()). Bei den
// meisten Spielen gibt es nur einen einzigen Kandidaten; nur FinalFantasy
// braucht wegen der Doppel-Codes bei Nachdrucken mehrere Versuche.
internal fun ourCatalogMatchKeyCandidates(game: String, number: String, name: String): List<String> = when (game) {
    "DBFW", "OnePiece", "Digimon" -> listOf(number)
    "FinalFantasy" -> (number.split("/").reversed().map { stripTrailingLetters(it) } + stripTrailingLetters(number)).distinct()
    else -> listOf(cleanCardName(name))
}

// Sealed-Produkt-Namensbereinigung (03.08., Nutzer-Fund "ich meine da stehen
// immer noch alte USD-Preise") - anders als bei Einzelkarten reicht hier EIN
// generischer Abgleich für alle Spiele: Sealed-Produktnamen sind praktisch
// immer eindeutig, keine Holo-/Alt-Art-Mehrdeutigkeit wie bei Karten (siehe
// applyCardmarketSealedPrices() in PortfolioRepository.kt, bewusst ohne die
// Options/Index-Komplexität von applyCardmarketCardPrices()).
//
// WICHTIG (Bugfix 03.08., beim ersten Live-Test gefunden): Klammer-Inhalte
// NICHT pauschal entfernen und NICHT einfach alle Ziffern wegwerfen - beides
// zerstört bei uns echte, unterscheidende Information ("Starter Deck 6: Son
// Goku (Mini)" wurde dadurch fälschlich auf denselben Schlüssel wie "Starter
// Deck 1: Son Goku" abgebildet und bekam dessen Preis zugewiesen, obwohl es
// ein anderes Produkt ist). Stattdessen nur eckige Klammer-TAGS entfernen
// (Cardmarkets "[Fusion World]") und GEZIELT das führende "<Deck/Pack> <Zahl>:"
// unserer eigenen Nummerierung auf Cardmarkets schlichteres "<Deck/Pack>:"
// zurückstutzen - alle anderen Ziffern/Klammerinhalte (z.B. "(Mini)") bleiben
// erhalten und unterscheiden weiterhin echte, verschiedene Produkte
// voneinander. An echten Downloads verifiziert (03.08.): "Starter Deck 1:
// Son Goku" -> "Starter Deck: Son Goku" (Cardmarket-kompatibel), "Starter
// Deck 6: Son Goku (Mini)" -> "Starter Deck: Son Goku (Mini)" (matcht
// Cardmarkets EIGENEN "(Mini)"-Eintrag, statt mit der Nicht-Mini-Version zu
// kollidieren).
private val sealedBracketTagRegex = Regex("""\[[^]]*]""")
private val sealedDeckOrdinalRegex = Regex("""(?i)\b(Deck|Pack)\s+\d+\s*:""")
private val nonAlphanumericRegex = Regex("""[^A-Za-z0-9]""")

internal fun cleanSealedProductName(name: String): String {
    var n = sealedBracketTagRegex.replace(name, " ")
    n = sealedDeckOrdinalRegex.replace(n) { m -> "${m.groupValues[1]}:" }
    return nonAlphanumericRegex.replace(n, "").lowercase()
}

// "(Non-English)"-Varianten bewusst ausschließen (03.08.) - sonst würde bei
// zwei Cardmarket-Einträgen mit identisch bereinigtem Namen (englische UND
// nicht-englische Ausgabe desselben Produkts) zufällig irgendeine der beiden
// Notierungen gewinnen, je nach JSON-Reihenfolge
private fun isNonEnglishSealedProduct(cardmarketName: String): Boolean = "(Non-English)" in cardmarketName

// DBFW-Sealed-Produkte laufen wie bei Einzelkarten unter derselben
// Cardmarket-game-id wie das alte "Dragon Ball Super Card Game" - nur die
// mit "[Fusion World]" markierten Einträge gehören zu uns, siehe
// extractFusionWorldCardCode() oben für dasselbe Prinzip bei Karten.
internal fun isRelevantSealedProduct(game: String, cardmarketName: String): Boolean {
    if (isNonEnglishSealedProduct(cardmarketName)) return false
    return if (game == "DBFW") "[Fusion World]" in cardmarketName else true
}

// Manuelle Zuordnung (04.08., Nutzer-Vorgabe "der User sieht nach und
// wählt aus") - Suchergebnis-Zeile für die "richtige Karte suchen"-UI. Live-
// Fetch bei jeder Suche statt Caching: die manuelle Zuordnung ist eine
// seltene, bewusste Einzelaktion pro Karte, kein Hintergrund-Vorgang wie
// refreshCardmarketPricesIfStale() - der zusätzliche Download lohnt sich
// hier nicht zu vermeiden.
data class CardmarketProductSearchResult(
    val idProduct: Long,
    val name: String,
    val priceEur: Double?
)

suspend fun searchCardmarketProducts(game: String, query: String, singles: Boolean = true): List<CardmarketProductSearchResult> {
    val gameId = CARDMARKET_GAME_IDS[game] ?: return emptyList()
    val trimmed = query.trim()
    if (trimmed.length < 2) return emptyList()
    val priceGuide = fetchCardmarketPriceGuide(gameId)
    val products = fetchCardmarketProductList(gameId, singles)
    val priceByProductId = priceGuide.priceGuides.associateBy { it.idProduct }
    return products.products
        .filter { it.name.contains(trimmed, ignoreCase = true) }
        .take(50)
        .map { p ->
            val price = priceByProductId[p.idProduct]
            val eur = price?.trend?.takeIf { it > 0 }
                ?: price?.avg?.takeIf { it > 0 }
                ?: price?.low?.takeIf { it > 0 }
            CardmarketProductSearchResult(p.idProduct, p.name, eur)
        }
}

// "Aus dem Set durchstöbern" (12.08., Nutzer-Vorgabe "man kann nur suchen
// und da findet man ja nichts, da es sonst die APP selbst gefunden hätte")
// - für Karten, bei denen weder der automatische Namens-/Code-Abgleich noch
// eine manuelle Textsuche etwas findet, hilft nur noch Durchstöbern statt
// Raten. Cardmarkets Produktliste hat zwar eine idExpansion pro Produkt,
// aber KEINEN Erweiterungs-NAMEN (weder in dieser Datei noch über sonst
// öffentlich erreichbare Endpunkte - eine cardmarket.com-Seite mit
// Erweiterungs-Namen blockt automatisierte Zugriffe, siehe Recherche
// 12.08.). Statt den Nutzer die richtige idExpansion raten zu lassen, wird
// sie AUTOMATISCH über eine ANDERE Karte aus demselben Set hergeleitet, die
// bereits einen Preis hat (also selbst schon einmal erfolgreich zugeordnet
// wurde) - Karten derselben physischen Veröffentlichung liegen praktisch
// immer in derselben Cardmarket-Erweiterung. Gibt es keine solche
// Schwesterkarte (z.B. brandneues Set, noch nichts zugeordnet), bleibt nur
// die Textsuche.
internal fun findSetExpansionId(
    game: String,
    setId: String,
    catalog: List<CardCatalogEntity>,
    products: CardmarketProductCatalogFile
): Long? {
    val siblingKeys = catalog
        .asSequence()
        .filter { it.setId == setId && it.marketPriceEur != null }
        .flatMap { ourCatalogMatchKeyCandidates(game, it.number, it.name).asSequence() }
        .toSet()
    if (siblingKeys.isEmpty()) return null
    return products.products.firstOrNull { p ->
        extractCardmarketMatchKey(game, p.name) in siblingKeys
    }?.idExpansion
}

suspend fun browseCardmarketProductsInSet(
    game: String,
    setId: String,
    catalog: List<CardCatalogEntity>,
    singles: Boolean = true
): List<CardmarketProductSearchResult> {
    val gameId = CARDMARKET_GAME_IDS[game] ?: return emptyList()
    val priceGuide = fetchCardmarketPriceGuide(gameId)
    val products = fetchCardmarketProductList(gameId, singles)
    val expansionId = findSetExpansionId(game, setId, catalog, products) ?: return emptyList()
    val priceByProductId = priceGuide.priceGuides.associateBy { it.idProduct }
    return products.products
        .filter { it.idExpansion == expansionId }
        .sortedBy { it.name }
        .take(300)
        .map { p ->
            val price = priceByProductId[p.idProduct]
            val eur = price?.trend?.takeIf { it > 0 }
                ?: price?.avg?.takeIf { it > 0 }
                ?: price?.low?.takeIf { it > 0 }
            CardmarketProductSearchResult(p.idProduct, p.name, eur)
        }
}

suspend fun fetchCardmarketPriceGuide(gameId: Int = CARDMARKET_DBFW_GAME_ID): CardmarketPriceGuideFile {
    val client = HttpClient { install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) } }
    try {
        return client.get("https://downloads.s3.cardmarket.com/productCatalog/priceGuide/price_guide_$gameId.json").body()
    } finally {
        client.close()
    }
}

suspend fun fetchCardmarketProductList(gameId: Int = CARDMARKET_DBFW_GAME_ID, singles: Boolean = true): CardmarketProductCatalogFile {
    val fileName = if (singles) "products_singles_$gameId.json" else "products_nonsingles_$gameId.json"
    val client = HttpClient { install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) } }
    try {
        return client.get("https://downloads.s3.cardmarket.com/productCatalog/productList/$fileName").body()
    } finally {
        client.close()
    }
}

// Läuft höchstens 1x pro Tag - "täglich" (Nutzer-Vorgabe), aber ohne echten
// Hintergrund-Job (WorkManager/BGTaskScheduler): einfach bei jedem
// App-/Server-Start prüfen, ob der letzte Abgleich schon 24h her ist. Fehler
// (Netzwerk, Cardmarket down, ...) bewusst nur best-effort pro Spiel - ein
// einzelnes fehlgeschlagenes TCG (Download-Fehler, Format-Änderung) darf
// weder die anderen TCGs blockieren noch App-/Server-Start zum Absturz
// bringen. Lädt inzwischen alle 10 TCGs (~100 MB in Summe) - auf dem Handy
// ggf. relevant fürs mobile Datenvolumen, aber unverändert bewusst NICHT
// awaited (siehe Aufrufer in App.kt/Main.kt), verzögert also nichts.
suspend fun refreshCardmarketPricesIfStale(repository: PortfolioRepository) {
    val lastUpdated = repository.getSetting("cardmarketPricesUpdatedAt")?.toLongOrNull() ?: 0L
    if (currentTimeMillis() - lastUpdated < ONE_DAY_MILLIS) return
    for ((game, gameId) in CARDMARKET_GAME_IDS) {
        // Das Preisverzeichnis (price_guide_$gameId.json) deckt Einzelkarten
        // UND Sealed-Produkte gemeinsam ab (ein idProduct-Namensraum) - EIN
        // Download für beide statt eines zweiten, redundanten
        val priceGuide = try {
            fetchCardmarketPriceGuide(gameId)
        } catch (e: Exception) {
            null
        }
        if (priceGuide != null) {
            try {
                val products = fetchCardmarketProductList(gameId)
                repository.applyCardmarketCardPrices(game, priceGuide, products)
            } catch (e: Exception) {
                // Best-effort, siehe Kommentar oben - der bisherige
                // marketPriceUsd-Fallback bleibt für dieses eine Spiel in
                // jedem Fall nutzbar
            }
            // Sealed-Produkte (03.08., Nutzer-Fund "ich meine da stehen
            // immer noch alte USD-Preise") - eigener, unabhängiger
            // try/catch, damit ein Fehlschlag hier nicht den (oben schon
            // erfolgreich gelaufenen) Einzelkarten-Abgleich zunichtemacht
            // oder umgekehrt
            try {
                val sealedProducts = fetchCardmarketProductList(gameId, singles = false)
                repository.applyCardmarketSealedPrices(game, priceGuide, sealedProducts)
            } catch (e: Exception) {
                // Best-effort, siehe Kommentar oben
            }
        }
    }
    repository.setSetting("cardmarketPricesUpdatedAt", currentTimeMillis().toString())
}
