package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-08 "Monkey.D.Luffy" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569008): 15 Einträge, davon 15 mit eigenem
// ST08-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt08SetSeed = CardSetSeed(id = "OPST08", name = "ST08: Monkey.D.Luffy", game = "OnePiece", totalCards = 15)

val opSt08CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST08-ST08-001", "OPST08", "ST08-001", "Monkey.D.Luffy", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-001.png", null),
    CatalogCardSeed("OPST08-ST08-002", "OPST08", "ST08-002", "Uta", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-002.png", null),
    CatalogCardSeed("OPST08-ST08-003", "OPST08", "ST08-003", "Gaimon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-003.png", null),
    CatalogCardSeed("OPST08-ST08-004", "OPST08", "ST08-004", "Koby", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-004.png", null),
    CatalogCardSeed("OPST08-ST08-005", "OPST08", "ST08-005", "Shanks", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-005.png", null),
    CatalogCardSeed("OPST08-ST08-006", "OPST08", "ST08-006", "Shirahoshi", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-006.png", null),
    CatalogCardSeed("OPST08-ST08-007", "OPST08", "ST08-007", "Nefeltari Vivi", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-007.png", null),
    CatalogCardSeed("OPST08-ST08-008", "OPST08", "ST08-008", "Higuma", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-008.png", null),
    CatalogCardSeed("OPST08-ST08-009", "OPST08", "ST08-009", "Makino", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-009.png", null),
    CatalogCardSeed("OPST08-ST08-010", "OPST08", "ST08-010", "Monkey.D.Garp", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-010.png", null),
    CatalogCardSeed("OPST08-ST08-011", "OPST08", "ST08-011", "Monkey.D.Luffy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-011.png", null),
    CatalogCardSeed("OPST08-ST08-012", "OPST08", "ST08-012", "Laboon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-012.png", null),
    CatalogCardSeed("OPST08-ST08-013", "OPST08", "ST08-013", "Mr.2.Bon.Kurei(Bentham)", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-013.png", null),
    CatalogCardSeed("OPST08-ST08-014", "OPST08", "ST08-014", "Gum-Gum Bell", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-014.png", null),
    CatalogCardSeed("OPST08-ST08-015", "OPST08", "ST08-015", "Gum-Gum Pistol", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST08-015.png", null),
)
