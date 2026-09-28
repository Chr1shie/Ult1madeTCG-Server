package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-05 "One Piece Film Edition" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569005): 17 Einträge, davon 17 mit eigenem
// ST05-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt05SetSeed = CardSetSeed(id = "OPST05", name = "ST05: One Piece Film Edition", game = "OnePiece", totalCards = 17)

val opSt05CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST05-ST05-001", "OPST05", "ST05-001", "Shanks", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-001.png", null),
    CatalogCardSeed("OPST05-ST05-002", "OPST05", "ST05-002", "Ain", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-002.png", null),
    CatalogCardSeed("OPST05-ST05-003", "OPST05", "ST05-003", "Ann", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-003.png", null),
    CatalogCardSeed("OPST05-ST05-004", "OPST05", "ST05-004", "Uta", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-004.png", null),
    CatalogCardSeed("OPST05-ST05-005", "OPST05", "ST05-005", "Carina", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-005.png", null),
    CatalogCardSeed("OPST05-ST05-006", "OPST05", "ST05-006", "Gild Tesoro", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-006.png", null),
    CatalogCardSeed("OPST05-ST05-007", "OPST05", "ST05-007", "Gordon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-007.png", null),
    CatalogCardSeed("OPST05-ST05-008", "OPST05", "ST05-008", "Shiki", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-008.png", null),
    CatalogCardSeed("OPST05-ST05-009", "OPST05", "ST05-009", "Scarlet", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-009.png", null),
    CatalogCardSeed("OPST05-ST05-010", "OPST05", "ST05-010", "Zephyr", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-010.png", null),
    CatalogCardSeed("OPST05-ST05-011", "OPST05", "ST05-011", "Douglas Bullet", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-011.png", null),
    CatalogCardSeed("OPST05-ST05-012", "OPST05", "ST05-012", "Baccarat", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-012.png", null),
    CatalogCardSeed("OPST05-ST05-013", "OPST05", "ST05-013", "Bins", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-013.png", null),
    CatalogCardSeed("OPST05-ST05-014", "OPST05", "ST05-014", "Buena Festa", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-014.png", null),
    CatalogCardSeed("OPST05-ST05-015", "OPST05", "ST05-015", "Dr. Indigo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-015.png", null),
    CatalogCardSeed("OPST05-ST05-016", "OPST05", "ST05-016", "Lion's Threat Imperial Earth Bind", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-016.png", null),
    CatalogCardSeed("OPST05-ST05-017", "OPST05", "ST05-017", "Union Armada", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST05-017.png", null),
)
