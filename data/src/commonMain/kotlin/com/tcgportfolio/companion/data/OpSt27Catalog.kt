package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-27 "Black Marshall.D.Teach" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569027): 15 Einträge, davon 5 mit eigenem
// ST27-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt27SetSeed = CardSetSeed(id = "OPST27", name = "ST27: Black Marshall.D.Teach", game = "OnePiece", totalCards = 15)

val opSt27CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST27-OP09-081-AA2", "OPST27", "OP09-081", "Marshall.D.Teach", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-081_p2.png", null),
    CatalogCardSeed("OPST27-OP09-083-R", "OPST27", "OP09-083", "Van Augur", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-083_r1.png", null),
    CatalogCardSeed("OPST27-OP09-086-R", "OPST27", "OP09-086", "Jesus Burgess", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-086_r1.png", null),
    CatalogCardSeed("OPST27-OP09-088-R", "OPST27", "OP09-088", "Shiryu", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-088_r1.png", null),
    CatalogCardSeed("OPST27-OP09-089-R", "OPST27", "OP09-089", "Stronger", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-089_r1.png", null),
    CatalogCardSeed("OPST27-OP09-090-R", "OPST27", "OP09-090", "Doc Q", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-090_r1.png", null),
    CatalogCardSeed("OPST27-OP09-091-R", "OPST27", "OP09-091", "Vasco Shot", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-091_r1.png", null),
    CatalogCardSeed("OPST27-OP09-095-R", "OPST27", "OP09-095", "Laffitte", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-095_r1.png", null),
    CatalogCardSeed("OPST27-OP09-099-R", "OPST27", "OP09-099", "Fullalead", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-099_r1.png", null),
    CatalogCardSeed("OPST27-OP10-084-R", "OPST27", "OP10-084", "Sanjuan.Wolf", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP10-084_r1.png", null),
    CatalogCardSeed("OPST27-ST27-001", "OPST27", "ST27-001", "Avalo Pizarro", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST27-001.png", null),
    CatalogCardSeed("OPST27-ST27-002", "OPST27", "ST27-002", "Catarina Devon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST27-002.png", null),
    CatalogCardSeed("OPST27-ST27-003", "OPST27", "ST27-003", "Kuzan", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST27-003.png", null),
    CatalogCardSeed("OPST27-ST27-004", "OPST27", "ST27-004", "Sanjuan.Wolf", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST27-004.png", null),
    CatalogCardSeed("OPST27-ST27-005", "OPST27", "ST27-005", "Marshall.D.Teach", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST27-005.png", null),
)
