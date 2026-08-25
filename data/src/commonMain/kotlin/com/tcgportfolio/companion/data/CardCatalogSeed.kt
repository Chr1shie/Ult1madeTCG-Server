package com.tcgportfolio.companion.data

data class CatalogCardSeed(
    val id: String,
    val setId: String,
    val number: String,
    val name: String,
    val variant: String,
    val rarity: String,
    val imageUrl: String,
    val marketPriceUsd: Double? = null,
    // Cardmarkets eigene Produkt-id (06.08.) - bisher nur für MTG befüllt
    // (kommt direkt von Scryfall, siehe generate_mtg_catalog.py), erlaubt dort
    // einen exakten ID-Join statt Namens-Fuzzy-Matching in
    // PortfolioRepository.applyCardmarketCardPrices(). NULL für alle anderen
    // TCGs (fallen weiter auf den bisherigen Namens-/Code-Abgleich zurück).
    val cardmarketId: Long? = null
)

data class CardSetSeed(
    val id: String,
    val name: String,
    val game: String,
    val totalCards: Long
)
