package com.tcgportfolio.companion.data

// ST23: Digimon Beatbreak - offizielle Kartenliste world.digimoncard.com (Stand 28.09.2026),
// Format wie TimelessBondsCatalog.kt (_P1 -> -AA "Alternate Art", _P2 -> -AA2 ...).
// Generiert von tools/catalog/generate_digimon_new_sets.py.
// Offizielle Produktliste: 22 Einträge; hier 22.
// Starterdeck als eigenes Set: Set-Id "DGST23" (ohne Bindestrich), alle Karten-Ids mit
// "DGST23-" vorangestellt (die gedruckte Nummer bleibt in number, z.B. "ST23-01").
// 6 Reprints fremder Nummern (Alternative-Art-Drucke aus diesem Produkt), Id
// "DGST23-<Nummer>-AA<n>".
val digimonStarterDeckSt23SetSeed = CardSetSeed(id = "DGST23", name = "ST23: Digimon Beatbreak", game = "Digimon", totalCards = 22)

val digimonStarterDeckSt23CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("DGST23-ST23-01", "DGST23", "ST23-01", "Kekkomon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST23-01.png", null),
    CatalogCardSeed("DGST23-ST23-02", "DGST23", "ST23-02", "Liollmon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST23-02.png", null),
    CatalogCardSeed("DGST23-ST23-03", "DGST23", "ST23-03", "Cougarmon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST23-03.png", null),
    CatalogCardSeed("DGST23-ST23-04", "DGST23", "ST23-04", "Murasamemon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST23-04.png", null),
    CatalogCardSeed("DGST23-ST23-05", "DGST23", "ST23-05", "Habakirimon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST23-05.png", null),
    CatalogCardSeed("DGST23-ST23-06", "DGST23", "ST23-06", "Gekkomon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST23-06.png", null),
    CatalogCardSeed("DGST23-ST23-06-AA", "DGST23", "ST23-06", "Gekkomon", "Alternate Art", "R", "https://world.digimoncard.com/images/cardlist/card/ST23-06_P1.png", null),
    CatalogCardSeed("DGST23-ST23-07", "DGST23", "ST23-07", "Armalizamon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST23-07.png", null),
    CatalogCardSeed("DGST23-ST23-08", "DGST23", "ST23-08", "Monarchlizamon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST23-08.png", null),
    CatalogCardSeed("DGST23-ST23-09", "DGST23", "ST23-09", "Atratusmon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST23-09.png", null),
    CatalogCardSeed("DGST23-ST23-10", "DGST23", "ST23-10", "Pristimon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST23-10.png", null),
    CatalogCardSeed("DGST23-ST23-11", "DGST23", "ST23-11", "Wolvermon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST23-11.png", null),
    CatalogCardSeed("DGST23-ST23-12", "DGST23", "ST23-12", "Chiropmon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST23-12.png", null),
    CatalogCardSeed("DGST23-ST23-13", "DGST23", "ST23-13", "Tomoro Tenma & Kyo Sawashiro", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST23-13.png", null),
    CatalogCardSeed("DGST23-ST23-14", "DGST23", "ST23-14", "Reina Sakuya & Makoto Kuonji", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST23-14.png", null),
    CatalogCardSeed("DGST23-ST23-15", "DGST23", "ST23-15", "e-Pulse", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST23-15.png", null),
    CatalogCardSeed("DGST23-LM-033-AA", "DGST23", "LM-033", "Garnet Memory Boost!", "Alternate Art", "P", "https://world.digimoncard.com/images/cardlist/card/LM-033_P1.png", null),
    CatalogCardSeed("DGST23-LM-034-AA", "DGST23", "LM-034", "Wisteria Memory Boost!", "Alternate Art", "P", "https://world.digimoncard.com/images/cardlist/card/LM-034_P1.png", null),
    CatalogCardSeed("DGST23-LM-035-AA", "DGST23", "LM-035", "Amber Memory Boost!", "Alternate Art", "P", "https://world.digimoncard.com/images/cardlist/card/LM-035_P1.png", null),
    CatalogCardSeed("DGST23-LM-036-AA", "DGST23", "LM-036", "Jade Memory Boost!", "Alternate Art", "P", "https://world.digimoncard.com/images/cardlist/card/LM-036_P1.png", null),
    CatalogCardSeed("DGST23-LM-037-AA", "DGST23", "LM-037", "Sepia Memory Boost!", "Alternate Art", "P", "https://world.digimoncard.com/images/cardlist/card/LM-037_P1.png", null),
    CatalogCardSeed("DGST23-LM-038-AA", "DGST23", "LM-038", "Grape Memory Boost!", "Alternate Art", "P", "https://world.digimoncard.com/images/cardlist/card/LM-038_P1.png", null),
)
