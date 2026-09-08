package com.tcgportfolio.companion.data

import kotlinx.serialization.Serializable

// Eigenes Format fürs Sync-Protokoll (Phase 1e) - bewusst GETRENNT von
// BackupPayload/ExportedCard/ExportedSealedProduct: Sync braucht Löschungen,
// die sich fortpflanzen, Backup-Restore braucht bewusst das Gegenteil (eine
// alte Sicherung soll gelöschte Karten wieder zurückbringen können). Siehe
// PortfolioRepository.exportSyncData()/importSyncData().

@Serializable
data class SyncCard(
    val cardId: String? = null,
    val isHolo: Long = 0,
    val name: String,
    val quantity: Long,
    val purchasePrice: Double,
    val imageUrl: String? = null,
    val updatedAt: Long,
    // Eigener Preis (18.08.) - Defaults halten alte Gegenstellen kompatibel
    // (ignoreUnknownKeys auf der Empfängerseite, Defaults beim Fehlen)
    val customPriceEur: Double? = null,
    val customPriceInTotal: Long = 1,
    val customPriceInGameTotal: Long = 1,
    // Holo-Stil je Karte (18.08.) - null = globale Einstellung
    val holoStyle: String? = null,
    // Sprachbewusste Kartenbilder (07.09., siehe LocalizedCardImages.kt) -
    // Scan-Sprache + Pro-Karte-Override, Defaults halten alte Gegenstellen
    // kompatibel
    val scanLanguage: String? = null,
    val imageLanguage: String? = null
)

@Serializable
data class SyncSealedProduct(
    val catalogId: String? = null,
    val isSealed: Long = 1,
    val name: String,
    val category: String,
    val game: String,
    val quantity: Long,
    val purchasePrice: Double,
    val imageUrl: String? = null,
    val updatedAt: Long,
    // Eigener Preis (20.08.) - Pendant zu den gleichnamigen SyncCard-Feldern,
    // Defaults halten alte Gegenstellen kompatibel
    val customPriceEur: Double? = null,
    val customPriceInTotal: Long = 1,
    val customPriceInGameTotal: Long = 1
)

@Serializable
data class SyncDeletion(
    val itemKey: String,
    val deletedAt: Long
)

// Wunschlisten (28.07., Nutzer-Vorgabe: "alles soll synchron sein zwischen
// App und Server") - anders als Karten/Sealed-Produkte sind das ZWEI
// verschachtelte Ebenen (Liste + ihre Karten). "uid" ist die stabile,
// geräteübergreifende Identität einer Liste (die lokale AUTOINCREMENT-id ist
// das NICHT); Karten-Identität innerhalb einer Liste ist ihre cardId (bzw.
// Name als Ersatz), siehe wishlistItemKey() in PortfolioRepository. Kein
// eigenes "Update" nötig - Listen/Karten werden nie bearbeitet, nur angelegt
// oder gelöscht, createdAt/addedAt übernehmen die Rolle von updatedAt.
@Serializable
data class SyncWishlistItem(
    val cardId: String? = null,
    val name: String,
    val imageUrl: String? = null,
    val addedAt: Long
)

@Serializable
data class SyncWishlist(
    val uid: String,
    val name: String,
    val game: String,
    val createdAt: Long,
    val items: List<SyncWishlistItem> = emptyList(),
    // Umbenennen (19.08.) - Default 0 hält alte Sync-Partner kompatibel
    val nameUpdatedAt: Long = 0
)

// Binder (31.07., Nutzer-Vorgabe) - strukturell identisch zu SyncWishlist/
// SyncWishlistItem oben, siehe Kommentar bei BinderEntity in Portfolio.sq.
// Eigene Klassen statt SyncWishlist wiederzuverwenden, damit Binder und
// Wishlist unabhängig voneinander weiterentwickelt werden können - genau wie
// hier schon vorausgesehen, hat Binder inzwischen (Binder-Seiten, ebenfalls
// 31.07.) eine Sortierreihenfolge (position) bekommen, die Wishlist nie
// braucht. position/positionUpdatedAt sind der einzige echte "Update"-Fall
// im ganzen Binder/Wishlist-Sync (sonst wird nie etwas an einem bestehenden
// Eintrag verändert, nur angelegt/gelöscht) - positionUpdatedAt ist deshalb
// ein EIGENER Zeitstempel, getrennt von addedAt, siehe importSyncData().
@Serializable
data class SyncBinderItem(
    val cardId: String? = null,
    val name: String,
    val imageUrl: String? = null,
    val addedAt: Long,
    val position: Int = 0,
    val positionUpdatedAt: Long = 0
)

