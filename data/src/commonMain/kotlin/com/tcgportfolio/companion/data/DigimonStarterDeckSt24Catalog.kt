package com.tcgportfolio.companion.data

// ST24: Digimon Data Squad - offizielle Kartenliste world.digimoncard.com (Stand 28.09.2026),
// Format wie TimelessBondsCatalog.kt (_P1 -> -AA "Alternate Art", _P2 -> -AA2 ...).
// Generiert von tools/catalog/generate_digimon_new_sets.py.
// Offizielle Produktliste: 22 Einträge; hier 22.
// Starterdeck als eigenes Set: Set-Id "DGST24" (ohne Bindestrich), alle Karten-Ids mit
// "DGST24-" vorangestellt (die gedruckte Nummer bleibt in number, z.B. "ST24-01").
// 6 Reprints fremder Nummern (Alternative-Art-Drucke aus diesem Produkt), Id
// "DGST24-<Nummer>-AA<n>".
val digimonStarterDeckSt24SetSeed = CardSetSeed(id = "DGST24", name = "ST24: Digimon Data Squad", game = "Digimon", totalCards = 22)

val digimonStarterDeckSt24CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("DGST24-ST24-01", "DGST24", "ST24-01", "Koromon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST24-01.png", null),
    CatalogCardSeed("DGST24-ST24-02", "DGST24", "ST24-02", "Gaomon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST24-02.png", null),
    CatalogCardSeed("DGST24-ST24-03", "DGST24", "ST24-03", "Gaogamon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST24-03.png", null),
    CatalogCardSeed("DGST24-ST24-04", "DGST24", "ST24-04", "Agumon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST24-04.png", null),
    CatalogCardSeed("DGST24-ST24-04-AA", "DGST24", "ST24-04", "Agumon", "Alternate Art", "R", "https://world.digimoncard.com/images/cardlist/card/ST24-04_P1.png", null),
    CatalogCardSeed("DGST24-ST24-05", "DGST24", "ST24-05", "GeoGreymon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST24-05.png", null),
    CatalogCardSeed("DGST24-ST24-06", "DGST24", "ST24-06", "RizeGreymon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST24-06.png", null),
    CatalogCardSeed("DGST24-ST24-07", "DGST24", "ST24-07", "ShineGreymon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST24-07.png", null),
    CatalogCardSeed("DGST24-ST24-08", "DGST24", "ST24-08", "Lalamon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST24-08.png", null),
    CatalogCardSeed("DGST24-ST24-09", "DGST24", "ST24-09", "Sunflowmon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST24-09.png", null),
    CatalogCardSeed("DGST24-ST24-10", "DGST24", "ST24-10", "Lilamon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST24-10.png", null),
    CatalogCardSeed("DGST24-ST24-11", "DGST24", "ST24-11", "Rosemon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST24-11.png", null),
    CatalogCardSeed("DGST24-ST24-12", "DGST24", "ST24-12", "Falcomon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST24-12.png", null),
    CatalogCardSeed("DGST24-ST24-13", "DGST24", "ST24-13", "Marcus Damon & Thomas H. Norstein", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST24-13.png", null),
    CatalogCardSeed("DGST24-ST24-14", "DGST24", "ST24-14", "Yoshino Fujieda & Keenan Crier", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST24-14.png", null),
    CatalogCardSeed("DGST24-ST24-15", "DGST24", "ST24-15", "DNA Charge", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST24-15.png", null),
    CatalogCardSeed("DGST24-LM-033-AA2", "DGST24", "LM-033", "Garnet Memory Boost!", "Alternate Art 2", "P", "https://world.digimoncard.com/images/cardlist/card/LM-033_P2.png", null),
    CatalogCardSeed("DGST24-LM-034-AA2", "DGST24", "LM-034", "Wisteria Memory Boost!", "Alternate Art 2", "P", "https://world.digimoncard.com/images/cardlist/card/LM-034_P2.png", null),
    CatalogCardSeed("DGST24-LM-035-AA2", "DGST24", "LM-035", "Amber Memory Boost!", "Alternate Art 2", "P", "https://world.digimoncard.com/images/cardlist/card/LM-035_P2.png", null),
    CatalogCardSeed("DGST24-LM-036-AA2", "DGST24", "LM-036", "Jade Memory Boost!", "Alternate Art 2", "P", "https://world.digimoncard.com/images/cardlist/card/LM-036_P2.png", null),
    CatalogCardSeed("DGST24-LM-037-AA2", "DGST24", "LM-037", "Sepia Memory Boost!", "Alternate Art 2", "P", "https://world.digimoncard.com/images/cardlist/card/LM-037_P2.png", null),
    CatalogCardSeed("DGST24-LM-038-AA2", "DGST24", "LM-038", "Grape Memory Boost!", "Alternate Art 2", "P", "https://world.digimoncard.com/images/cardlist/card/LM-038_P2.png", null),
)
