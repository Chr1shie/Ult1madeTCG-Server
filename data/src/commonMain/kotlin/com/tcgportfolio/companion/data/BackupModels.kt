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
    val imageUrl: String? = null,
    // Eigener Preis + Holo-Stil (28.08., Backup-Vollständigkeit) - Defaults
    // halten alte Backups lesbar, genau wie bei ExportedSealedProduct
    val customPriceEur: Double? = null,
    val customPriceInTotal: Long = 1,
    val customPriceInGameTotal: Long = 1,
    val holoStyle: String? = null
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
    val version: Int = 2,
    val exportedAt: Long,
    val cards: List<ExportedCard> = emptyList(),
    val sealedProducts: List<ExportedSealedProduct> = emptyList(),
    // Backup-Vollständigkeit (28.08., Nutzer-Fund "Binder werden nicht
    // wiederhergestellt"): Wunschlisten, Binder und Decks fehlten im Backup
    // komplett. Die Sync-Datenklassen werden hier bewusst WIEDERVERWENDET
    // (gleiche Struktur, gleiche uid-Identitäten), aber ohne Löschlisten -
    // Backup-Restore ist add-only und soll Gelöschtes zurückbringen können
    // (siehe Kommentar oben in SyncModels.kt). version 1 -> 2; alte Backups
    // bleiben über die Listen-Defaults lesbar, alte App-Versionen lesen
    // neue Backups dank ignoreUnknownKeys (nur ohne die neuen Ebenen).
    val wishlists: List<SyncWishlist> = emptyList(),
    val binders: List<SyncBinder> = emptyList(),
    val decks: List<SyncDeck> = emptyList()
)
