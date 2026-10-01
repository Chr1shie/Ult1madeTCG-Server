package com.tcgportfolio.companion

import com.tcgportfolio.companion.data.tcgplayerGroupCategories
import com.tcgportfolio.companion.data.tcgplayerMappingChunks
import com.tcgportfolio.companion.data.tcgplayerSealedMappingChunks
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// TCGplayer-Preise für US-Nutzer (30.09., Nutzer-Vorgabe: "Über 70% ... kommen
// aus den USA. Die können mit Cardmarket-Preisen in Euro nichts anfangen" -
// aber "weder über meinen Server noch irgendwie bezahlen"). Quelle ist TCGCSV
// (tcgcsv.com), ein Hobby-Projekt, das TCGplayers Katalog- und Preisdaten
// täglich (~20:00 UTC) als öffentliches JSON spiegelt, pro TCGplayer-Gruppe
// (= Set) eine Preisdatei. Die Zuordnung unserer Karten zu TCGplayer-Produkten
// ist beim Bauen entstanden (tools/catalog/generate_tcgplayer_mapping.py,
// data/TcgplayerMapping.kt) - die App lädt nur noch die PREISE.
//
// Rücksicht auf den Hobby-Betreiber: nur wenn der Nutzer TCGplayer als
// Preisquelle gewählt hat, nur die Gruppen, in denen er tatsächlich Karten/
// Produkte hat (plus das gerade geöffnete Set), höchstens 1x pro Tag je
// Gruppe, nacheinander mit kurzer Pause, eigener User-Agent. Fällt TCGCSV weg,
// bleiben die Preise einfach leer - nichts geht kaputt.
//
// Die Preise landen in CardCatalogEntity/SealedCatalogEntity.marketPriceUsd
// (seit 37.sqm der Live-TCGplayer-Preis). Bilder von TCGplayer werden nie
// verwendet (harte Projektregel).

const val PRICE_SOURCE_SETTING_KEY = "priceSource"
const val PRICE_SOURCE_CARDMARKET = "cardmarket"
const val PRICE_SOURCE_TCGPLAYER = "tcgplayer"

private const val TCGCSV_BASE = "https://tcgcsv.com/tcgplayer"
private const val TCGCSV_USER_AGENT = "Ult1madeTCG/1.4 (+https://ult1madetcg.com)"
private const val TCGPLAYER_MAX_AGE_MILLIS = 20L * 60 * 60 * 1000

data class TcgplayerMappingEntry(val groupId: Int, val productId: Int, val subType: String)

object TcgplayerMapping {
    private fun parse(chunks: List<String>): Map<String, TcgplayerMappingEntry> {
        val map = HashMap<String, TcgplayerMappingEntry>()
        for (chunk in chunks) {
            for (line in chunk.lineSequence()) {
                val parts = line.split(' ')
                if (parts.size < 4) continue
                val gid = parts[1].toIntOrNull() ?: continue
                val pid = parts[2].toIntOrNull() ?: continue
                map[parts[0]] = TcgplayerMappingEntry(gid, pid, parts[3])
            }
        }
        return map
    }

    private val cards: Map<String, TcgplayerMappingEntry> by lazy { parse(tcgplayerMappingChunks) }
    private val sealed: Map<String, TcgplayerMappingEntry> by lazy { parse(tcgplayerSealedMappingChunks) }
    private val cardsByGroup: Map<Int, List<Pair<String, TcgplayerMappingEntry>>> by lazy {
        cards.entries.groupBy({ it.value.groupId }, { it.key to it.value })
    }
    private val sealedByGroup: Map<Int, List<Pair<String, TcgplayerMappingEntry>>> by lazy {
        sealed.entries.groupBy({ it.value.groupId }, { it.key to it.value })
    }
    val groupCategory: Map<Int, Int> by lazy {
        tcgplayerGroupCategories.split(' ').mapNotNull { pair ->
            val (g, c) = pair.split(':').takeIf { it.size == 2 } ?: return@mapNotNull null
            (g.toIntOrNull() ?: return@mapNotNull null) to (c.toIntOrNull() ?: return@mapNotNull null)
        }.toMap()
    }

    fun card(cardId: String): TcgplayerMappingEntry? = cards[cardId]
    fun sealed(sealedId: String): TcgplayerMappingEntry? = sealed[sealedId]
    fun cardsInGroup(groupId: Int) = cardsByGroup[groupId].orEmpty()
    fun sealedInGroup(groupId: Int) = sealedByGroup[groupId].orEmpty()
}

