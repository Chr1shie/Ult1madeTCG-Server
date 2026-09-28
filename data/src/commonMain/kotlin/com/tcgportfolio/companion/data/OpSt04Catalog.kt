package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-04 "Animal Kingdom Pirates" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569004): 17 Einträge, davon 17 mit eigenem
// ST04-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt04SetSeed = CardSetSeed(id = "OPST04", name = "ST04: Animal Kingdom Pirates", game = "OnePiece", totalCards = 17)

val opSt04CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST04-ST04-001", "OPST04", "ST04-001", "Kaido", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-001.png", null),
    CatalogCardSeed("OPST04-ST04-002", "OPST04", "ST04-002", "Ulti", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-002.png", null),
    CatalogCardSeed("OPST04-ST04-003", "OPST04", "ST04-003", "Kaido", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-003.png", null),
    CatalogCardSeed("OPST04-ST04-004", "OPST04", "ST04-004", "King", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-004.png", null),
    CatalogCardSeed("OPST04-ST04-005", "OPST04", "ST04-005", "Queen", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-005.png", null),
    CatalogCardSeed("OPST04-ST04-006", "OPST04", "ST04-006", "Sasaki", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-006.png", null),
    CatalogCardSeed("OPST04-ST04-007", "OPST04", "ST04-007", "Sheepshead", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-007.png", null),
    CatalogCardSeed("OPST04-ST04-008", "OPST04", "ST04-008", "Jack", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-008.png", null),
    CatalogCardSeed("OPST04-ST04-009", "OPST04", "ST04-009", "Ginrummy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-009.png", null),
    CatalogCardSeed("OPST04-ST04-010", "OPST04", "ST04-010", "Who's.Who", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-010.png", null),
    CatalogCardSeed("OPST04-ST04-011", "OPST04", "ST04-011", "Black Maria", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-011.png", null),
    CatalogCardSeed("OPST04-ST04-012", "OPST04", "ST04-012", "Page One", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-012.png", null),
    CatalogCardSeed("OPST04-ST04-013", "OPST04", "ST04-013", "X.Drake", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-013.png", null),
    CatalogCardSeed("OPST04-ST04-014", "OPST04", "ST04-014", "Lead Performer \"Disaster\"", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-014.png", null),
    CatalogCardSeed("OPST04-ST04-015", "OPST04", "ST04-015", "Brachio Bomber", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-015.png", null),
    CatalogCardSeed("OPST04-ST04-016", "OPST04", "ST04-016", "Blast Breath", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-016.png", null),
    CatalogCardSeed("OPST04-ST04-017", "OPST04", "ST04-017", "Onigashima Island", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST04-017.png", null),
)
