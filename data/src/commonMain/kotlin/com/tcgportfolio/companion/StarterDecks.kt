package com.tcgportfolio.companion

// Starter-Decks ins Deckbau-Tool (29.09.) - One Piece, DBFW und Digimon. Die
// Listen selbst sind generiert (StarterDeckLists.kt); hier nur Auswahl je
// Spiel und die Zuordnung eines Vault-Produkts (Katalog-Produkt wie
// "Starter Deck 1: Son Goku" oder Freitext wie "ST-01 Straw Hat Crew") zum
// passenden Starter-Set. MTG läuft weiter über MTGJSON (PreconDecks.kt).
object StarterDecks {
    val games = setOf("OnePiece", "DBFW", "Digimon")

    fun forGame(game: String): List<StarterDeckList> = starterDeckListsGenerated.filter { it.game == game }

    private fun normalize(s: String): String = s.lowercase().replace(Regex("[^a-z0-9]"), "")

    // Unterscheidender Namensteil des Sets: "ST01: Straw Hat Crew" -> "Straw
    // Hat Crew", "Starter Deck -Son Goku (Mini)-" -> "Son Goku (Mini)",
    // "Starter Deck EX The Beat of Ki" -> "The Beat of Ki"
    private fun distinctiveName(setName: String): String =
        setName.substringAfter(": ")
            .replace(Regex("^Starter Deck( EX)?\\s*:?\\s*", RegexOption.IGNORE_CASE), "")
            .replace(Regex("^Starter Deck EX:?\\s*", RegexOption.IGNORE_CASE), "")
            .trim('-', ' ')

    fun matchProduct(game: String, productName: String): StarterDeckList? {
        val decks = forGame(game)
        if (decks.isEmpty()) return null
        // 1. Deck-Nummer im Produktnamen - Katalog-Produkte heißen bei allen
        // drei Spielen "Starter Deck 15: ...", Freitext oft "ST-15"/"FS15"
        val codeNumber = Regex("\\b(?:ST|FS|SD|Starter Deck)\\s*-?0*(\\d{1,2})\\b", RegexOption.IGNORE_CASE)
            .find(productName)?.groupValues?.get(1)?.toIntOrNull()
        if (codeNumber != null) {
            decks.firstOrNull { Regex("\\d+$").find(it.setId)?.value?.toIntOrNull() == codeNumber }?.let { return it }
        }
        // 2. Name des Decks im Produktnamen (längster Treffer gewinnt, damit
        // "Son Goku (Mini)" nicht als "Son Goku" erkannt wird)
        val normProduct = normalize(productName)
        return decks
            .map { it to normalize(distinctiveName(it.name)) }
            .filter { (_, n) -> n.length >= 4 && n in normProduct }
            .maxByOrNull { (_, n) -> n.length }
            ?.first
    }
}
