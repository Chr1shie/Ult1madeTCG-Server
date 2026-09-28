package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-18 "Purple Monkey.D.Luffy" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569018): 15 Einträge, davon 5 mit eigenem
// ST18-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt18SetSeed = CardSetSeed(id = "OPST18", name = "ST18: Purple Monkey.D.Luffy", game = "OnePiece", totalCards = 15)

val opSt18CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST18-OP05-060-AA3", "OPST18", "OP05-060", "Monkey.D.Luffy", "Alternate Art 3", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-060_p3.png", null),
    CatalogCardSeed("OPST18-OP05-061-R", "OPST18", "OP05-061", "Uso-Hachi", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-061_r1.png", null),
    CatalogCardSeed("OPST18-OP05-063-R", "OPST18", "OP05-063", "O-Robi", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-063_r1.png", null),
    CatalogCardSeed("OPST18-OP05-066-R", "OPST18", "OP05-066", "Jinbe", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-066_r1.png", null),
    CatalogCardSeed("OPST18-OP05-067-R", "OPST18", "OP05-067", "Zoro-Juurou", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-067_r1.png", null),
    CatalogCardSeed("OPST18-OP05-068-R", "OPST18", "OP05-068", "Chopa-Emon", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-068_r1.png", null),
    CatalogCardSeed("OPST18-OP05-070-R", "OPST18", "OP05-070", "Fra-Nosuke", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-070_r1.png", null),
    CatalogCardSeed("OPST18-OP05-072-R", "OPST18", "OP05-072", "Hone-Kichi", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-072_r1.png", null),
    CatalogCardSeed("OPST18-OP05-076-AA", "OPST18", "OP05-076", "When You're at Sea You Fight against Pirates!!", "Alternate Art", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP05-076_p1.png", null),
    CatalogCardSeed("OPST18-P-041-R", "OPST18", "P-041", "Monkey.D.Luffy", "Reprint", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-041_r1.png", null),
    CatalogCardSeed("OPST18-ST18-001", "OPST18", "ST18-001", "Uso-Hachi", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST18-001.png", null),
    CatalogCardSeed("OPST18-ST18-002", "OPST18", "ST18-002", "O-Nami", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST18-002.png", null),
    CatalogCardSeed("OPST18-ST18-003", "OPST18", "ST18-003", "San-Gorou", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST18-003.png", null),
    CatalogCardSeed("OPST18-ST18-004", "OPST18", "ST18-004", "Zoro-Juurou", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST18-004.png", null),
    CatalogCardSeed("OPST18-ST18-005", "OPST18", "ST18-005", "Luffy-Tarou", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST18-005.png", null),
)
