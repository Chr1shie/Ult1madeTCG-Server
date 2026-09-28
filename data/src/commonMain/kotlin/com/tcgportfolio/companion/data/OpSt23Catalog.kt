package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-23 "Red Shanks" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569023): 15 Einträge, davon 5 mit eigenem
// ST23-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt23SetSeed = CardSetSeed(id = "OPST23", name = "ST23: Red Shanks", game = "OnePiece", totalCards = 15)

val opSt23CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST23-OP09-001-AA2", "OPST23", "OP09-001", "Shanks", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-001_p2.png", null),
    CatalogCardSeed("OPST23-OP09-006-R", "OPST23", "OP09-006", "Howling Gab", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-006_r1.png", null),
    CatalogCardSeed("OPST23-OP09-010-R", "OPST23", "OP09-010", "Bonk Punch", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-010_r1.png", null),
    CatalogCardSeed("OPST23-OP09-011-R", "OPST23", "OP09-011", "Hongo", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-011_r1.png", null),
    CatalogCardSeed("OPST23-OP09-012-R", "OPST23", "OP09-012", "Monster", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-012_r1.png", null),
    CatalogCardSeed("OPST23-OP09-013-R", "OPST23", "OP09-013", "Yasopp", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-013_r1.png", null),
    CatalogCardSeed("OPST23-OP09-014-R", "OPST23", "OP09-014", "Limejuice", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-014_r1.png", null),
    CatalogCardSeed("OPST23-OP09-015-R", "OPST23", "OP09-015", "Lucky.Roux", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-015_r1.png", null),
    CatalogCardSeed("OPST23-OP09-016-R", "OPST23", "OP09-016", "Rockstar", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-016_r1.png", null),
    CatalogCardSeed("OPST23-OP09-020-R", "OPST23", "OP09-020", "Come On!! We'll Fight You!!", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-020_r1.png", null),
    CatalogCardSeed("OPST23-ST23-001", "OPST23", "ST23-001", "Uta", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST23-001.png", null),
    CatalogCardSeed("OPST23-ST23-002", "OPST23", "ST23-002", "Shanks", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST23-002.png", null),
    CatalogCardSeed("OPST23-ST23-003", "OPST23", "ST23-003", "Benn.Beckman", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST23-003.png", null),
    CatalogCardSeed("OPST23-ST23-004", "OPST23", "ST23-004", "Monkey.D.Luffy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST23-004.png", null),
    CatalogCardSeed("OPST23-ST23-005", "OPST23", "ST23-005", "Yasopp", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST23-005.png", null),
)
