package com.tcgportfolio.companion

// Final-Fantasy-TCG-Deckbau-Regelwerk (08.09., 1.1, Nutzer-Auftrag "und
// vielleicht noch für Final Fantasy") - braucht keine Zusatzdaten:
// - Genau 50 Karten.
// - Maximal 3 Karten mit derselben KARTENNUMMER (z.B. "7-034L"; Full-Art-
//   und Nachdruck-Varianten tragen dieselbe Nummer und zählen zusammen).
// Keine Formate/Legalitäten. Wie überall nur Warnungen, kein Blockieren.
fun checkFinalFantasyDeckRules(cards: List<DeckRuleCheckCard>): DeckRuleCheckResult {
    val violations = mutableListOf<String>()
    val totalCount = cards.sumOf { it.quantity }
    if (totalCount != 50L) {
        violations += "Deck hat $totalCount statt 50 Karten."
    }
    cards.groupBy { it.number?.uppercase() ?: it.name.lowercase() }.forEach { (_, rows) ->
        val count = rows.sumOf { it.quantity }
        if (count > 3) {
            val first = rows.first()
            violations += "\"${first.name}\" (${first.number ?: "?"}) ist $count-mal im Deck (max. 3 erlaubt)."
        }
    }
    return DeckRuleCheckResult(
        violations = violations,
        cardsWithoutRuleData = 0,
        totalDistinctCards = cards.size
    )
}
