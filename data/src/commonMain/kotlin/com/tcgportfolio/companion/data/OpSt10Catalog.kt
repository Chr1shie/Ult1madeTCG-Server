package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-10 "Ultra Deck: The Three Captains" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569010): 19 Einträge, davon 17 mit eigenem
// ST10-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt10SetSeed = CardSetSeed(id = "OPST10", name = "ST10: Ultra Deck: The Three Captains", game = "OnePiece", totalCards = 19)

val opSt10CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST10-OP01-016-AA3", "OPST10", "OP01-016", "Nami", "Alternate Art 3", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP01-016_p3.png", null),
    CatalogCardSeed("OPST10-OP01-025-AA2", "OPST10", "OP01-025", "Roronoa Zoro", "Alternate Art 2", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/OP01-025_p2.png", null),
    CatalogCardSeed("OPST10-ST10-001", "OPST10", "ST10-001", "Trafalgar Law", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-001.png", null),
    CatalogCardSeed("OPST10-ST10-002", "OPST10", "ST10-002", "Monkey.D.Luffy", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-002.png", null),
    CatalogCardSeed("OPST10-ST10-003", "OPST10", "ST10-003", "Eustass\"Captain\"Kid", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-003.png", null),
    CatalogCardSeed("OPST10-ST10-004", "OPST10", "ST10-004", "Sanji", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-004.png", null),
    CatalogCardSeed("OPST10-ST10-005", "OPST10", "ST10-005", "Jinbe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-005.png", null),
    CatalogCardSeed("OPST10-ST10-006", "OPST10", "ST10-006", "Monkey.D.Luffy", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-006.png", null),
    CatalogCardSeed("OPST10-ST10-007", "OPST10", "ST10-007", "Killer", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-007.png", null),
    CatalogCardSeed("OPST10-ST10-008", "OPST10", "ST10-008", "Shachi & Penguin", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-008.png", null),
    CatalogCardSeed("OPST10-ST10-009", "OPST10", "ST10-009", "Jean Bart", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-009.png", null),
    CatalogCardSeed("OPST10-ST10-010", "OPST10", "ST10-010", "Trafalgar Law", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-010.png", null),
    CatalogCardSeed("OPST10-ST10-011", "OPST10", "ST10-011", "Heat", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-011.png", null),
    CatalogCardSeed("OPST10-ST10-012", "OPST10", "ST10-012", "Bepo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-012.png", null),
    CatalogCardSeed("OPST10-ST10-013", "OPST10", "ST10-013", "Eustass\"Captain\"Kid", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-013.png", null),
    CatalogCardSeed("OPST10-ST10-014", "OPST10", "ST10-014", "Wire", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-014.png", null),
    CatalogCardSeed("OPST10-ST10-015", "OPST10", "ST10-015", "Gum-Gum Giant Sumo Slap", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-015.png", null),
    CatalogCardSeed("OPST10-ST10-016", "OPST10", "ST10-016", "Gum-Gum Kong Gatling", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-016.png", null),
    CatalogCardSeed("OPST10-ST10-017", "OPST10", "ST10-017", "Punk Vise", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST10-017.png", null),
)
