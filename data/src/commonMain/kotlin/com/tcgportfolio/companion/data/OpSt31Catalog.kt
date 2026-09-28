package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-31 "Red Monkey.D.Luffy" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569031): 15 Einträge, davon 5 mit eigenem
// ST31-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt31SetSeed = CardSetSeed(id = "OPST31", name = "ST31: Red Monkey.D.Luffy", game = "OnePiece", totalCards = 15)

val opSt31CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST31-OP01-016-AA9", "OPST31", "OP01-016", "Nami", "Alternate Art 9", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP01-016_p9.png", null),
    CatalogCardSeed("OPST31-OP04-016-AA3", "OPST31", "OP04-016", "Bad Manners Kick Course", "Alternate Art 3", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP04-016_p3.png", null),
    CatalogCardSeed("OPST31-OP11-003-R", "OPST31", "OP11-003", "Usopp", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-003_r1.png", null),
    CatalogCardSeed("OPST31-OP11-009-R", "OPST31", "OP11-009", "Nico Robin", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-009_r1.png", null),
    CatalogCardSeed("OPST31-OP11-012-R", "OPST31", "OP11-012", "Franky", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-012_r1.png", null),
    CatalogCardSeed("OPST31-OP13-021-R", "OPST31", "OP13-021", "Gum-Gum Gatling Gun", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP13-021_r1.png", null),
    CatalogCardSeed("OPST31-OP14-015-R", "OPST31", "OP14-015", "Roronoa Zoro", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP14-015_r1.png", null),
    CatalogCardSeed("OPST31-P-101-R", "OPST31", "P-101", "Tony Tony.Chopper", "Reprint", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-101_r1.png", null),
    CatalogCardSeed("OPST31-ST21-001-R", "OPST31", "ST21-001", "Monkey.D.Luffy", "Reprint", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-001_r1.png", null),
    CatalogCardSeed("OPST31-ST23-004-R", "OPST31", "ST23-004", "Monkey.D.Luffy", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST23-004_r1.png", null),
    CatalogCardSeed("OPST31-ST31-001", "OPST31", "ST31-001", "Sanji", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST31-001.png", null),
    CatalogCardSeed("OPST31-ST31-002", "OPST31", "ST31-002", "Jinbe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST31-002.png", null),
    CatalogCardSeed("OPST31-ST31-003", "OPST31", "ST31-003", "Brook", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST31-003.png", null),
    CatalogCardSeed("OPST31-ST31-004", "OPST31", "ST31-004", "Monkey.D.Luffy", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST31-004.png", null),
    CatalogCardSeed("OPST31-ST31-005", "OPST31", "ST31-005", "Thousand Sunny", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST31-005.png", null),
)
