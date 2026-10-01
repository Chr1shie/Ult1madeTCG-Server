package com.tcgportfolio.companion

import com.tcgportfolio.companion.data.digimonRuleDataChunks
import com.tcgportfolio.companion.data.onePieceRuleDataChunks

// Deckbau-Regelprüfung für One Piece und Digimon (30.09., mit Release 1.3).
// Nutzer-Vorgabe: "Entweder wir prüfen alles oder gar nichts - man muss sich
// auf unsere App verlassen können beim Deckbau." Deshalb die VOLLSTÄNDIGEN
// offiziellen Regeln inkl. Kartentext-Sonderregeln und Bannlisten. Die
// Kartendaten (Typ, Farben, Kosten, Merkmale, Sonderregeln) kommen aus den
// offiziellen Kartenlisten, generiert von
// tools/catalog/generate_onepiece_digimon_rule_data.py. Wie überall nur
// Warnungen, kein Blockieren; Karten ohne Daten werden gezählt
// (cardsWithoutRuleData), nie als Verstoß gewertet.

private fun parseRuleChunks(chunks: List<String>): Map<String, String> {
    val map = HashMap<String, String>()
    for (chunk in chunks) {
        for (line in chunk.lineSequence()) {
            val sp = line.indexOf(' ')
            if (sp > 0) map[line.substring(0, sp)] = line.substring(sp + 1)
        }
    }
    return map
}

private fun ruleNumber(card: DeckRuleCheckCard): String = (card.number ?: "").trim().uppercase()

// ---------------------------------------------------------------- One Piece

data class OnePieceCardRule(
    val category: Char,            // L = Leader, C = Character, E = Event, S = Stage
    val colors: Set<String>,
    val cost: Int?,
    val types: Set<String>,
    val unlimitedCopies: Boolean,
    // Leader-Deckbau-Sonderregeln aus dem Kartentext
    val leaderMaxCost: Int?,
    val leaderMaxEventCost: Int?,
    val leaderOnlyType: String?
)

object OnePieceRuleData {
    private val raw: Map<String, String> by lazy { parseRuleChunks(onePieceRuleDataChunks) }

    fun lookup(number: String): OnePieceCardRule? {
        val parts = (raw[number] ?: return null).split('|')
        val rules = parts.getOrNull(4).orEmpty().split(';').filter { it.isNotEmpty() }
        return OnePieceCardRule(
            category = parts[0].firstOrNull() ?: '?',
            colors = parts.getOrNull(1).orEmpty().split('/').map { it.trim() }.filter { it.isNotEmpty() }.toSet(),
            cost = parts.getOrNull(2)?.toIntOrNull(),
            types = parts.getOrNull(3).orEmpty().split('/').map { it.trim() }.filter { it.isNotEmpty() }.toSet(),
            unlimitedCopies = "U" in rules,
            leaderMaxCost = rules.firstOrNull { it.startsWith("c") }?.drop(1)?.toIntOrNull(),
            leaderMaxEventCost = rules.firstOrNull { it.startsWith("e") }?.drop(1)?.toIntOrNull(),
            leaderOnlyType = rules.firstOrNull { it.startsWith("t") }?.drop(1)
        )
    }
}

// Offizielle Bannliste: https://en.onepiece-cardgame.com/news/restriction.html
// Stand 30.09.2026 (angekündigt 24.09.); OP14-020 gilt erst ab 12.10.2026.
// Beschränkte Karten gibt es derzeit keine.
private const val OP14_020_BANNED_FROM_MILLIS = 1_791_763_200_000L // 12.10.2026 00:00 UTC
private val onePieceBanned = setOf("OP06-047", "OP03-040", "OP06-086", "ST10-001", "OP06-116")
private val onePieceBannedPairs = listOf(
    "OP07-115" to "EB04-058",
    "OP11-040" to "OP11-067",
    "OP11-040" to "OP08-069"
)

internal fun onePieceBannedNow(nowMillis: Long): Set<String> =
    if (nowMillis >= OP14_020_BANNED_FROM_MILLIS) onePieceBanned + "OP14-020" else onePieceBanned