@Serializable
data class SyncBinder(
    val uid: String,
    val name: String,
    val game: String,
    val createdAt: Long,
    val pageSize: Int = 9,
    val items: List<SyncBinderItem> = emptyList(),
    // Umbenennen (03.08., Nutzer-Vorgabe) - eigener Zeitstempel NUR für den
    // Namen, analog zu SyncBinderItem.positionUpdatedAt, siehe Kommentar bei
    // BinderEntity in Portfolio.sq
    val nameUpdatedAt: Long = 0,
    // Binder-Farbe (25.08., Nutzer-Korrektur "das wäre ja doof") - LWW über
    // colorUpdatedAt; das Cover-FOTO läuft über den Foto-Sync-Kanal
    val color: String? = null,
    val colorUpdatedAt: Long = 0
)

// Sealed-Wantslisten (28.08., Server-Parität vor dem Launch) - strukturell
// wie SyncWishlist/SyncBinder (uid als Listen-Identität), Eintrags-Identität
// ist die catalogId innerhalb der Liste. Der Preis-Alarm ist die einzige
// veränderliche Eigenschaft (analog position beim Binder-Eintrag) und wird
// per alarmUpdatedAt-LWW aufgelöst.
@Serializable
data class SyncSealedWishlistItem(
    val catalogId: String,
    val addedAt: Long,
    val priceAlarmEur: Double? = null,
    val alarmUpdatedAt: Long = 0
)

@Serializable
data class SyncSealedWishlist(
    val uid: String,
    val name: String,
    val game: String,
    val createdAt: Long,
    val items: List<SyncSealedWishlistItem> = emptyList()
)

// Decks (02.08., Phase 3 Deckbuilding, Nutzer-Vorgabe) - strukturell an
// SyncBinder angelehnt (uid als Sync-Identität), aber ohne Steckplätze:
// quantity statt position, addedAt dient hier bewusst als "zuletzt
// angefasst" (auch bei reinen Mengenänderungen aktualisiert, siehe
// updateDeckCardQuantity() in Portfolio.sq) statt einer eigenen
// positionUpdatedAt-artigen Zusatzspalte - Menge ist die einzige
// veränderliche Eigenschaft einer Deck-Karte. cardId bewusst NICHT
// nullable (anders als SyncBinderItem) - Deck-Karten ohne Katalogbezug
// ergeben im Deckbau-Kontext keinen Sinn, siehe DeckCardEntity-Kommentar.
@Serializable
data class SyncDeckCard(
    val cardId: String,
    val quantity: Long,
    val addedAt: Long
)

@Serializable
data class SyncDeck(
    val uid: String,
    val name: String,
    val game: String,
    val createdAt: Long,
    val cards: List<SyncDeckCard> = emptyList(),
    // Deckbau-Format + Commander (08.09., siehe MtgDeckRules.kt) - Defaults
    // halten alte Gegenstellen kompatibel; beim Merge gewinnt ein gesetzter
    // Wert über einen lokal fehlenden
    val format: String? = null,
    val commanderCardId: String? = null,
    // Deck-Cover (08.09.): nur http-URLs (Katalogkarte) wandern hier mit,
    // eigene Fotos laufen über den Foto-Sync-Kanal ("deck"); LWW per
    // coverUpdatedAt
    val coverImageUrl: String? = null,
    val coverUpdatedAt: Long = 0
)

// Manuelle Cardmarket-Preisauswahl (28.07., Nutzer-Vorgabe "let the user
// decide ... the right one will be tracked anywhere in our app or the
// server") - anders als die rohen Cardmarket-Daten selbst (unabhängig pro
// Seite neu geladen, siehe CardmarketPriceSync.kt) ist das echte Nutzer-
// Angabe und wird synchronisiert. catalogCardId ist die stabile
// CardCatalogEntity.id, selectedIndex die Position in der (pro Seite
// eigenständig geladenen) Optionsliste - kann nach dem Sync auf der
// Gegenseite außerhalb der Listengrenzen liegen, falls deren Optionsliste
// gerade kleiner ist; wird dort dann einfach ignoriert, bis der nächste
// eigene Preisabgleich wieder genug Optionen liefert.
@Serializable
data class SyncCardmarketPriceSelection(
    val catalogCardId: String,
    val selectedIndex: Int,
    val selectedAt: Long
)

// Sealed-Pendant zu SyncCardmarketPriceSelection (11.08.), siehe dortigen
// Kommentar - gleiches Prinzip, nur auf SealedCatalogEntity.id statt
// CardCatalogEntity.id.
@Serializable
data class SyncSealedCardmarketPriceSelection(
    val sealedCatalogId: String,
    val selectedIndex: Int,
    val selectedAt: Long
)

