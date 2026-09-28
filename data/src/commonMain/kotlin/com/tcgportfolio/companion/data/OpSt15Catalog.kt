package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-15 "Red Edward.Newgate" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569015): 15 Einträge, davon 5 mit eigenem
// ST15-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt15SetSeed = CardSetSeed(id = "OPST15", name = "ST15: Red Edward.Newgate", game = "OnePiece", totalCards = 15)

val opSt15CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST15-OP02-001-AA2", "OPST15", "OP02-001", "Edward.Newgate", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-001_p2.png", null),
    CatalogCardSeed("OPST15-OP02-008-R", "OPST15", "OP02-008", "Jozu", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-008_r1.png", null),
    CatalogCardSeed("OPST15-OP02-018-R", "OPST15", "OP02-018", "Marco", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-018_r1.png", null),
    CatalogCardSeed("OPST15-OP02-019-R", "OPST15", "OP02-019", "Rakuyo", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-019_r1.png", null),
    CatalogCardSeed("OPST15-OP02-023-R", "OPST15", "OP02-023", "You May Be a Fool...but I Still Love You", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-023_r1.png", null),
    CatalogCardSeed("OPST15-OP03-003-R", "OPST15", "OP03-003", "Izo", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-003_r1.png", null),
    CatalogCardSeed("OPST15-OP03-006-R", "OPST15", "OP03-006", "Speed Jil", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-006_r1.png", null),
    CatalogCardSeed("OPST15-OP03-007-R", "OPST15", "OP03-007", "Namule", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-007_r1.png", null),
    CatalogCardSeed("OPST15-OP03-009-R", "OPST15", "OP03-009", "Haruta", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-009_r1.png", null),
    CatalogCardSeed("OPST15-OP03-010-R", "OPST15", "OP03-010", "Fossa", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-010_r1.png", null),
    CatalogCardSeed("OPST15-ST15-001", "OPST15", "ST15-001", "Atmos", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST15-001.png", null),
    CatalogCardSeed("OPST15-ST15-002", "OPST15", "ST15-002", "Edward.Newgate", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST15-002.png", null),
    CatalogCardSeed("OPST15-ST15-003", "OPST15", "ST15-003", "Kingdew", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST15-003.png", null),
    CatalogCardSeed("OPST15-ST15-004", "OPST15", "ST15-004", "Thatch", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST15-004.png", null),
    CatalogCardSeed("OPST15-ST15-005", "OPST15", "ST15-005", "Portgas.D.Ace", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST15-005.png", null),
)
