package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-21 "Starter Deck EX: Gear5" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569021): 32 Einträge, davon 32 mit eigenem
// ST21-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt21SetSeed = CardSetSeed(id = "OPST21", name = "ST21: Starter Deck EX: Gear5", game = "OnePiece", totalCards = 32)

val opSt21CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST21-ST21-001", "OPST21", "ST21-001", "Monkey.D.Luffy", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-001.png", null),
    CatalogCardSeed("OPST21-ST21-001-AA", "OPST21", "ST21-001", "Monkey.D.Luffy", "Alternate Art", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-001_p1.png", null),
    CatalogCardSeed("OPST21-ST21-002", "OPST21", "ST21-002", "Usopp", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-002.png", null),
    CatalogCardSeed("OPST21-ST21-002-AA", "OPST21", "ST21-002", "Usopp", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-002_p1.png", null),
    CatalogCardSeed("OPST21-ST21-003", "OPST21", "ST21-003", "Sanji", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-003.png", null),
    CatalogCardSeed("OPST21-ST21-003-AA", "OPST21", "ST21-003", "Sanji", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-003_p1.png", null),
    CatalogCardSeed("OPST21-ST21-004", "OPST21", "ST21-004", "Jewelry Bonney", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-004.png", null),
    CatalogCardSeed("OPST21-ST21-004-AA", "OPST21", "ST21-004", "Jewelry Bonney", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-004_p1.png", null),
    CatalogCardSeed("OPST21-ST21-005", "OPST21", "ST21-005", "Jinbe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-005.png", null),
    CatalogCardSeed("OPST21-ST21-005-AA", "OPST21", "ST21-005", "Jinbe", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-005_p1.png", null),
    CatalogCardSeed("OPST21-ST21-006", "OPST21", "ST21-006", "Stussy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-006.png", null),
    CatalogCardSeed("OPST21-ST21-006-AA", "OPST21", "ST21-006", "Stussy", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-006_p1.png", null),
    CatalogCardSeed("OPST21-ST21-007", "OPST21", "ST21-007", "Sentomaru", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-007.png", null),
    CatalogCardSeed("OPST21-ST21-007-AA", "OPST21", "ST21-007", "Sentomaru", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-007_p1.png", null),
    CatalogCardSeed("OPST21-ST21-008", "OPST21", "ST21-008", "Tony Tony.Chopper", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-008.png", null),
    CatalogCardSeed("OPST21-ST21-008-AA", "OPST21", "ST21-008", "Tony Tony.Chopper", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-008_p1.png", null),
    CatalogCardSeed("OPST21-ST21-009", "OPST21", "ST21-009", "Nami", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-009.png", null),
    CatalogCardSeed("OPST21-ST21-009-AA", "OPST21", "ST21-009", "Nami", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-009_p1.png", null),
    CatalogCardSeed("OPST21-ST21-010", "OPST21", "ST21-010", "Nico Robin", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-010.png", null),
    CatalogCardSeed("OPST21-ST21-010-AA", "OPST21", "ST21-010", "Nico Robin", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-010_p1.png", null),
    CatalogCardSeed("OPST21-ST21-011", "OPST21", "ST21-011", "Franky", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-011.png", null),
    CatalogCardSeed("OPST21-ST21-011-AA", "OPST21", "ST21-011", "Franky", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-011_p1.png", null),
    CatalogCardSeed("OPST21-ST21-012", "OPST21", "ST21-012", "Brook", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-012.png", null),
    CatalogCardSeed("OPST21-ST21-012-AA", "OPST21", "ST21-012", "Brook", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-012_p1.png", null),
    CatalogCardSeed("OPST21-ST21-013", "OPST21", "ST21-013", "Vegapunk", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-013.png", null),
    CatalogCardSeed("OPST21-ST21-013-AA", "OPST21", "ST21-013", "Vegapunk", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-013_p1.png", null),
    CatalogCardSeed("OPST21-ST21-014", "OPST21", "ST21-014", "Monkey.D.Luffy", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-014.png", null),
    CatalogCardSeed("OPST21-ST21-014-AA", "OPST21", "ST21-014", "Monkey.D.Luffy", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-014_p1.png", null),
    CatalogCardSeed("OPST21-ST21-015", "OPST21", "ST21-015", "Roronoa Zoro", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-015.png", null),
    CatalogCardSeed("OPST21-ST21-015-AA", "OPST21", "ST21-015", "Roronoa Zoro", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-015_p1.png", null),
    CatalogCardSeed("OPST21-ST21-016", "OPST21", "ST21-016", "Gum-Gum Dawn Whip", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-016.png", null),
    CatalogCardSeed("OPST21-ST21-017", "OPST21", "ST21-017", "Gum-Gum Mole Pistol", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST21-017.png", null),
)
