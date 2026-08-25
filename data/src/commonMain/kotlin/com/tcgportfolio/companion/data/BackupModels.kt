package com.tcgportfolio.companion.data

import kotlinx.serialization.Serializable

// Reine Datenklassen fürs Export/Import-JSON (Backup) - bewusst getrennt von
// den SQLDelight-generierten Entities, damit @Serializable sauber per
// Compiler-Plugin funktioniert (an generierte Klassen kann man keine
// Annotation dranhängen).

@Serializable
data class ExportedCard(
    val cardId: String? = null,
    val isHolo: Long = 0,
    val name: String,
    val quantity: Long,
    val purchasePrice: Double,
    val imageUrl: String? = null
)

@Serializable
data class ExportedSealedProduct(
    val catalogId: String? = null,
    val isSealed: Long = 1,
    val name: String,
    val category: String,
    val game: String,
    val quantity: Long,
    val purchasePrice: Double,
    val imageUrl: String? = null,
    // Eigener Preis (20.08.) - Defaults halten alte Backups lesbar
    val customPriceEur: Double? = null,
    val customPriceInTotal: Long = 1,
    val customPriceInGameTotal: Long = 1
)

@Serializable
data class BackupPayload(
    val version: Int = 1,
    val exportedAt: Long,
    val cards: List<ExportedCard> = emptyList(),
    val sealedProducts: List<ExportedSealedProduct> = emptyList()
)
