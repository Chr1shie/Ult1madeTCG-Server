package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-02 "Worst Generation" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569002): 17 Einträge, davon 17 mit eigenem
// ST02-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt02SetSeed = CardSetSeed(id = "OPST02", name = "ST02: Worst Generation", game = "OnePiece", totalCards = 17)

val opSt02CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST02-ST02-001", "OPST02", "ST02-001", "Eustass\"Captain\"Kid", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-001.png", null),
    CatalogCardSeed("OPST02-ST02-002", "OPST02", "ST02-002", "Vito", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-002.png", null),
    CatalogCardSeed("OPST02-ST02-003", "OPST02", "ST02-003", "Urouge", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-003.png", null),
    CatalogCardSeed("OPST02-ST02-004", "OPST02", "ST02-004", "Capone\"Gang\"Bege", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-004.png", null),
    CatalogCardSeed("OPST02-ST02-005", "OPST02", "ST02-005", "Killer", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-005.png", null),
    CatalogCardSeed("OPST02-ST02-006", "OPST02", "ST02-006", "Koby", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-006.png", null),
    CatalogCardSeed("OPST02-ST02-007", "OPST02", "ST02-007", "Jewelry Bonney", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-007.png", null),
    CatalogCardSeed("OPST02-ST02-008", "OPST02", "ST02-008", "Scratchmen Apoo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-008.png", null),
    CatalogCardSeed("OPST02-ST02-009", "OPST02", "ST02-009", "Trafalgar Law", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-009.png", null),
    CatalogCardSeed("OPST02-ST02-010", "OPST02", "ST02-010", "Basil Hawkins", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-010.png", null),
    CatalogCardSeed("OPST02-ST02-011", "OPST02", "ST02-011", "Heat", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-011.png", null),
    CatalogCardSeed("OPST02-ST02-012", "OPST02", "ST02-012", "Bepo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-012.png", null),
    CatalogCardSeed("OPST02-ST02-013", "OPST02", "ST02-013", "Eustass\"Captain\"Kid", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-013.png", null),
    CatalogCardSeed("OPST02-ST02-014", "OPST02", "ST02-014", "X.Drake", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-014.png", null),
    CatalogCardSeed("OPST02-ST02-015", "OPST02", "ST02-015", "Scalpel", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-015.png", null),
    CatalogCardSeed("OPST02-ST02-016", "OPST02", "ST02-016", "Repel", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-016.png", null),
    CatalogCardSeed("OPST02-ST02-017", "OPST02", "ST02-017", "Straw Sword", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST02-017.png", null),
)