// Offizielle Regeln (Comprehensive Rules 5-1-2, 2-3-5, 2-14-2):
// - Genau 1 Leader, dazu genau 50 Karten (Character/Event/Stage).
// - Nur Karten, deren Farben ALLE im Leader vorkommen (mehrfarbige Karten
//   gelten als jede ihrer Farben).
// - Maximal 4 Karten je Kartennummer (außer Karten mit "any number").
// - Bannliste + Bann-Paare; Leader-Kartentext-Sonderregeln (Kosten-/Typ-
//   Grenzen, z. B. OP12-001, OP13-079, P-117).
fun checkOnePieceDeckRules(cards: List<DeckRuleCheckCard>, nowMillis: Long = currentTimeMillis()): DeckRuleCheckResult {
    val violations = mutableListOf<String>()
    val rules = cards.associateWith { OnePieceRuleData.lookup(ruleNumber(it)) }
    val isLeader: (DeckRuleCheckCard) -> Boolean = { card ->
        rules[card]?.category?.let { it == 'L' } ?: card.rarity.equals("L", ignoreCase = true)
    }
    val leaders = cards.filter(isLeader)
    val leaderCount = leaders.sumOf { it.quantity }
    when {
        leaderCount == 0L -> violations += "Kein Leader im Deck."
        leaderCount > 1L -> violations += "Es darf nur genau 1 Leader im Deck sein (aktuell $leaderCount)."
    }
    val mainCards = cards.filterNot(isLeader)
    val mainCount = mainCards.sumOf { it.quantity }
    if (mainCount != 50L) violations += "Deck hat $mainCount statt 50 Karten (Leader nicht mitgezählt)."

    val leaderRule = if (leaders.size == 1) rules[leaders.first()] else null
    if (leaderRule != null) {
        for (card in mainCards) {
            val rule = rules[card] ?: continue
            val foreign = rule.colors - leaderRule.colors
            if (rule.colors.isNotEmpty() && leaderRule.colors.isNotEmpty() && foreign.isNotEmpty()) {
                violations += "\"${card.name}\" (${card.number}) ist ${rule.colors.joinToString("/") { colorDe(it) }} - " +
                    "der Leader erlaubt nur ${leaderRule.colors.joinToString("/") { colorDe(it) }}."
            }
            leaderRule.leaderMaxCost?.let { max ->
                if ((rule.cost ?: 0) > max) violations += "\"${card.name}\" (${card.number}) kostet ${rule.cost} - dieser Leader erlaubt nur Karten bis Kosten $max."
            }
            leaderRule.leaderMaxEventCost?.let { max ->
                if (rule.category == 'E' && (rule.cost ?: 0) > max) violations += "\"${card.name}\" (${card.number}) ist ein Event mit Kosten ${rule.cost} - dieser Leader erlaubt nur Events bis Kosten $max."
            }
            leaderRule.leaderOnlyType?.let { type ->
                if (type !in rule.types) violations += "\"${card.name}\" (${card.number}) hat nicht den Typ {$type} - dieser Leader erlaubt nur {$type}-Karten."
            }
        }
    }

    mainCards.groupBy { ruleNumber(it).ifEmpty { it.name.lowercase() } }.forEach { (_, rows) ->
        val count = rows.sumOf { it.quantity }
        if (count > 4 && rules[rows.first()]?.unlimitedCopies != true) {
            val first = rows.first()
            violations += "\"${first.name}\" (${first.number ?: "?"}) ist $count-mal im Deck (max. 4 erlaubt)."
        }
    }

    val present = cards.map { ruleNumber(it) }.toSet()
    val banned = onePieceBannedNow(nowMillis)
    cards.filter { ruleNumber(it) in banned }.distinctBy { ruleNumber(it) }.forEach {
        violations += "\"${it.name}\" (${it.number}) ist offiziell gebannt."
    }
    onePieceBannedPairs.forEach { (a, b) ->
        if (a in present && b in present) violations += "$a und $b dürfen nicht zusammen im Deck sein (offizielles Bann-Paar)."
    }
    return DeckRuleCheckResult(
        violations = violations,
        cardsWithoutRuleData = rules.values.count { it == null },
        totalDistinctCards = cards.size
    )
}

private fun colorDe(color: String): String = when (color) {
    "Red" -> "Rot"; "Green" -> "Grün"; "Blue" -> "Blau"; "Purple" -> "Lila"
    "Black" -> "Schwarz"; "Yellow" -> "Gelb"; else -> color
}

// ------------------------------------------------------------------ Digimon

data class DigimonCardRule(val isDigiEgg: Boolean, val maxCopies: Int, val countsAs: String?)

object DigimonRuleData {
    private val raw: Map<String, String> by lazy { parseRuleChunks(digimonRuleDataChunks) }

