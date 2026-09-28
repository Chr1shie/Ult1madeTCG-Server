package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-35 "Red/Black Sabo" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569035): 15 Einträge, davon 5 mit eigenem
// ST35-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt35SetSeed = CardSetSeed(id = "OPST35", name = "ST35: Red/Black Sabo", game = "OnePiece", totalCards = 15)

val opSt35CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST35-OP12-090-R", "OPST35", "OP12-090", "Belo Betty", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-090_r1.png", null),
    CatalogCardSeed("OPST35-OP12-093-R", "OPST35", "OP12-093", "Morley", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-093_r1.png", null),
    CatalogCardSeed("OPST35-OP12-098-R", "OPST35", "OP12-098", "Hair Removal Fist", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-098_r1.png", null),
    CatalogCardSeed("OPST35-OP13-004-AA2", "OPST35", "OP13-004", "Sabo", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP13-004_p2.png", null),
    CatalogCardSeed("OPST35-OP13-005-R", "OPST35", "OP13-005", "Inazuma", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP13-005_r1.png", null),
    CatalogCardSeed("OPST35-OP13-008-R", "OPST35", "OP13-008", "Emporio.Ivankov", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP13-008_r1.png", null),
    CatalogCardSeed("OPST35-OP13-017-R", "OPST35", "OP13-017", "Monkey.D.Dragon", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP13-017_r1.png", null),
    CatalogCardSeed("OPST35-OP13-019-AA2", "OPST35", "OP13-019", "But Ace Here Said You Deserved It!!", "Alternate Art 2", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP13-019_p2.png", null),
    CatalogCardSeed("OPST35-OP13-081-R", "OPST35", "OP13-081", "Koala", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP13-081_r1.png", null),
    CatalogCardSeed("OPST35-P-105-R", "OPST35", "P-105", "Sabo", "Reprint", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-105_r1.png", null),
    CatalogCardSeed("OPST35-ST35-001", "OPST35", "ST35-001", "Hack", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST35-001.png", null),
    CatalogCardSeed("OPST35-ST35-002", "OPST35", "ST35-002", "Lindbergh", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST35-002.png", null),
    CatalogCardSeed("OPST35-ST35-003", "OPST35", "ST35-003", "Karasu", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST35-003.png", null),
    CatalogCardSeed("OPST35-ST35-004", "OPST35", "ST35-004", "Koala", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST35-004.png", null),
    CatalogCardSeed("OPST35-ST35-005", "OPST35", "ST35-005", "Bartholomew Kuma", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST35-005.png", null),
)
