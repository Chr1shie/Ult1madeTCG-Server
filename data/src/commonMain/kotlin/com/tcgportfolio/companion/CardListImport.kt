package com.tcgportfolio.companion

import com.tcgportfolio.companion.db.CardCatalogEntity

// Import aus anderen Apps (08.09., 1.1, Nutzer-Frage "Können wir Backups aus
// z.B. ManaBox einspielen?"): zwei Formate, beide für Magic -
// 1. ManaBox-CSV (Sammlungs-Export der App): Kopfzeile mit u.a. Name, Set
//    code, Collector number, Foil, Quantity, Scryfall ID, Purchase price,
//    Language - Spalten werden per Kopfzeile gefunden, Reihenfolge egal.
// 2. Decklisten als Text (Arena/Moxfield/Archidekt-Stil): "4 Lightning Bolt
//    (MH3) 123", "1x Sol Ring", "Sol Ring" - mit optionalem Set/Nummer,
//    Abschnitts-Überschriften (Commander/Deck/Sideboard) werden erkannt,
//    der Sideboard-Abschnitt übersprungen.
// Zuordnung zum Katalog (resolve): Set+Nummer (unsere Karten-Id ist genau
// "<set>-<nummer>"), sonst Scryfall-Id (steckt im Dateinamen unserer
// Scryfall-Bild-URLs), sonst Name+Set, sonst Name (erstbester Druck).
// Was nicht zuzuordnen ist, wird gemeldet, nicht stillschweigend verworfen.

data class ImportedCardLine(
    val name: String,
    val setCode: String? = null,
    val number: String? = null,
    val quantity: Long = 1,
    val foil: Boolean = false,
    val purchasePrice: Double? = null,
    val scryfallId: String? = null,
    val language: String? = null,
    // Abschnitt aus Decklisten ("commander"/"main"/"sideboard")
    val section: String = "main"
)

data class ResolvedImportCard(
    val card: CardCatalogEntity,
    val line: ImportedCardLine
)

data class ResolvedImport(
    val matched: List<ResolvedImportCard>,
    val unmatched: List<ImportedCardLine>
) {
    val matchedQuantity: Long get() = matched.sumOf { it.line.quantity }
}

enum class CardListFormat { MANABOX_CSV, DECK_LIST_TEXT }

object CardListImport {
    fun detectFormat(text: String): CardListFormat? {
        val firstLine = text.lineSequence().firstOrNull { it.isNotBlank() }?.trim() ?: return null
        val lower = firstLine.lowercase()
        if (',' in lower && "name" in lower && ("set code" in lower || "collector number" in lower || "quantity" in lower)) {
            return CardListFormat.MANABOX_CSV
        }
        val lines = text.lineSequence().map { it.trim() }.filter { it.isNotEmpty() }.toList()
        return if (lines.any { deckLineRegex.matches(it) }) CardListFormat.DECK_LIST_TEXT else null
    }

    fun parse(text: String): List<ImportedCardLine> = when (detectFormat(text)) {
        CardListFormat.MANABOX_CSV -> parseManaBoxCsv(text)
        CardListFormat.DECK_LIST_TEXT -> parseDeckListText(text)
        null -> emptyList()
    }

    // --- ManaBox CSV ---
    private fun splitCsvLine(line: String): List<String> {
        val out = mutableListOf<String>()
        val cur = StringBuilder()
        var inQuotes = false
        var i = 0
        while (i < line.length) {
            val ch = line[i]
            when {
                inQuotes && ch == '"' && i + 1 < line.length && line[i + 1] == '"' -> { cur.append('"'); i++ }
                ch == '"' -> inQuotes = !inQuotes
                ch == ',' && !inQuotes -> { out += cur.toString(); cur.setLength(0) }
                else -> cur.append(ch)
            }
            i++
        }
        out += cur.toString()
        return out.map { it.trim() }
    }

    fun parseManaBoxCsv(text: String): List<ImportedCardLine> {
        val lines = text.lines().filter { it.isNotBlank() }
        if (lines.isEmpty()) return emptyList()
        val header = splitCsvLine(lines.first()).map { it.lowercase() }
        fun col(vararg names: String): Int = names.map { n -> header.indexOf(n) }.firstOrNull { it >= 0 } ?: -1
        val iName = col("name")
        val iSet = col("set code", "set", "edition")
        val iNumber = col("collector number", "number", "card number")
        val iFoil = col("foil", "finish")
        val iQty = col("quantity", "count", "qty")
        val iScryfall = col("scryfall id", "scryfall_id")
        val iPrice = col("purchase price", "price")
        val iLang = col("language", "lang")
        if (iName < 0) return emptyList()
        return lines.drop(1).mapNotNull { raw ->
            val cells = splitCsvLine(raw)
            val name = cells.getOrNull(iName)?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val foilRaw = cells.getOrNull(iFoil)?.lowercase() ?: ""
            ImportedCardLine(
                name = name,
                setCode = cells.getOrNull(iSet)?.takeIf { it.isNotBlank() }?.lowercase(),
                number = cells.getOrNull(iNumber)?.takeIf { it.isNotBlank() },
                quantity = cells.getOrNull(iQty)?.toLongOrNull()?.coerceAtLeast(1) ?: 1,
                foil = foilRaw == "foil" || foilRaw == "etched" || foilRaw == "true" || foilRaw == "1",
                purchasePrice = cells.getOrNull(iPrice)?.replace(",", ".")?.toDoubleOrNull()?.takeIf { it > 0.0 },
                scryfallId = cells.getOrNull(iScryfall)?.takeIf { it.length >= 32 },
                language = cells.getOrNull(iLang)?.lowercase()?.takeIf { it.isNotBlank() }
            )
        }
    }

