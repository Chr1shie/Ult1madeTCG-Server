package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-26 "Purple/Black Monkey.D.Luffy" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569026): 15 Einträge, davon 5 mit eigenem
// ST26-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt26SetSeed = CardSetSeed(id = "OPST26", name = "ST26: Purple/Black Monkey.D.Luffy", game = "OnePiece", totalCards = 15)

val opSt26CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST26-OP05-065-R", "OPST26", "OP05-065", "San-Gorou", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-065_r1.png", null),
    CatalogCardSeed("OPST26-OP05-066-R2", "OPST26", "OP05-066", "Jinbe", "Reprint 2", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-066_r2.png", null),
    CatalogCardSeed("OPST26-OP05-070-R2", "OPST26", "OP05-070", "Fra-Nosuke", "Reprint 2", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-070_r2.png", null),
    CatalogCardSeed("OPST26-OP09-061-AA2", "OPST26", "OP09-061", "Monkey.D.Luffy", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-061_p2.png", null),
    CatalogCardSeed("OPST26-OP09-063-R", "OPST26", "OP09-063", "Usopp", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-063_r1.png", null),
    CatalogCardSeed("OPST26-OP09-070-R", "OPST26", "OP09-070", "Nami", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-070_r1.png", null),
    CatalogCardSeed("OPST26-OP09-076-R", "OPST26", "OP09-076", "Roronoa Zoro", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-076_r1.png", null),
    CatalogCardSeed("OPST26-OP09-077-R", "OPST26", "OP09-077", "Gum-Gum Lightning", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-077_r1.png", null),
    CatalogCardSeed("OPST26-OP09-078-AA", "OPST26", "OP09-078", "Gum-Gum Giant", "Alternate Art", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-078_p1.png", null),
    CatalogCardSeed("OPST26-ST14-010-R", "OPST26", "ST14-010", "Brook", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-010_r1.png", null),
    CatalogCardSeed("OPST26-ST26-001", "OPST26", "ST26-001", "Soba Mask", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST26-001.png", null),
    CatalogCardSeed("OPST26-ST26-002", "OPST26", "ST26-002", "Tony Tony.Chopper", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST26-002.png", null),
    CatalogCardSeed("OPST26-ST26-003", "OPST26", "ST26-003", "Nico Robin", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST26-003.png", null),
    CatalogCardSeed("OPST26-ST26-004", "OPST26", "ST26-004", "General Franky", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST26-004.png", null),
    CatalogCardSeed("OPST26-ST26-005", "OPST26", "ST26-005", "Monkey.D.Luffy", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST26-005.png", null),
)