// Mandantenfähigkeit (02.08., Nutzer-Vorgabe) - siehe CONCEPT.md
// "Mandantenfähigkeit / Mehrere Accounts". uid ist wie bei SyncWishlist/
// SyncBinder die stabile, geräteübergreifende Identität (App und Server
// vergeben lokale ids unabhängig voneinander). Bündelt je Account dessen
// komplettes Datenpaket - ein Sync-Lauf verarbeitet laut Nutzer-Vorgabe
// IMMER ALLE Accounts auf einmal ("egal welcher Account sich anmeldet, es
// müssen beide Accounts gesynced werden"), nicht nur den gerade aktiven.
// pinHash/pinSalt werden mitsynchronisiert, damit ein PIN-geschützter
// Account auf jedem Gerät/der Weboberfläche gleich geschützt ist - der
// Klartext-PIN selbst verlässt das Gerät nie, nur der Hash.
@Serializable
data class SyncAccount(
    val uid: String,
    val name: String,
    val pinHash: String? = null,
    val pinSalt: String? = null,
    val updatedAt: Long,
    val cards: List<SyncCard> = emptyList(),
    val sealedProducts: List<SyncSealedProduct> = emptyList(),
    val deletedCardKeys: List<SyncDeletion> = emptyList(),
    val deletedSealedKeys: List<SyncDeletion> = emptyList(),
    val wishlists: List<SyncWishlist> = emptyList(),
    val deletedWishlistKeys: List<SyncDeletion> = emptyList(),
    val deletedWishlistItemKeys: List<SyncDeletion> = emptyList(),
    val binders: List<SyncBinder> = emptyList(),
    val deletedBinderKeys: List<SyncDeletion> = emptyList(),
    val deletedBinderItemKeys: List<SyncDeletion> = emptyList(),
    val decks: List<SyncDeck> = emptyList(),
    val deletedDeckKeys: List<SyncDeletion> = emptyList(),
    val deletedDeckCardKeys: List<SyncDeletion> = emptyList(),
    // Sealed-Wantslisten (28.08.) - Defaults halten alte Gegenstellen
    // kompatibel (App v5/Server davor kennen die Felder schlicht nicht)
    val sealedWishlists: List<SyncSealedWishlist> = emptyList(),
    val deletedSealedWishlistKeys: List<SyncDeletion> = emptyList(),
    val deletedSealedWishlistItemKeys: List<SyncDeletion> = emptyList()
)

@Serializable
data class SyncPayload(
    val exportedAt: Long,
    // Umrechnungsfaktor (26.07.) - Nutzer-Fund: App und Server (bzw. dessen
    // Weboberfläche) hatten bisher unabhängig kalibrierte Werte, wodurch
    // dieselben Karten je nach Ort unterschiedliche EUR-Werte zeigten. Jetzt
    // Teil des Sync-Protokolls, per updatedAt-Zeitstempel aufgelöst (jüngerer
    // Wert gewinnt auf beiden Seiten) - siehe PortfolioRepository. Bewusst
    // GERÄTEWEIT statt pro Account (der Umrechnungsfaktor ist eine reine
    // Anzeige-Kalibrierung, kein Bestandsdatum).
    val marketFactor: Float? = null,
    val marketFactorUpdatedAt: Long? = null,
    // Kartenbild-Sprache (07.09., siehe LocalizedCardImages.kt) - geräteweite
    // Anzeige-Einstellung "asScanned"/"de"/"en", LWW per Zeitstempel genau
    // wie marketFactor
    val cardImageLanguageMode: String? = null,
    val cardImageLanguageModeUpdatedAt: Long? = null,
    // cardmarketPriceSelections (02.08., bewusst weiterhin geräteweit statt
    // pro Account) - die Auswahl sitzt auf CardCatalogEntity, also den
    // GETEILTEN Katalog-Stammdaten, nicht auf einer PortfolioItemEntity-Zeile
    // eines bestimmten Accounts (siehe Kommentar bei SyncCardmarketPriceSelection).
    // Bekannte Einschränkung: besitzen zwei Accounts zufällig dieselbe Karte
    // MIT mehreren Cardmarket-Notierungen und wollen unterschiedliche Notierungen
    // wählen, überschreiben sie sich gegenseitig - akzeptierter Rand-/Sonderfall
    // für die erste Umsetzung, siehe CONCEPT.md.
    val cardmarketPriceSelections: List<SyncCardmarketPriceSelection> = emptyList(),
    // sealedCardmarketPriceSelections (11.08.) - Pendant zu
    // cardmarketPriceSelections oben, siehe SyncSealedCardmarketPriceSelection.
    val sealedCardmarketPriceSelections: List<SyncSealedCardmarketPriceSelection> = emptyList(),
    val accounts: List<SyncAccount> = emptyList(),
    val deletedAccountKeys: List<SyncDeletion> = emptyList()
)
