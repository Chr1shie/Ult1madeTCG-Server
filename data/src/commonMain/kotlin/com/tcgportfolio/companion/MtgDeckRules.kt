package com.tcgportfolio.companion

import com.tcgportfolio.companion.data.mtgRuleDataChunks

// Magic-Deckbau-Regelwerk (08.09., 1.1, Nutzer-Auftrag "Deckbau-Regeln für
// Magic: nur Commander, Standard und Pioneer, der Rest ist egal"). Gleiche
// Philosophie wie PokemonDeckRules.kt: NUR Warnungen, kein Blockieren, und
// eine Karte ohne Regeldaten ist "unbekannt", nicht "regelwidrig".
//
// Regeldaten kommen aus MtgRuleData.kt (GENERIERT von
// tools/fetch_mtg_rule_data.py aus der Scryfall-Suche, ein Druck je Zeile
// "<cardId> <flags> <farbidentität>", siehe Skript-Kopf) - bewusst als
// String-Chunks kompiliert statt zur Laufzeit geladen: Deckbau muss offline
// funktionieren, und Standard-Legalität ändert sich nur mit jedem Set (dann
// läuft das Skript beim Katalog-Frische-Pass eh mit).
//
// Kernregeln (offizielle Turnierregeln):
// - Standard/Pioneer: mindestens 60 Karten, maximal 4 Kopien mit demselben
//   NAMEN (Standardländer und "any number of cards named"-Karten
//   unbegrenzt), jede Karte muss im Format legal sein.
// - Commander: genau 100 Karten inklusive Commander, alles Singleton (außer
//   Standardländer/"any number"), ein festgelegter Commander (legendäre
//   Kreatur oder "can be your commander"), jede Karte innerhalb der
//   Farbidentität des Commanders, keine gebannten Karten.
// Das Format je Deck steht in DeckEntity.format ("standard"/"pioneer"/
// "commander", NULL = kein Regelcheck), der Commander in
// DeckEntity.commanderCardId - beides synct mit (SyncDeck).

enum class MtgFormat(val key: String, val label: String) {
    STANDARD("standard", "Standard"),
    PIONEER("pioneer", "Pioneer"),
    COMMANDER("commander", "Commander");

    companion object {
        fun fromKey(key: String?): MtgFormat? = entries.firstOrNull { it.key == key }
    }
}

data class MtgCardRule(
    val standard: Boolean,
    val pioneer: Boolean,
    val commander: Boolean,
    val canBeCommander: Boolean,
    val basicLand: Boolean,
    val unlimited: Boolean,
    // Teilmenge von "WUBRG" in dieser Reihenfolge, "" = farblos
    val colorIdentity: String
) {
    fun legalIn(format: MtgFormat): Boolean = when (format) {
        MtgFormat.STANDARD -> standard
        MtgFormat.PIONEER -> pioneer
        MtgFormat.COMMANDER -> commander
    }
}

object MtgRuleData {
    private val raw: Map<String, String> by lazy {
        val map = HashMap<String, String>()
        for (chunk in mtgRuleDataChunks) {
            for (line in chunk.lineSequence()) {
                val sp = line.indexOf(' ')
                if (sp <= 0) continue
                map[line.substring(0, sp)] = line.substring(sp + 1)
            }
        }
        map
    }

    fun lookup(cardId: String): MtgCardRule? {
        val entry = raw[cardId] ?: return null
        val parts = entry.split(' ')
        val flags = parts.getOrNull(0) ?: "-"
        val ci = parts.getOrNull(1)?.takeIf { it != "-" } ?: ""
        return MtgCardRule(
            standard = 'S' in flags,
            pioneer = 'P' in flags,
            commander = 'C' in flags,
            canBeCommander = 'L' in flags,
            basicLand = 'B' in flags,
            unlimited = 'U' in flags,
            colorIdentity = ci
        )
    }

    val isAvailable: Boolean get() = raw.isNotEmpty()
}

private fun formatColorIdentity(ci: String): String =
    if (ci.isEmpty()) "farblos" else ci.map {
        when (it) { 'W' -> "Weiß"; 'U' -> "Blau"; 'B' -> "Schwarz"; 'R' -> "Rot"; 'G' -> "Grün"; else -> it.toString() }
    }.joinToString("/")

// Name ohne Rückseite ("Fable of the Mirror-Breaker // Reflection of
// Kiki-Jiki" -> Vorderseite), damit verschiedene Drucke/Schreibweisen
// derselben Karte zusammen gezählt werden
private fun mtgCountingName(name: String): String = name.substringBefore(" // ").trim().lowercase()

fun checkMtgDeckRules(cards: List<DeckRuleCheckCard>, format: MtgFormat, commanderCardId: String?): DeckRuleCheckResult {
    val violations = mutableListOf<String>()
    val rules = cards.associate { it.cardId to MtgRuleData.lookup(it.cardId) }
    val totalCount = cards.sumOf { it.quantity }

    // Kartenzahl
    when (format) {
        MtgFormat.COMMANDER -> if (totalCount != 100L) {
            violations += "Deck hat $totalCount statt 100 Karten (Commander mitgezählt)."
        }
        else -> if (totalCount < 60L) {
            violations += "Deck hat $totalCount Karten (mindestens 60 nötig)."
        }
    }

    // Kopien je Name
    val maxCopies = if (format == MtgFormat.COMMANDER) 1L else 4L
    cards.groupBy { mtgCountingName(it.name) }.forEach { (_, rows) ->
        val rule = rows.firstNotNullOfOrNull { rules[it.cardId] }
        if (rule != null && (rule.basicLand || rule.unlimited)) return@forEach
        val count = rows.sumOf { it.quantity }
        if (count > maxCopies) {
            val display = rows.first().name.substringBefore(" // ")
            violations += if (format == MtgFormat.COMMANDER) {
                "\"$display\" ist $count-mal im Deck (Commander: nur 1 Kopie erlaubt)."
            } else {
                "\"$display\" ist $count-mal im Deck (max. 4 erlaubt)."
            }
        }
    }

    // Legalität
    cards.forEach { c ->
        val rule = rules[c.cardId] ?: return@forEach
        if (!rule.legalIn(format)) {
            violations += "\"${c.name.substringBefore(" // ")}\" ist in ${format.label} nicht legal."
        }
    }

    // Commander-spezifisch
    if (format == MtgFormat.COMMANDER) {
        val commander = commanderCardId?.let { id -> cards.firstOrNull { it.cardId == id } }
        if (commanderCardId == null) {
            violations += "Kein Commander festgelegt - in der Kartenliste eine legendäre Kreatur als Commander wählen."
        } else if (commander == null) {
            violations += "Der festgelegte Commander liegt nicht im Deck."
        } else {
            val rule = rules[commander.cardId]
            if (rule != null && !rule.canBeCommander) {
                violations += "\"${commander.name.substringBefore(" // ")}\" darf kein Commander sein (keine legendäre Kreatur)."
            }
            val identity = rule?.colorIdentity
            if (identity != null) {
                cards.forEach { c ->
                    val r = rules[c.cardId] ?: return@forEach
                    if (r.colorIdentity.any { it !in identity }) {
                        violations += "\"${c.name.substringBefore(" // ")}\" (${formatColorIdentity(r.colorIdentity)}) passt nicht zur Farbidentität des Commanders (${formatColorIdentity(identity)})."
                    }
                }
            }
        }
    }

    return DeckRuleCheckResult(
        violations = violations,
        cardsWithoutRuleData = cards.count { rules[it.cardId] == null },
        totalDistinctCards = cards.size
    )
}
