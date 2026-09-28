package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-34 "Purple Charlotte Katakuri" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569034): 15 Einträge, davon 5 mit eigenem
// ST34-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt34SetSeed = CardSetSeed(id = "OPST34", name = "ST34: Purple Charlotte Katakuri", game = "OnePiece", totalCards = 15)

val opSt34CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST34-EB03-032-R", "OPST34", "EB03-032", "Charlotte Flampe", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/EB03-032_r1.png", null),
    CatalogCardSeed("OPST34-EB03-035-R", "OPST34", "EB03-035", "Charlotte Pudding", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/EB03-035_r1.png", null),
    CatalogCardSeed("OPST34-OP11-062-AA2", "OPST34", "OP11-062", "Charlotte Katakuri", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-062_p2.png", null),
    CatalogCardSeed("OPST34-OP11-065-R", "OPST34", "OP11-065", "Charlotte Anana", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-065_r1.png", null),
    CatalogCardSeed("OPST34-OP11-066-R", "OPST34", "OP11-066", "Charlotte Oven", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-066_r1.png", null),
    CatalogCardSeed("OPST34-OP11-068-R", "OPST34", "OP11-068", "Charlotte Daifuku", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-068_r1.png", null),
    CatalogCardSeed("OPST34-OP11-071-R", "OPST34", "OP11-071", "Charlotte Perospero", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-071_r1.png", null),
    CatalogCardSeed("OPST34-OP11-079-R", "OPST34", "OP11-079", "When Two Men Are Fighting the Last Thing I Need Is Some Half-Hearted Assistance!!!!", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-079_r1.png", null),
    CatalogCardSeed("OPST34-OP11-081-R", "OPST34", "OP11-081", "Cognac Mama-Mash", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP11-081_r1.png", null),
    CatalogCardSeed("OPST34-P-090-R", "OPST34", "P-090", "Charlotte Smoothie", "Reprint", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-090_r1.png", null),
    CatalogCardSeed("OPST34-ST34-001", "OPST34", "ST34-001", "Charlotte Katakuri", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST34-001.png", null),
    CatalogCardSeed("OPST34-ST34-002", "OPST34", "ST34-002", "Charlotte Cracker", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST34-002.png", null),
    CatalogCardSeed("OPST34-ST34-003", "OPST34", "ST34-003", "Charlotte Brulee", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST34-003.png", null),
    CatalogCardSeed("OPST34-ST34-004", "OPST34", "ST34-004", "Charlotte Linlin", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST34-004.png", null),
    CatalogCardSeed("OPST34-ST34-005", "OPST34", "ST34-005", "Baron Tamago & Pekoms", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST34-005.png", null),
)
