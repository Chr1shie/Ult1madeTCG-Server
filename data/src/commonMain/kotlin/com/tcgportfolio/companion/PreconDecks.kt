package com.tcgportfolio.companion

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// Fertige Decks aus dem Vault ins Deckbau-Tool (08.09., 1.1, Nutzer-Wunsch
// "wenn man bereits Decks im Vault hat, direkt eines davon zum Deckbau
// übernehmen, nicht immer neu anlegen") - das Problem war, dass unser
// Sealed-Katalog nicht weiß, welche Karten in einem Deck stecken. Für
// MAGIC löst das MTGJSON (mtgjson.com, offene Daten): DeckList.json führt
// ~3.000 vorgefertigte Decks (Commander Decks, Starter, Challenger, Theme
// Decks ...), je Deck eine Datei mit commander/mainBoard als setCode+number
// -> exakt unsere Karten-Ids. Läuft zur Laufzeit übers Netz (die Listen
// sind zu groß, um sie einzukompilieren) - Zuordnung Produktname ->
// MTGJSON-Deckname per Normalisierung (matchProduct), bei mehreren
// Kandidaten entscheidet der Nutzer. Andere TCGs: keine offene
// Decklisten-Quelle bekannt (FFTCG-Starter nur als PDF beim Hersteller).

@Serializable
data class MtgJsonDeckRef(
    val code: String,
    val fileName: String,
    val name: String,
    val releaseDate: String? = null,
    val type: String = ""
)

@Serializable
private data class MtgJsonDeckListResponse(val data: List<MtgJsonDeckRef> = emptyList())

@Serializable
data class MtgJsonDeckCard(
    val name: String,
    val setCode: String,
    val number: String,
    val count: Int = 1,
    val isFoil: Boolean = false
)

@Serializable
data class MtgJsonDeck(
    val code: String = "",
    val name: String,
    val type: String = "",
    val commander: List<MtgJsonDeckCard> = emptyList(),
    val mainBoard: List<MtgJsonDeckCard> = emptyList(),
    val sideBoard: List<MtgJsonDeckCard> = emptyList()
)

@Serializable
private data class MtgJsonDeckResponse(val data: MtgJsonDeck)

object PreconDecks {
    private const val BASE = "https://mtgjson.com/api/v5"
    private const val UA = "Ult1madeTCG/1.1 (+https://ult1madetcg.com)"
    private val client by lazy {
        HttpClient { install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) } }
    }
    private var cachedList: List<MtgJsonDeckRef>? = null

    // Nur Produkttypen, die als fertiges Spieldeck verkauft werden
    private val deckTypes = setOf(
        "Commander Deck", "Starter Deck", "Arena Starter Deck", "Planeswalker Deck", "Challenger Deck",
        "Theme Deck", "Intro Pack", "Duel Deck", "Event Deck", "Brawl Deck", "Jumpstart", "Pioneer Challenger Deck",
        "Starter Commander Deck", "Welcome Deck", "Game Night", "Clash Pack", "Premium Deck"
    )

    suspend fun listDecks(): List<MtgJsonDeckRef> {
        cachedList?.let { return it }
        val list = client.get("$BASE/DeckList.json") { header("User-Agent", UA) }
            .body<MtgJsonDeckListResponse>().data
            .filter { it.type in deckTypes || it.type.endsWith("Deck") }
        cachedList = list
        return list
    }

    suspend fun fetchDeck(ref: MtgJsonDeckRef): MtgJsonDeck =
        client.get("$BASE/decks/${ref.fileName}.json") { header("User-Agent", UA) }.body<MtgJsonDeckResponse>().data

    private fun normalize(s: String): String = s.lowercase().replace(Regex("[^a-z0-9]"), "")

    // Kandidaten für ein Vault-Produkt, beste zuerst: Deckname muss im
    // Produktnamen stecken ("Commander 2018 Deck - Adaptive Enchantment" ->
    // "Adaptive Enchantment"); Jahreszahl im Produktnamen bevorzugt Decks
    // mit passendem Erscheinungsjahr; Commander-Produkte bevorzugen den Typ
    // "Commander Deck".
    fun matchProduct(productName: String, refs: List<MtgJsonDeckRef>): List<MtgJsonDeckRef> {
        val normProduct = normalize(productName)
        val year = Regex("(19|20)\\d{2}").find(productName)?.value
        val wantsCommander = productName.contains("commander", ignoreCase = true)
        return refs
            .filter { ref -> normalize(ref.name).length >= 4 && normalize(ref.name) in normProduct }
            .sortedWith(
                compareByDescending<MtgJsonDeckRef> { normalize(it.name).length }
                    .thenByDescending { year != null && it.releaseDate?.startsWith(year) == true }
                    .thenByDescending { wantsCommander && it.type.contains("Commander") }
                    .thenByDescending { it.releaseDate ?: "" }
            )
    }

    fun toImportLines(deck: MtgJsonDeck): List<ImportedCardLine> =
        deck.commander.map { c ->
            ImportedCardLine(name = c.name, setCode = c.setCode.lowercase(), number = c.number, quantity = c.count.toLong().coerceAtLeast(1), foil = c.isFoil, section = "commander")
        } + deck.mainBoard.map { c ->
            ImportedCardLine(name = c.name, setCode = c.setCode.lowercase(), number = c.number, quantity = c.count.toLong().coerceAtLeast(1), foil = c.isFoil)
        }
}
