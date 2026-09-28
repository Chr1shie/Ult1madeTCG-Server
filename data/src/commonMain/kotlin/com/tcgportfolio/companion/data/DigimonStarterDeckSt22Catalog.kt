package com.tcgportfolio.companion.data

// ST22: Advanced Deck Amethyst Mandala - offizielle Kartenliste world.digimoncard.com (Stand 28.09.2026),
// Format wie TimelessBondsCatalog.kt (_P1 -> -AA "Alternate Art", _P2 -> -AA2 ...).
// Generiert von tools/catalog/generate_digimon_new_sets.py.
// Offizielle Produktliste: 26 Einträge; hier 24.
// Starterdeck als eigenes Set: Set-Id "DGST22" (ohne Bindestrich), alle Karten-Ids mit
// "DGST22-" vorangestellt (die gedruckte Nummer bleibt in number, z.B. "ST22-01").
// 9 Reprints fremder Nummern (Alternative-Art-Drucke aus diesem Produkt), Id
// "DGST22-<Nummer>-AA<n>".
// Nicht übernommen: 2x token: ST22-TOKEN01, ST22-TOKEN02
val digimonStarterDeckSt22SetSeed = CardSetSeed(id = "DGST22", name = "ST22: Advanced Deck Amethyst Mandala", game = "Digimon", totalCards = 24)

val digimonStarterDeckSt22CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("DGST22-ST22-01", "DGST22", "ST22-01", "Viximon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST22-01.png", null),
    CatalogCardSeed("DGST22-ST22-02", "DGST22", "ST22-02", "Renamon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST22-02.png", null),
    CatalogCardSeed("DGST22-ST22-02-AA", "DGST22", "ST22-02", "Renamon", "Alternate Art", "SR", "https://world.digimoncard.com/images/cardlist/card/ST22-02_P1.png", null),
    CatalogCardSeed("DGST22-ST22-03", "DGST22", "ST22-03", "Kyubimon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST22-03.png", null),
    CatalogCardSeed("DGST22-ST22-04", "DGST22", "ST22-04", "Taomon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST22-04.png", null),
    CatalogCardSeed("DGST22-ST22-05", "DGST22", "ST22-05", "Sakuyamon ACE", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST22-05.png", null),
    CatalogCardSeed("DGST22-ST22-06", "DGST22", "ST22-06", "Sakuyamon: Maid Mode", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST22-06.png", null),
    CatalogCardSeed("DGST22-ST22-07", "DGST22", "ST22-07", "Rika Nonaka", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST22-07.png", null),
    CatalogCardSeed("DGST22-ST22-08", "DGST22", "ST22-08", "Offensive Plug-In V", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST22-08.png", null),
    CatalogCardSeed("DGST22-ST22-09", "DGST22", "ST22-09", "High-Speed Plug-In H", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST22-09.png", null),
    CatalogCardSeed("DGST22-ST22-10", "DGST22", "ST22-10", "Amethyst Mandala", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST22-10.png", null),
    CatalogCardSeed("DGST22-ST22-11", "DGST22", "ST22-11", "Defense Plug-In F", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST22-11.png", null),
    CatalogCardSeed("DGST22-ST22-12", "DGST22", "ST22-12", "DoGatchmon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST22-12.png", null),
    CatalogCardSeed("DGST22-ST22-13", "DGST22", "ST22-13", "GrandGalemon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST22-13.png", null),
    CatalogCardSeed("DGST22-ST22-14", "DGST22", "ST22-14", "Barbamon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST22-14.png", null),
    CatalogCardSeed("DGST22-BT17-031-AA2", "DGST22", "BT17-031", "Renamon", "Alternate Art 2", "C", "https://world.digimoncard.com/images/cardlist/card/BT17-031_P2.png", null),
    CatalogCardSeed("DGST22-BT17-035-AA2", "DGST22", "BT17-035", "Taomon", "Alternate Art 2", "C", "https://world.digimoncard.com/images/cardlist/card/BT17-035_P2.png", null),
    CatalogCardSeed("DGST22-BT19-034-AA", "DGST22", "BT19-034", "Kyubimon", "Alternate Art", "C", "https://world.digimoncard.com/images/cardlist/card/BT19-034_P1.png", null),
    CatalogCardSeed("DGST22-BT19-037-AA3", "DGST22", "BT19-037", "Taomon ACE", "Alternate Art 3", "SR", "https://world.digimoncard.com/images/cardlist/card/BT19-037_P3.png", null),
    CatalogCardSeed("DGST22-BT19-083-AA2", "DGST22", "BT19-083", "Rika Nonaka", "Alternate Art 2", "R", "https://world.digimoncard.com/images/cardlist/card/BT19-083_P2.png", null),
    CatalogCardSeed("DGST22-EX2-019-AA2", "DGST22", "EX2-019", "Renamon", "Alternate Art 2", "R", "https://world.digimoncard.com/images/cardlist/card/EX2-019_P2.png", null),
    CatalogCardSeed("DGST22-EX8-037-AA2", "DGST22", "EX8-037", "Sakuyamon (X Antibody)", "Alternate Art 2", "SR", "https://world.digimoncard.com/images/cardlist/card/EX8-037_P2.png", null),
    CatalogCardSeed("DGST22-LM-029-AA3", "DGST22", "LM-029", "Yellow Scramble", "Alternate Art 3", "P", "https://world.digimoncard.com/images/cardlist/card/LM-029_P3.png", null),
    CatalogCardSeed("DGST22-P-105-AA4", "DGST22", "P-105", "Physical Training", "Alternate Art 4", "P", "https://world.digimoncard.com/images/cardlist/card/P-105_P4.png", null),
)