    // --- Decklisten-Text ---
    // "4 Lightning Bolt (MH3) 123", "4x Lightning Bolt", "1 Sol Ring *F*"
    private val deckLineRegex = Regex("^(\\d+)\\s*[xX]?\\s+(.+?)(?:\\s+\\(([A-Za-z0-9]{2,6})\\)(?:\\s+([A-Za-z0-9★†]+))?)?(?:\\s+\\*F\\*)?(?:\\s+#.*)?$")
    private val sectionRegex = Regex("^(commander|commanders|deck|main|mainboard|sideboard|side|maybeboard|companion|tokens?)\\s*:?\\s*(\\(\\d+\\))?$", RegexOption.IGNORE_CASE)

    fun parseDeckListText(text: String): List<ImportedCardLine> {
        var section = "main"
        val out = mutableListOf<ImportedCardLine>()
        for (rawLine in text.lines()) {
            val line = rawLine.trim()
            if (line.isEmpty() || line.startsWith("//")) continue
            sectionRegex.find(line)?.let { m ->
                section = when (m.groupValues[1].lowercase()) {
                    "commander", "commanders" -> "commander"
                    "sideboard", "side", "maybeboard", "tokens", "token", "companion" -> "sideboard"
                    else -> "main"
                }
                return@let
            }
            if (sectionRegex.matches(line)) continue
            val m = deckLineRegex.find(line)
            if (m != null) {
                out += ImportedCardLine(
                    name = m.groupValues[2].trim(),
                    setCode = m.groupValues[3].takeIf { it.isNotEmpty() }?.lowercase(),
                    number = m.groupValues[4].takeIf { it.isNotEmpty() },
                    quantity = m.groupValues[1].toLongOrNull()?.coerceAtLeast(1) ?: 1,
                    foil = line.contains("*F*"),
                    section = section
                )
            } else if (section == "commander") {
                // Commander-Zeile ohne Zahl ("Ellivere of the Wild Court")
                out += ImportedCardLine(name = line, section = section)
            }
        }
        return out
    }

    // --- Zuordnung zum Katalog ---
    private fun normalizeNumber(n: String): String = n.trim().trimStart('0').ifEmpty { "0" }.lowercase()
    private fun normalizeName(n: String): String = n.substringBefore(" // ").trim().lowercase()
    private val scryfallIdRegex = Regex("/([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})\\.jpg")

    fun resolve(lines: List<ImportedCardLine>, catalog: List<CardCatalogEntity>): ResolvedImport {
        val byId = catalog.associateBy { it.id.lowercase() }
        val byScryfall = HashMap<String, CardCatalogEntity>()
        val byName = HashMap<String, MutableList<CardCatalogEntity>>()
        for (c in catalog) {
            scryfallIdRegex.find(c.imageUrl)?.let { byScryfall[it.groupValues[1]] = c }
            byName.getOrPut(normalizeName(c.name)) { mutableListOf() } += c
        }
        val matched = mutableListOf<ResolvedImportCard>()
        val unmatched = mutableListOf<ImportedCardLine>()
        for (line in lines) {
            if (line.section == "sideboard") continue
            val bySetNumber = if (line.setCode != null && line.number != null) {
                byId["${line.setCode}-${line.number}".lowercase()]
                    ?: byId["${line.setCode}-${normalizeNumber(line.number)}"]
                    ?: catalog.firstOrNull { it.setId.equals(line.setCode, ignoreCase = true) && normalizeNumber(it.number) == normalizeNumber(line.number) }
            } else null
            val card = bySetNumber
                ?: line.scryfallId?.let { byScryfall[it.lowercase()] }
                ?: byName[normalizeName(line.name)]?.let { candidates ->
                    candidates.firstOrNull { it.setId.equals(line.setCode, ignoreCase = true) } ?: candidates.firstOrNull()
                }
            if (card != null) matched += ResolvedImportCard(card, line) else unmatched += line
        }
        return ResolvedImport(matched, unmatched)
    }
}
