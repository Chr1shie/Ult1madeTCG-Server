package com.tcgportfolio.companion.data

// Altered TCG Sealed-Produkte (13.08., Nutzer-Vorgabe "gleich sealed
// produkte") - Cardmarket fuehrt Altered TCG (Stand 13.08.) noch nicht als
// eigenes Spiel (alle getesteten game-ids ab 23 liefern 403, siehe
// Kommentar bei CARDMARKET_GAME_IDS in CardmarketPriceSync.kt - anders
// als bei Star Wars: Unlimited konnte hier also keine echte Produktliste
// samt Namen automatisch gezogen werden). Produktnamen deshalb nach dem
// game-weit einheitlichen offiziellen Muster "<Settitel> Booster Box"/
// "<Settitel> Starter Deck" gebildet (Set-Titel selbst echt, direkt aus
// den AlteredEquinox-Kartendaten, siehe AlteredCoreCatalog.kt) - Bilder auf
// dem "no-image"-Sentinel wie bei jedem anderen TCG ohne verifizierte
// offizielle Bildquelle. Sobald Cardmarket das Spiel listet oder eine
// andere offizielle Bildquelle gefunden wird, hier nachziehen.
// Nur "Booster Box" pro Set aufgenommen, keine geratenen Starter-Deck-
// Eintraege - anders als bei SWU (dort echte Cardmarket-Produktnamen)
// ist hier nicht zuverlaessig bekannt, welche der kleineren Sets
// (ALIZE/BISE/CYCLONE/DUSTER/EOLE/FUGUE) ueberhaupt eigene Starter-Decks
// bekommen haben - ein erfundenes Produkt waere schlimmer als ein
// fehlendes.
val alteredSealedCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("ALT-BTG-BB", "Beyond the Gates Booster Box", "Booster Box", "Altered", "https://no-image.tcgportfolio.internal/altered-sealed-no-photo-yet", null),
    SealedCatalogSeed("ALT-TBF-BB", "Trial by Frost Booster Box", "Booster Box", "Altered", "https://no-image.tcgportfolio.internal/altered-sealed-no-photo-yet", null),
    SealedCatalogSeed("ALT-WFM-BB", "Whispers from the Maze Booster Box", "Booster Box", "Altered", "https://no-image.tcgportfolio.internal/altered-sealed-no-photo-yet", null),
    SealedCatalogSeed("ALT-SBO-BB", "Skybound Odyssey Booster Box", "Booster Box", "Altered", "https://no-image.tcgportfolio.internal/altered-sealed-no-photo-yet", null),
    SealedCatalogSeed("ALT-SOU-BB", "Seeds of Unity Booster Box", "Booster Box", "Altered", "https://no-image.tcgportfolio.internal/altered-sealed-no-photo-yet", null),
    SealedCatalogSeed("ALT-ROC-BB", "Roots of Corruption Booster Box", "Booster Box", "Altered", "https://no-image.tcgportfolio.internal/altered-sealed-no-photo-yet", null),
    SealedCatalogSeed("ALT-NEJ-BB", "Neverending Journey Booster Box", "Booster Box", "Altered", "https://no-image.tcgportfolio.internal/altered-sealed-no-photo-yet", null),
)
