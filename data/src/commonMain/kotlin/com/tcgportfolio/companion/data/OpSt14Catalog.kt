package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-14 "3D2Y" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569014): 17 Einträge, davon 17 mit eigenem
// ST14-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt14SetSeed = CardSetSeed(id = "OPST14", name = "ST14: 3D2Y", game = "OnePiece", totalCards = 17)

val opSt14CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST14-ST14-001", "OPST14", "ST14-001", "Monkey.D.Luffy", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-001.png", null),
    CatalogCardSeed("OPST14-ST14-002", "OPST14", "ST14-002", "Usopp", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-002.png", null),
    CatalogCardSeed("OPST14-ST14-003", "OPST14", "ST14-003", "Sanji", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-003.png", null),
    CatalogCardSeed("OPST14-ST14-004", "OPST14", "ST14-004", "Jinbe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-004.png", null),
    CatalogCardSeed("OPST14-ST14-005", "OPST14", "ST14-005", "Tony Tony.Chopper", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-005.png", null),
    CatalogCardSeed("OPST14-ST14-006", "OPST14", "ST14-006", "Nami", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-006.png", null),
    CatalogCardSeed("OPST14-ST14-007", "OPST14", "ST14-007", "Nico Robin", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-007.png", null),
    CatalogCardSeed("OPST14-ST14-008", "OPST14", "ST14-008", "Haredas", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-008.png", null),
    CatalogCardSeed("OPST14-ST14-009", "OPST14", "ST14-009", "Franky", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-009.png", null),
    CatalogCardSeed("OPST14-ST14-010", "OPST14", "ST14-010", "Brook", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-010.png", null),
    CatalogCardSeed("OPST14-ST14-011", "OPST14", "ST14-011", "Heracles", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-011.png", null),
    CatalogCardSeed("OPST14-ST14-012", "OPST14", "ST14-012", "Monkey.D.Luffy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-012.png", null),
    CatalogCardSeed("OPST14-ST14-013", "OPST14", "ST14-013", "Roronoa Zoro", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-013.png", null),
    CatalogCardSeed("OPST14-ST14-014", "OPST14", "ST14-014", "Gum-Gum Giant Rifle", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-014.png", null),
    CatalogCardSeed("OPST14-ST14-015", "OPST14", "ST14-015", "Gum-Gum Diable Three-Swords Style Mouten Jet Six Hundred Pound Phoenix Cannon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-015.png", null),
    CatalogCardSeed("OPST14-ST14-016", "OPST14", "ST14-016", "I Have My Crew!!", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-016.png", null),
    CatalogCardSeed("OPST14-ST14-017", "OPST14", "ST14-017", "Thousand Sunny", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST14-017.png", null),
)
