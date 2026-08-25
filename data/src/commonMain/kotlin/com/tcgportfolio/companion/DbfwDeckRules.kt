package com.tcgportfolio.companion

// DBFW-Deckbau-Regelwerk (03.08., Phase 3 Deckbuilding, Nutzer-Vorgabe "auch
// für DBFW hinbekommen") - siehe CONCEPT.md "Deckbuilding" und
// DbfwCardRulesSync.kt für die Herkunft der Regel-Metadaten. Wie bei Pokémon
// bewusst NUR Warnungen, kein hartes Blockieren (dieselbe Nutzer-Entscheidung
// "warnen, aber zulassen" gilt spielübergreifend). Karten OHNE bekannte
// Regelmetadaten (cardType/supertype == null) erzeugen für sich genommen
// KEINE Warnung, siehe cardsWithoutRuleData.
//
// Hier wird DeckRuleCheckCard.supertype als "cardType" (LEADER/BATTLE/EXTRA)
// gelesen und subtypes als einelementige Farb-Liste (leer = farblos/
// unbekannt, siehe DbfwCardRulesSync.kt) - keine neuen, DBFW-eigenen
// Datentypen, um DeckScreen.kt spielunabhängig zu halten.
//
// Kernregeln (offizielles DBFW-Regelwerk, dbs-cardgame.com/fw FAQ "Building a
// deck"):
// - Genau 1 Anführer (Leader) im Deck.
// - 50 bis 60 weitere Karten (Battle/Extra), NICHT den Anführer mitgezählt.
// - Maximal 4 Karten mit derselben KARTENNUMMER (z.B. "FB01-050" - anders als
//   bei Pokémon zählt hier die Nummer, nicht der Name, siehe DeckRules.kt).
// - Alle Karten müssen farblich zum Anführer passen (Anführer-Farbe(n)
//   müssen die Karten-Farbe enthalten). "Extra"-Karten sind grundsätzlich
//   farblos und dürfen immer rein. Ist die Anführer-Farbe selbst unbekannt
//   (fehlende Regeldaten), wird der Farbcheck komplett übersprungen statt
//   falsch zu warnen.

fun checkDbfwDeckRules(cards: List<DeckRuleCheckCard>): DeckRuleCheckResult {
    val violations = mutableListOf<String>()

    val leaders = cards.filter { it.supertype == "LEADER" }
    val leaderCount = leaders.sumOf { it.quantity }
    when {
        leaders.isEmpty() -> violations += "Kein Anführer (Leader) im Deck."
        leaderCount > 1 -> violations += "Es darf nur genau 1 Anführer im Deck sein (aktuell $leaderCount)."
    }

    val nonLeaderCards = cards.filter { it.supertype != "LEADER" }
    val nonLeaderCount = nonLeaderCards.sumOf { it.quantity }
    if (nonLeaderCount < 50 || nonLeaderCount > 60) {
        violations += "Deck hat $nonLeaderCount statt 50-60 Karten (Anführer nicht mitgezählt)."
    }

    val countsByNumber = cards.groupBy { it.number ?: it.cardId }.mapValues { (_, rows) -> rows.sumOf { it.quantity } }
    countsByNumber.forEach { (number, count) ->
        if (count > 4) violations += "Karte $number ist $count-mal im Deck (max. 4 erlaubt)."
    }

    val leaderColor = leaders.firstOrNull()?.subtypes?.firstOrNull()
    if (leaderColor != null) {
        nonLeaderCards.forEach { card ->
            if (card.supertype == "EXTRA") return@forEach
            val cardColor = card.subtypes.firstOrNull() ?: return@forEach
            if (cardColor != leaderColor) {
                violations += "\"${card.name}\" ($cardColor) passt farblich nicht zum Anführer ($leaderColor)."
            }
        }
    }

    return DeckRuleCheckResult(
        violations = violations,
        cardsWithoutRuleData = cards.count { it.supertype == null },
        totalDistinctCards = cards.size
    )
}
