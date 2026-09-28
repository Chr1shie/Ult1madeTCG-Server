package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-32 "Green Roronoa Zoro" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569032): 15 Einträge, davon 5 mit eigenem
// ST32-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt32SetSeed = CardSetSeed(id = "OPST32", name = "ST32: Green Roronoa Zoro", game = "OnePiece", totalCards = 15)

val opSt32CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST32-OP10-036-R", "OPST32", "OP10-036", "Perona", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP10-036_r1.png", null),
    CatalogCardSeed("OPST32-OP12-020-AA5", "OPST32", "OP12-020", "Roronoa Zoro", "Alternate Art 5", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-020_p5.png", null),
    CatalogCardSeed("OPST32-OP12-023-R", "OPST32", "OP12-023", "Kawamatsu", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-023_r1.png", null),
    CatalogCardSeed("OPST32-OP12-026-R", "OPST32", "OP12-026", "Kuina", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-026_r1.png", null),
    CatalogCardSeed("OPST32-OP12-027-R", "OPST32", "OP12-027", "Koushirou", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-027_r1.png", null),
    CatalogCardSeed("OPST32-OP12-028-R", "OPST32", "OP12-028", "Kouzuki Hiyori", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-028_r1.png", null),
    CatalogCardSeed("OPST32-OP12-031-R", "OPST32", "OP12-031", "Tashigi", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-031_r1.png", null),
    CatalogCardSeed("OPST32-OP12-039-AA2", "OPST32", "OP12-039", "Luffy Is the Man Who Will Become the King of Pirates!!!", "Alternate Art 2", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-039_p2.png", null),
    CatalogCardSeed("OPST32-OP15-036-R", "OPST32", "OP15-036", "Ryuma", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP15-036_r1.png", null),
    CatalogCardSeed("OPST32-ST24-005-R", "OPST32", "ST24-005", "X.Drake", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST24-005_r1.png", null),
    CatalogCardSeed("OPST32-ST32-001", "OPST32", "ST32-001", "Kin'emon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST32-001.png", null),
    CatalogCardSeed("OPST32-ST32-002", "OPST32", "ST32-002", "Kouzuki Oden", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST32-002.png", null),
    CatalogCardSeed("OPST32-ST32-003", "OPST32", "ST32-003", "Dracule Mihawk", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST32-003.png", null),
    CatalogCardSeed("OPST32-ST32-004", "OPST32", "ST32-004", "Silvers Rayleigh", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST32-004.png", null),
    CatalogCardSeed("OPST32-ST32-005", "OPST32", "ST32-005", "Roronoa Zoro", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST32-005.png", null),
)
