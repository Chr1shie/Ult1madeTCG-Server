package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-12 "Zoro and Sanji" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569012): 17 Einträge, davon 17 mit eigenem
// ST12-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt12SetSeed = CardSetSeed(id = "OPST12", name = "ST12: Zoro and Sanji", game = "OnePiece", totalCards = 17)

val opSt12CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST12-ST12-001", "OPST12", "ST12-001", "Roronoa Zoro & Sanji", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-001.png", null),
    CatalogCardSeed("OPST12-ST12-002", "OPST12", "ST12-002", "Kuina", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-002.png", null),
    CatalogCardSeed("OPST12-ST12-003", "OPST12", "ST12-003", "Dracule Mihawk", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-003.png", null),
    CatalogCardSeed("OPST12-ST12-004", "OPST12", "ST12-004", "Humandrill", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-004.png", null),
    CatalogCardSeed("OPST12-ST12-005", "OPST12", "ST12-005", "Perona", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-005.png", null),
    CatalogCardSeed("OPST12-ST12-006", "OPST12", "ST12-006", "Yosaku & Johnny", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-006.png", null),
    CatalogCardSeed("OPST12-ST12-007", "OPST12", "ST12-007", "Rika", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-007.png", null),
    CatalogCardSeed("OPST12-ST12-008", "OPST12", "ST12-008", "Roronoa Zoro", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-008.png", null),
    CatalogCardSeed("OPST12-ST12-009", "OPST12", "ST12-009", "Elephant True Bluefin", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-009.png", null),
    CatalogCardSeed("OPST12-ST12-010", "OPST12", "ST12-010", "Emporio.Ivankov", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-010.png", null),
    CatalogCardSeed("OPST12-ST12-011", "OPST12", "ST12-011", "Sanji", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-011.png", null),
    CatalogCardSeed("OPST12-ST12-012", "OPST12", "ST12-012", "Charlotte Pudding", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-012.png", null),
    CatalogCardSeed("OPST12-ST12-013", "OPST12", "ST12-013", "Zeff", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-013.png", null),
    CatalogCardSeed("OPST12-ST12-014", "OPST12", "ST12-014", "Duval", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-014.png", null),
    CatalogCardSeed("OPST12-ST12-015", "OPST12", "ST12-015", "Patty & Carne", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-015.png", null),
    CatalogCardSeed("OPST12-ST12-016", "OPST12", "ST12-016", "Lion Strike", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-016.png", null),
    CatalogCardSeed("OPST12-ST12-017", "OPST12", "ST12-017", "Plastic Surgery Shot", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST12-017.png", null),
)
