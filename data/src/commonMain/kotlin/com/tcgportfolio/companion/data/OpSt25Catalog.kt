package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-25 "Blue Buggy" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569025): 15 Einträge, davon 5 mit eigenem
// ST25-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt25SetSeed = CardSetSeed(id = "OPST25", name = "ST25: Blue Buggy", game = "OnePiece", totalCards = 15)

val opSt25CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST25-OP09-042-AA2", "OPST25", "OP09-042", "Buggy", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-042_p2.png", null),
    CatalogCardSeed("OPST25-OP09-043-R", "OPST25", "OP09-043", "Alvida", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-043_r1.png", null),
    CatalogCardSeed("OPST25-OP09-045-R", "OPST25", "OP09-045", "Cabaji", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-045_r1.png", null),
    CatalogCardSeed("OPST25-OP09-051-R", "OPST25", "OP09-051", "Buggy", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-051_r1.png", null),
    CatalogCardSeed("OPST25-OP09-053-R", "OPST25", "OP09-053", "Mohji", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-053_r1.png", null),
    CatalogCardSeed("OPST25-OP09-054-R", "OPST25", "OP09-054", "Richie", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-054_r1.png", null),
    CatalogCardSeed("OPST25-OP09-055-R", "OPST25", "OP09-055", "Mr.1(Daz.Bonez)", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-055_r1.png", null),
    CatalogCardSeed("OPST25-OP09-056-R", "OPST25", "OP09-056", "Mr.3(Galdino)", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-056_r1.png", null),
    CatalogCardSeed("OPST25-OP09-057-R", "OPST25", "OP09-057", "Cross Guild", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-057_r1.png", null),
    CatalogCardSeed("OPST25-P-084", "OPST25", "P-084", "Buggy", "Normal", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-084.png", null),
    CatalogCardSeed("OPST25-ST25-001", "OPST25", "ST25-001", "Alvida", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST25-001.png", null),
    CatalogCardSeed("OPST25-ST25-002", "OPST25", "ST25-002", "Cabaji", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST25-002.png", null),
    CatalogCardSeed("OPST25-ST25-003", "OPST25", "ST25-003", "Crocodile & Mihawk", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST25-003.png", null),
    CatalogCardSeed("OPST25-ST25-004", "OPST25", "ST25-004", "Buggy", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST25-004.png", null),
    CatalogCardSeed("OPST25-ST25-005", "OPST25", "ST25-005", "Mohji", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST25-005.png", null),
)
