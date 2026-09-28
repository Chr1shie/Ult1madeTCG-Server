package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-33 "Blue Kuzan" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569033): 15 Einträge, davon 5 mit eigenem
// ST33-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt33SetSeed = CardSetSeed(id = "OPST33", name = "ST33: Blue Kuzan", game = "OnePiece", totalCards = 15)

val opSt33CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST33-EB04-026-R", "OPST33", "EB04-026", "Bluegrass", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/EB04-026_r1.png", null),
    CatalogCardSeed("OPST33-EB04-028-AA2", "OPST33", "EB04-028", "Ice Time", "Alternate Art 2", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/EB04-028_p2.png", null),
    CatalogCardSeed("OPST33-OP12-040-AA2", "OPST33", "OP12-040", "Kuzan", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-040_p2.png", null),
    CatalogCardSeed("OPST33-OP12-043-R", "OPST33", "OP12-043", "Kuzan", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-043_r1.png", null),
    CatalogCardSeed("OPST33-OP12-045-R", "OPST33", "OP12-045", "Jango", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-045_r1.png", null),
    CatalogCardSeed("OPST33-OP12-046-R", "OPST33", "OP12-046", "Zephyr(Navy)", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-046_r1.png", null),
    CatalogCardSeed("OPST33-OP12-047-R", "OPST33", "OP12-047", "Sengoku", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-047_r1.png", null),
    CatalogCardSeed("OPST33-OP12-050-R", "OPST33", "OP12-050", "Jaguar.D.Saul", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-050_r1.png", null),
    CatalogCardSeed("OPST33-OP12-052-R", "OPST33", "OP12-052", "Fullbody", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-052_r1.png", null),
    CatalogCardSeed("OPST33-OP12-057-R", "OPST33", "OP12-057", "Ice Block Pheasant Peck", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-057_r1.png", null),
    CatalogCardSeed("OPST33-ST33-001", "OPST33", "ST33-001", "Koby", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST33-001.png", null),
    CatalogCardSeed("OPST33-ST33-002", "OPST33", "ST33-002", "Sakazuki", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST33-002.png", null),
    CatalogCardSeed("OPST33-ST33-003", "OPST33", "ST33-003", "Smoker", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST33-003.png", null),
    CatalogCardSeed("OPST33-ST33-004", "OPST33", "ST33-004", "Borsalino", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST33-004.png", null),
    CatalogCardSeed("OPST33-ST33-005", "OPST33", "ST33-005", "Monkey.D.Garp", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST33-005.png", null),
)