    fun lookup(number: String): DigimonCardRule? {
        val parts = (raw[number] ?: return null).split('|')
        return DigimonCardRule(
            isDigiEgg = parts[0] == "E",
            maxCopies = parts.firstOrNull { it.startsWith("n") }?.drop(1)?.toIntOrNull() ?: 4,
            countsAs = parts.firstOrNull { it.startsWith("a") }?.drop(1)
        )
    }
}

// Offizielle Einschränkungsliste: https://world.digimoncard.com/rule/restriction_card/
// "List of Currently Affected Cards", Stand 30.09.2026 (gültig seit 01.09.2026).
private val digimonBanned = setOf("BT5-109", "BT2-090", "EX5-065", "BT15-003")
private val digimonRestrictedToOne = setOf(
    "EX1-066", "EX8-012", "BT14-033", "BT23-032", "BT3-092", "BT10-080", "EX5-059", "EX5-061",
    "BT1-090", "BT6-104", "BT13-110", "BT16-011", "EX3-057", "EX4-006", "EX1-021", "BT19-040",
    "EX2-070", "BT4-111", "BT17-069", "BT4-104", "P-029", "P-030", "BT11-033", "ST9-09",
    "EX4-030", "P-123", "P-130", "BT15-057", "BT9-098", "ST2-13", "BT14-084", "BT14-002",
    "BT15-102", "EX5-015", "EX5-018", "EX5-062", "BT13-012", "BT2-069", "BT7-069", "BT3-054",
    "EX2-039", "P-008", "P-025", "BT11-064", "BT7-107", "BT10-009", "BT7-038", "BT7-064",
    "BT2-047", "BT3-103", "BT6-100", "EX1-068", "BT7-072"
)
// Bann-Paar: ist A im Deck, darf keine der B-Karten hinein
private val digimonBannedPairs = listOf(
    "EX2-007" to setOf("EX7-064"),
    "BT20-037" to setOf("BT17-035", "EX8-037")
)

// Offizielle Regeln: Hauptdeck genau 50 Karten, Digi-Ei-Deck 0-5 Digi-Eier,
// max. 4 Karten je Kartennummer über beide Decks (Kartentext-Ausnahmen:
// "up to 50 copies", RB1-Karten zählen als ihre Promo-Nummer), dazu
// Bannliste, Limit-1-Liste und Bann-Paare.
fun checkDigimonDeckRules(cards: List<DeckRuleCheckCard>): DeckRuleCheckResult {
    val violations = mutableListOf<String>()
    val rules = cards.associateWith { DigimonRuleData.lookup(ruleNumber(it)) }
    val eggs = cards.filter { rules[it]?.isDigiEgg == true }
    val eggCount = eggs.sumOf { it.quantity }
    val mainCount = cards.filter { rules[it]?.isDigiEgg != true }.sumOf { it.quantity }
    if (mainCount != 50L) violations += "Hauptdeck hat $mainCount statt 50 Karten (Digi-Eier nicht mitgezählt)."
    if (eggCount > 5L) violations += "Digi-Ei-Deck hat $eggCount Karten (max. 5 erlaubt)."

    cards.groupBy { rules[it]?.countsAs ?: ruleNumber(it).ifEmpty { it.name.lowercase() } }.forEach { (number, rows) ->
        val count = rows.sumOf { it.quantity }
        val max = when {
            number in digimonRestrictedToOne -> 1
            else -> rows.maxOf { rules[it]?.maxCopies ?: 4 }
        }
        if (count > max) {
            val first = rows.first()
            val why = if (max == 1) " (offiziell auf 1 beschränkt)" else ""
            violations += "\"${first.name}\" ($number) ist $count-mal im Deck (max. $max erlaubt)$why."
        }
    }

    val present = cards.map { ruleNumber(it) }.toSet()
    cards.filter { ruleNumber(it) in digimonBanned }.distinctBy { ruleNumber(it) }.forEach {
        violations += "\"${it.name}\" (${it.number}) ist offiziell gebannt."
    }
    digimonBannedPairs.forEach { (a, bs) ->
        if (a in present) bs.filter { it in present }.forEach { b ->
            violations += "Mit $a im Deck ist $b nicht erlaubt (offizielles Bann-Paar)."
        }
    }
    return DeckRuleCheckResult(
        violations = violations,
        cardsWithoutRuleData = rules.values.count { it == null },
        totalDistinctCards = cards.size
    )
}
