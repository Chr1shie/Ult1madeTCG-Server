package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-07 "Big Mom Pirates" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569007): 17 Einträge, davon 17 mit eigenem
// ST07-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt07SetSeed = CardSetSeed(id = "OPST07", name = "ST07: Big Mom Pirates", game = "OnePiece", totalCards = 17)

val opSt07CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST07-ST07-001", "OPST07", "ST07-001", "Charlotte Linlin", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-001.png", null),
    CatalogCardSeed("OPST07-ST07-002", "OPST07", "ST07-002", "Charlotte Anana", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-002.png", null),
    CatalogCardSeed("OPST07-ST07-003", "OPST07", "ST07-003", "Charlotte Katakuri", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-003.png", null),
    CatalogCardSeed("OPST07-ST07-004", "OPST07", "ST07-004", "Charlotte Snack", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-004.png", null),
    CatalogCardSeed("OPST07-ST07-005", "OPST07", "ST07-005", "Charlotte Daifuku", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-005.png", null),
    CatalogCardSeed("OPST07-ST07-006", "OPST07", "ST07-006", "Charlotte Flampe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-006.png", null),
    CatalogCardSeed("OPST07-ST07-007", "OPST07", "ST07-007", "Charlotte Brulee", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-007.png", null),
    CatalogCardSeed("OPST07-ST07-008", "OPST07", "ST07-008", "Charlotte Pudding", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-008.png", null),
    CatalogCardSeed("OPST07-ST07-009", "OPST07", "ST07-009", "Charlotte Mont-d'or", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-009.png", null),
    CatalogCardSeed("OPST07-ST07-010", "OPST07", "ST07-010", "Charlotte Linlin", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-010.png", null),
    CatalogCardSeed("OPST07-ST07-011", "OPST07", "ST07-011", "Zeus", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-011.png", null),
    CatalogCardSeed("OPST07-ST07-012", "OPST07", "ST07-012", "Baron Tamago", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-012.png", null),
    CatalogCardSeed("OPST07-ST07-013", "OPST07", "ST07-013", "Prometheus", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-013.png", null),
    CatalogCardSeed("OPST07-ST07-014", "OPST07", "ST07-014", "Pekoms", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-014.png", null),
    CatalogCardSeed("OPST07-ST07-015", "OPST07", "ST07-015", "Soul Pocus", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-015.png", null),
    CatalogCardSeed("OPST07-ST07-016", "OPST07", "ST07-016", "Power Mochi", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-016.png", null),
    CatalogCardSeed("OPST07-ST07-017", "OPST07", "ST07-017", "Queen Mama Chanter", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST07-017.png", null),
)
