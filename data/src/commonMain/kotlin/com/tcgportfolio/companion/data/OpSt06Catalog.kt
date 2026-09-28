package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-06 "Absolute Justice" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569006): 17 Einträge, davon 17 mit eigenem
// ST06-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt06SetSeed = CardSetSeed(id = "OPST06", name = "ST06: Absolute Justice", game = "OnePiece", totalCards = 17)

val opSt06CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST06-ST06-001", "OPST06", "ST06-001", "Sakazuki", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-001.png", null),
    CatalogCardSeed("OPST06-ST06-002", "OPST06", "ST06-002", "Koby", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-002.png", null),
    CatalogCardSeed("OPST06-ST06-003", "OPST06", "ST06-003", "Jango", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-003.png", null),
    CatalogCardSeed("OPST06-ST06-004", "OPST06", "ST06-004", "Smoker", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-004.png", null),
    CatalogCardSeed("OPST06-ST06-005", "OPST06", "ST06-005", "Sengoku", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-005.png", null),
    CatalogCardSeed("OPST06-ST06-006", "OPST06", "ST06-006", "Tashigi", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-006.png", null),
    CatalogCardSeed("OPST06-ST06-007", "OPST06", "ST06-007", "Tsuru", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-007.png", null),
    CatalogCardSeed("OPST06-ST06-008", "OPST06", "ST06-008", "Hina", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-008.png", null),
    CatalogCardSeed("OPST06-ST06-009", "OPST06", "ST06-009", "Fullbody", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-009.png", null),
    CatalogCardSeed("OPST06-ST06-010", "OPST06", "ST06-010", "Helmeppo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-010.png", null),
    CatalogCardSeed("OPST06-ST06-011", "OPST06", "ST06-011", "Momonga", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-011.png", null),
    CatalogCardSeed("OPST06-ST06-012", "OPST06", "ST06-012", "Monkey.D.Garp", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-012.png", null),
    CatalogCardSeed("OPST06-ST06-013", "OPST06", "ST06-013", "T-Bone", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-013.png", null),
    CatalogCardSeed("OPST06-ST06-014", "OPST06", "ST06-014", "Shockwave", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-014.png", null),
    CatalogCardSeed("OPST06-ST06-015", "OPST06", "ST06-015", "Great Eruption", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-015.png", null),
    CatalogCardSeed("OPST06-ST06-016", "OPST06", "ST06-016", "White Out", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-016.png", null),
    CatalogCardSeed("OPST06-ST06-017", "OPST06", "ST06-017", "Navy HQ", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST06-017.png", null),
)
