package com.tcgportfolio.companion.data

// ST7: Gallantmon - offizielle Kartenliste world.digimoncard.com (Stand 28.09.2026),
// Format wie TimelessBondsCatalog.kt (_P1 -> -AA "Alternate Art", _P2 -> -AA2 ...).
// Generiert von tools/catalog/generate_digimon_new_sets.py.
// Offizielle Produktliste: 16 Einträge; hier 16.
// Starterdeck als eigenes Set: Set-Id "DGST7" (ohne Bindestrich), alle Karten-Ids mit
// "DGST7-" vorangestellt (die gedruckte Nummer bleibt in number, z.B. "ST7-01").
// 4 Reprints fremder Nummern (Alternative-Art-Drucke aus diesem Produkt), Id
// "DGST7-<Nummer>-AA<n>".
val digimonStarterDeckSt07SetSeed = CardSetSeed(id = "DGST7", name = "ST7: Gallantmon", game = "Digimon", totalCards = 16)

val digimonStarterDeckSt07CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("DGST7-ST7-01", "DGST7", "ST7-01", "Gigimon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST7-01.png", null),
    CatalogCardSeed("DGST7-ST7-02", "DGST7", "ST7-02", "Agumon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST7-02.png", null),
    CatalogCardSeed("DGST7-ST7-03", "DGST7", "ST7-03", "Guilmon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST7-03.png", null),
    CatalogCardSeed("DGST7-ST7-04", "DGST7", "ST7-04", "Biyomon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST7-04.png", null),
    CatalogCardSeed("DGST7-ST7-05", "DGST7", "ST7-05", "Growlmon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST7-05.png", null),
    CatalogCardSeed("DGST7-ST7-06", "DGST7", "ST7-06", "GeoGreymon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST7-06.png", null),
    CatalogCardSeed("DGST7-ST7-07", "DGST7", "ST7-07", "RizeGreymon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST7-07.png", null),
    CatalogCardSeed("DGST7-ST7-08", "DGST7", "ST7-08", "WarGrowlmon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST7-08.png", null),
    CatalogCardSeed("DGST7-ST7-09", "DGST7", "ST7-09", "Gallantmon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST7-09.png", null),
    CatalogCardSeed("DGST7-ST7-10", "DGST7", "ST7-10", "ShineGreymon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST7-10.png", null),
    CatalogCardSeed("DGST7-ST7-11", "DGST7", "ST7-11", "Lightning Joust", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST7-11.png", null),
    CatalogCardSeed("DGST7-ST7-12", "DGST7", "ST7-12", "Atomic Blaster", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST7-12.png", null),
    CatalogCardSeed("DGST7-BT1-009-AA", "DGST7", "BT1-009", "Monodramon", "Alternate Art", "C", "https://world.digimoncard.com/images/cardlist/card/BT1-009_P1.png", null),
    CatalogCardSeed("DGST7-BT1-019-AA", "DGST7", "BT1-019", "DarkTyrannomon", "Alternate Art", "C", "https://world.digimoncard.com/images/cardlist/card/BT1-019_P1.png", null),
    CatalogCardSeed("DGST7-BT1-020-AA", "DGST7", "BT1-020", "Groundramon", "Alternate Art", "U", "https://world.digimoncard.com/images/cardlist/card/BT1-020_P1.png", null),
    CatalogCardSeed("DGST7-ST1-16-AA2", "DGST7", "ST1-16", "Gaia Force", "Alternate Art 2", "U", "https://world.digimoncard.com/images/cardlist/card/ST1-16_P2.png", null),
)
