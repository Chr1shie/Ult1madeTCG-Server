package com.tcgportfolio.companion

// Pokémon-Deckbau-Regelwerk (02.08., Phase 3 Deckbuilding, Pilot-Spiel) -
// siehe CONCEPT.md "Deckbuilding". Bewusst NUR Warnungen, kein hartes
// Blockieren (Nutzer-Entscheidung 02.08. "warnen, aber zulassen") - eine
// Karte OHNE bekannte Regelmetadaten (supertype == null, siehe
// PokemonCardRulesSync.kt) erzeugt für sich genommen KEINE Warnung,
// "unbekannt" ist nicht dasselbe wie "regelwidrig". cardsWithoutRuleData im
// Ergebnis macht diese Unsicherheit für die UI sichtbar, statt sie zu
// verschweigen.
//
// Kernregeln (offizielles Pokémon-TCG-Regelwerk):
// - Genau 60 Karten im Deck.
// - Maximal 4 Karten mit demselben NAMEN (nicht derselben Druckvariante -
//   2x "Charizard ex" aus Set A + 2x aus Set B zählen zusammen als 4).
//   Ausnahme: Basis-Energien (subtype "Basic Energy") sind unbegrenzt.
// - Mindestens 1 Basis-Pokémon (subtype "Basic" bei supertype "Pokémon").
//
// DeckRuleCheckCard/DeckRuleCheckResult sind spielübergreifend, siehe
// DeckRules.kt (Nutzer-Vorgabe 03.08. "auch für DBFW hinbekommen").

fun checkPokemonDeckRules(cards: List<DeckRuleCheckCard>): DeckRuleCheckResult {
    val violations = mutableListOf<String>()
    val totalCount = cards.sumOf { it.quantity }
    if (totalCount != 60L) {
        violations += "Deck hat $totalCount statt 60 Karten."
    }

    val nonEnergyCards = cards.filter { "Basic Energy" !in it.subtypes }
    val countsByName = nonEnergyCards.groupBy { it.name }.mapValues { (_, rows) -> rows.sumOf { it.quantity } }
    countsByName.forEach { (name, count) ->
        if (count > 4) violations += "\"$name\" ist $count-mal im Deck (max. 4 erlaubt)."
    }

    val knownCards = cards.filter { it.supertype != null }
    val hasBasicPokemon = knownCards.any { it.supertype == "Pokémon" && "Basic" in it.subtypes }
    if (knownCards.isNotEmpty() && !hasBasicPokemon) {
        violations += "Kein Basis-Pokémon im Deck gefunden."
    }

    return DeckRuleCheckResult(
        violations = violations,
        cardsWithoutRuleData = cards.count { it.supertype == null },
        totalDistinctCards = cards.size
    )
}
