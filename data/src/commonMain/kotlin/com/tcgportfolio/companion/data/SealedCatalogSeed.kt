package com.tcgportfolio.companion.data

data class SealedCatalogSeed(
    val id: String,
    val name: String,
    val category: String,
    val game: String,
    val imageUrl: String,
    val marketPriceUsd: Double? = null
)
