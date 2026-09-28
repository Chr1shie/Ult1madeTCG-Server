package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-20 "Yellow Charlotte Katakuri" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569020): 15 Einträge, davon 5 mit eigenem
// ST20-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt20SetSeed = CardSetSeed(id = "OPST20", name = "ST20: Yellow Charlotte Katakuri", game = "OnePiece", totalCards = 15)

val opSt20CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST20-OP03-099-AA2", "OPST20", "OP03-099", "Charlotte Katakuri", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-099_p2.png", null),
    CatalogCardSeed("OPST20-OP03-106-R", "OPST20", "OP03-106", "Charlotte Opera", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-106_r1.png", null),
    CatalogCardSeed("OPST20-OP03-107-R", "OPST20", "OP03-107", "Charlotte Galette", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-107_r1.png", null),
    CatalogCardSeed("OPST20-OP03-110-R", "OPST20", "OP03-110", "Charlotte Smoothie", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-110_r1.png", null),
    CatalogCardSeed("OPST20-OP03-112-R", "OPST20", "OP03-112", "Charlotte Pudding", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-112_r1.png", null),
    CatalogCardSeed("OPST20-OP03-115-R", "OPST20", "OP03-115", "Streusen", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-115_r1.png", null),
    CatalogCardSeed("OPST20-OP03-118-R", "OPST20", "OP03-118", "Ikoku Sovereignty", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-118_r1.png", null),
    CatalogCardSeed("OPST20-OP03-121-R", "OPST20", "OP03-121", "Thunder Bolt", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-121_r1.png", null),
    CatalogCardSeed("OPST20-ST07-005-R", "OPST20", "ST07-005", "Charlotte Daifuku", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-005_r1.png", null),
    CatalogCardSeed("OPST20-ST07-014-R", "OPST20", "ST07-014", "Pekoms", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-014_r1.png", null),
    CatalogCardSeed("OPST20-ST20-001", "OPST20", "ST20-001", "Charlotte Katakuri", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST20-001.png", null),
    CatalogCardSeed("OPST20-ST20-002", "OPST20", "ST20-002", "Charlotte Cracker", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST20-002.png", null),
    CatalogCardSeed("OPST20-ST20-003", "OPST20", "ST20-003", "Charlotte Brulee", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST20-003.png", null),
    CatalogCardSeed("OPST20-ST20-004", "OPST20", "ST20-004", "Charlotte Pudding", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST20-004.png", null),
    CatalogCardSeed("OPST20-ST20-005", "OPST20", "ST20-005", "Charlotte Linlin", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST20-005.png", null),
)
