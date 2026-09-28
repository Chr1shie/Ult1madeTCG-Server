package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-29 "Egghead" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569029): 31 Einträge, davon 31 mit eigenem
// ST29-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt29SetSeed = CardSetSeed(id = "OPST29", name = "ST29: Egghead", game = "OnePiece", totalCards = 31)

val opSt29CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST29-ST29-001", "OPST29", "ST29-001", "Monkey.D.Luffy", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-001.png", null),
    CatalogCardSeed("OPST29-ST29-001-AA", "OPST29", "ST29-001", "Monkey.D.Luffy", "Alternate Art", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-001_p1.png", null),
    CatalogCardSeed("OPST29-ST29-002", "OPST29", "ST29-002", "Usopp", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-002.png", null),
    CatalogCardSeed("OPST29-ST29-002-AA", "OPST29", "ST29-002", "Usopp", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-002_p1.png", null),
    CatalogCardSeed("OPST29-ST29-003", "OPST29", "ST29-003", "Kaku", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-003.png", null),
    CatalogCardSeed("OPST29-ST29-003-AA", "OPST29", "ST29-003", "Kaku", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-003_p1.png", null),
    CatalogCardSeed("OPST29-ST29-004", "OPST29", "ST29-004", "Sanji", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-004.png", null),
    CatalogCardSeed("OPST29-ST29-004-AA", "OPST29", "ST29-004", "Sanji", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-004_p1.png", null),
    CatalogCardSeed("OPST29-ST29-005", "OPST29", "ST29-005", "Jinbe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-005.png", null),
    CatalogCardSeed("OPST29-ST29-005-AA", "OPST29", "ST29-005", "Jinbe", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-005_p1.png", null),
    CatalogCardSeed("OPST29-ST29-006", "OPST29", "ST29-006", "Stussy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-006.png", null),
    CatalogCardSeed("OPST29-ST29-006-AA", "OPST29", "ST29-006", "Stussy", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-006_p1.png", null),
    CatalogCardSeed("OPST29-ST29-007", "OPST29", "ST29-007", "Tony Tony.Chopper", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-007.png", null),
    CatalogCardSeed("OPST29-ST29-007-AA", "OPST29", "ST29-007", "Tony Tony.Chopper", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-007_p1.png", null),
    CatalogCardSeed("OPST29-ST29-008", "OPST29", "ST29-008", "Nami", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-008.png", null),
    CatalogCardSeed("OPST29-ST29-008-AA", "OPST29", "ST29-008", "Nami", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-008_p1.png", null),
    CatalogCardSeed("OPST29-ST29-009", "OPST29", "ST29-009", "Nico Robin", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-009.png", null),
    CatalogCardSeed("OPST29-ST29-009-AA", "OPST29", "ST29-009", "Nico Robin", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-009_p1.png", null),
    CatalogCardSeed("OPST29-ST29-010", "OPST29", "ST29-010", "Franky", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-010.png", null),
    CatalogCardSeed("OPST29-ST29-010-AA", "OPST29", "ST29-010", "Franky", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-010_p1.png", null),
    CatalogCardSeed("OPST29-ST29-011", "OPST29", "ST29-011", "Brook", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-011.png", null),
    CatalogCardSeed("OPST29-ST29-011-AA", "OPST29", "ST29-011", "Brook", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-011_p1.png", null),
    CatalogCardSeed("OPST29-ST29-012", "OPST29", "ST29-012", "Monkey.D.Luffy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-012.png", null),
    CatalogCardSeed("OPST29-ST29-012-AA", "OPST29", "ST29-012", "Monkey.D.Luffy", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-012_p1.png", null),
    CatalogCardSeed("OPST29-ST29-013", "OPST29", "ST29-013", "Rob Lucci", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-013.png", null),
    CatalogCardSeed("OPST29-ST29-013-AA", "OPST29", "ST29-013", "Rob Lucci", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-013_p1.png", null),
    CatalogCardSeed("OPST29-ST29-014", "OPST29", "ST29-014", "Roronoa Zoro", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-014.png", null),
    CatalogCardSeed("OPST29-ST29-014-AA", "OPST29", "ST29-014", "Roronoa Zoro", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-014_p1.png", null),
    CatalogCardSeed("OPST29-ST29-015", "OPST29", "ST29-015", "Raw Heat Strike", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-015.png", null),
    CatalogCardSeed("OPST29-ST29-016", "OPST29", "ST29-016", "Kizaru!! Compared to Two Years Ago We're a Hundred Times Stronger Now!!", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-016.png", null),
    CatalogCardSeed("OPST29-ST29-017", "OPST29", "ST29-017", "Iai Death Lion Song", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST29-017.png", null),
)