// Unterart-Kürzel aus der Zuordnung -> TCGplayers subTypeName
private val SUBTYPE_NAMES = mapOf(
    "N" to "Normal", "F" to "Foil", "H" to "Holofoil", "R" to "Reverse Holofoil",
    "1" to "1st Edition", "U" to "Unlimited", "L" to "Limited", "C" to "Cold Foil", "B" to "Rainbow Foil",
    "1N" to "1st Edition Normal", "1B" to "1st Edition Rainbow Foil", "1C" to "1st Edition Cold Foil",
    "1H" to "1st Edition Holofoil", "UN" to "Unlimited Edition Normal", "UB" to "Unlimited Edition Rainbow Foil",
    "UH" to "Unlimited Holofoil"
)
private fun isFoilSubtype(name: String) = "Foil" in name

@Serializable
data class TcgcsvPrice(
    val productId: Int,
    val marketPrice: Double? = null,
    val midPrice: Double? = null,
    val lowPrice: Double? = null,
    val subTypeName: String = "Normal"
)

@Serializable
private data class TcgcsvPriceResponse(val success: Boolean = true, val results: List<TcgcsvPrice> = emptyList())

// Preis einer Unterart: bevorzugt die zugeordnete, sonst eine gleichartige
// (Foil bleibt Foil, Normal bleibt nicht-Foil) - nie einfach der teurere
// Foil-Preis für eine normale Karte
fun pickTcgplayerPrice(prices: Map<String, Double>, wantedCode: String): Double? {
    val wanted = SUBTYPE_NAMES[wantedCode] ?: "Normal"
    prices[wanted]?.let { return it }
    val sameKind = prices.filterKeys { isFoilSubtype(it) == isFoilSubtype(wanted) }
    return sameKind["Normal"] ?: sameKind.values.minOrNull()
}

private fun TcgcsvPrice.best(): Double? = (marketPrice ?: midPrice)?.takeIf { it > 0.0 }

suspend fun fetchTcgplayerGroupPrices(categoryId: Int, groupId: Int): List<TcgcsvPrice> {
    val client = HttpClient { install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) } }
    try {
        return client.get("$TCGCSV_BASE/$categoryId/$groupId/prices") {
            header("User-Agent", TCGCSV_USER_AGENT)
        }.body<TcgcsvPriceResponse>().results
    } finally {
        client.close()
    }
}

// Holt die Preise der angegebenen Gruppen (bzw. aller Gruppen mit eigenen
// Karten/Produkten), sofern älter als ~1 Tag. onProgress(fertig, gesamt) für
// eine sichtbare Fortschrittsanzeige. Rückgabe: Anzahl aktualisierter Gruppen.
suspend fun refreshTcgplayerPrices(
    repository: PortfolioRepository,
    extraGroupIds: Set<Int> = emptySet(),
    onlyExtra: Boolean = false,
    force: Boolean = false,
    onProgress: ((done: Int, total: Int) -> Unit)? = null
): Int {
    if (repository.getSetting(PRICE_SOURCE_SETTING_KEY) != PRICE_SOURCE_TCGPLAYER) return 0
    val wanted = if (onlyExtra) extraGroupIds else repository.tcgplayerRelevantGroupIds() + extraGroupIds
    val fetched = repository.tcgplayerGroupFetchTimes()
    val now = currentTimeMillis()
    val due = wanted.filter { gid ->
        TcgplayerMapping.groupCategory[gid] != null && (force || now - (fetched[gid] ?: 0L) > TCGPLAYER_MAX_AGE_MILLIS)
    }
    var done = 0
    for (gid in due) {
        val category = TcgplayerMapping.groupCategory[gid] ?: continue
        val prices = runCatching { fetchTcgplayerGroupPrices(category, gid) }.getOrNull()
        if (prices != null) {
            val byProduct = HashMap<Int, MutableMap<String, Double>>()
            prices.forEach { p -> p.best()?.let { byProduct.getOrPut(p.productId) { HashMap() }[p.subTypeName] = it } }
            repository.applyTcgplayerGroupPrices(gid, byProduct)
        }
        done++
        onProgress?.invoke(done, due.size)
        delay(150)
    }
    return done
}

// Beim Wechsel der Preisquelle und für das gerade geöffnete Set - die
// Gruppen der Karten eines Sets
fun tcgplayerGroupIdsForCards(cardIds: Collection<String>): Set<Int> =
    cardIds.mapNotNull { TcgplayerMapping.card(it)?.groupId }.toSet()
