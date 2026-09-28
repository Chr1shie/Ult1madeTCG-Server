package com.tcgportfolio.companion.data

// ST8: UlforceVeedramon - offizielle Kartenliste world.digimoncard.com (Stand 28.09.2026),
// Format wie TimelessBondsCatalog.kt (_P1 -> -AA "Alternate Art", _P2 -> -AA2 ...).
// Generiert von tools/catalog/generate_digimon_new_sets.py.
// Offizielle Produktliste: 16 Einträge; hier 16.
// Starterdeck als eigenes Set: Set-Id "DGST8" (ohne Bindestrich), alle Karten-Ids mit
// "DGST8-" vorangestellt (die gedruckte Nummer bleibt in number, z.B. "ST8-01").
// 4 Reprints fremder Nummern (Alternative-Art-Drucke aus diesem Produkt), Id
// "DGST8-<Nummer>-AA<n>".
val digimonStarterDeckSt08SetSeed = CardSetSeed(id = "DGST8", name = "ST8: UlforceVeedramon", game = "Digimon", totalCards = 16)

val digimonStarterDeckSt08CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("DGST8-ST8-01", "DGST8", "ST8-01", "DemiVeemon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST8-01.png", null),
    CatalogCardSeed("DGST8-ST8-02", "DGST8", "ST8-02", "Gabumon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST8-02.png", null),
    CatalogCardSeed("DGST8-ST8-03", "DGST8", "ST8-03", "Dracomon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST8-03.png", null),
    CatalogCardSeed("DGST8-ST8-04", "DGST8", "ST8-04", "Veemon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST8-04.png", null),
    CatalogCardSeed("DGST8-ST8-05", "DGST8", "ST8-05", "Veedramon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST8-05.png", null),
    CatalogCardSeed("DGST8-ST8-06", "DGST8", "ST8-06", "Coredramon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST8-06.png", null),
    CatalogCardSeed("DGST8-ST8-07", "DGST8", "ST8-07", "Wingdramon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST8-07.png", null),
    CatalogCardSeed("DGST8-ST8-08", "DGST8", "ST8-08", "AeroVeedramon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST8-08.png", null),
    CatalogCardSeed("DGST8-ST8-09", "DGST8", "ST8-09", "Slayerdramon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST8-09.png", null),
    CatalogCardSeed("DGST8-ST8-10", "DGST8", "ST8-10", "UlforceVeedramon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST8-10.png", null),
    CatalogCardSeed("DGST8-ST8-11", "DGST8", "ST8-11", "Victory Sword", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST8-11.png", null),
    CatalogCardSeed("DGST8-ST8-12", "DGST8", "ST8-12", "V-Wing Blade", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST8-12.png", null),
    CatalogCardSeed("DGST8-BT1-028-AA", "DGST8", "BT1-028", "Elecmon", "Alternate Art", "C", "https://world.digimoncard.com/images/cardlist/card/BT1-028_P1.png", null),
    CatalogCardSeed("DGST8-BT1-037-AA", "DGST8", "BT1-037", "Gorillamon", "Alternate Art", "C", "https://world.digimoncard.com/images/cardlist/card/BT1-037_P1.png", null),
    CatalogCardSeed("DGST8-BT1-038-AA2", "DGST8", "BT1-038", "Monzaemon", "Alternate Art 2", "C", "https://world.digimoncard.com/images/cardlist/card/BT1-038_P2.png", null),
    CatalogCardSeed("DGST8-ST2-13-AA2", "DGST8", "ST2-13", "Hammer Spark", "Alternate Art 2", "C", "https://world.digimoncard.com/images/cardlist/card/ST2-13_P2.png", null),
)
