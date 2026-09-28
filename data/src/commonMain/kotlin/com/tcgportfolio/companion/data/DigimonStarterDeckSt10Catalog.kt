package com.tcgportfolio.companion.data

// ST10: Parallel World Tactician - offizielle Kartenliste world.digimoncard.com (Stand 28.09.2026),
// Format wie TimelessBondsCatalog.kt (_P1 -> -AA "Alternate Art", _P2 -> -AA2 ...).
// Generiert von tools/catalog/generate_digimon_new_sets.py.
// Offizielle Produktliste: 16 Einträge; hier 16.
// Starterdeck als eigenes Set: Set-Id "DGST10" (ohne Bindestrich), alle Karten-Ids mit
// "DGST10-" vorangestellt (die gedruckte Nummer bleibt in number, z.B. "ST10-01").
// 1 Reprints fremder Nummern (Alternative-Art-Drucke aus diesem Produkt), Id
// "DGST10-<Nummer>-AA<n>".
val digimonStarterDeckSt10SetSeed = CardSetSeed(id = "DGST10", name = "ST10: Parallel World Tactician", game = "Digimon", totalCards = 16)

val digimonStarterDeckSt10CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("DGST10-ST10-01", "DGST10", "ST10-01", "Nyaromon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST10-01.png", null),
    CatalogCardSeed("DGST10-ST10-02", "DGST10", "ST10-02", "Salamon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST10-02.png", null),
    CatalogCardSeed("DGST10-ST10-03", "DGST10", "ST10-03", "Lopmon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST10-03.png", null),
    CatalogCardSeed("DGST10-ST10-04", "DGST10", "ST10-04", "Gatomon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST10-04.png", null),
    CatalogCardSeed("DGST10-ST10-05", "DGST10", "ST10-05", "Angewomon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST10-05.png", null),
    CatalogCardSeed("DGST10-ST10-06", "DGST10", "ST10-06", "Mastemon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST10-06.png", null),
    CatalogCardSeed("DGST10-ST10-07", "DGST10", "ST10-07", "Ghostmon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST10-07.png", null),
    CatalogCardSeed("DGST10-ST10-08", "DGST10", "ST10-08", "Tsukaimon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST10-08.png", null),
    CatalogCardSeed("DGST10-ST10-09", "DGST10", "ST10-09", "Witchmon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST10-09.png", null),
    CatalogCardSeed("DGST10-ST10-10", "DGST10", "ST10-10", "Wizardmon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST10-10.png", null),
    CatalogCardSeed("DGST10-ST10-11", "DGST10", "ST10-11", "Bastemon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST10-11.png", null),
    CatalogCardSeed("DGST10-ST10-12", "DGST10", "ST10-12", "LadyDevimon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST10-12.png", null),
    CatalogCardSeed("DGST10-ST10-13", "DGST10", "ST10-13", "Junomon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST10-13.png", null),
    CatalogCardSeed("DGST10-ST10-14", "DGST10", "ST10-14", "Chaos Degradation", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST10-14.png", null),
    CatalogCardSeed("DGST10-ST10-15", "DGST10", "ST10-15", "Darkness Wave", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST10-15.png", null),
    CatalogCardSeed("DGST10-BT2-108-AA", "DGST10", "BT2-108", "Night Raid", "Alternate Art", "C", "https://world.digimoncard.com/images/cardlist/card/BT2-108_P1.png", null),
)
