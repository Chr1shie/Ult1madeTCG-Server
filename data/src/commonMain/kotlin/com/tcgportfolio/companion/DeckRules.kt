package com.tcgportfolio.companion

// Gemeinsame Typen fürs Deckbau-Regelwerk (02.08./03.08., Phase 3
// Deckbuilding) - ursprünglich in PokemonDeckRules.kt, jetzt hier ausgelagert,
// da DbfwDeckRules.kt (Nutzer-Vorgabe "auch für DBFW hinbekommen") dieselbe
// Ergebnisform braucht und DeckScreen.kt sie spielunabhängig rendert (ein
// Regelcheck-Banner für alle unterstützten TCGs statt eines pro Spiel).
//
// "number" (03.08. ergänzt) - bei Pokémon ungenutzt (dort zählt der NAME,
// siehe checkPokemonDeckRules), bei DBFW dagegen die eigentliche Gruppierung
// fürs 4-Kopien-Limit ("gleiche Kartennummer", nicht gleicher Name - siehe
// DbfwDeckRules.kt), deshalb optional statt eines zweiten, fast identischen
// Datentyps.
data class DeckRuleCheckCard(
    val cardId: String,
    val name: String,
    val quantity: Long,
    val supertype: String?,
    val subtypes: List<String>,
    val number: String? = null
)

data class DeckRuleCheckResult(
    val violations: List<String>,
    val cardsWithoutRuleData: Int,
    val totalDistinctCards: Int
)
