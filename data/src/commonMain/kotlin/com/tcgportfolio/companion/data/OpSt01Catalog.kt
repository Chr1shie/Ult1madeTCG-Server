package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-01 "Straw Hat Crew" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569001): 17 Einträge, davon 17 mit eigenem
// ST01-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt01SetSeed = CardSetSeed(id = "OPST01", name = "ST01: Straw Hat Crew", game = "OnePiece", totalCards = 17)

val opSt01CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST01-ST01-001", "OPST01", "ST01-001", "Monkey.D.Luffy", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-001.png", null),
    CatalogCardSeed("OPST01-ST01-002", "OPST01", "ST01-002", "Usopp", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-002.png", null),
    CatalogCardSeed("OPST01-ST01-003", "OPST01", "ST01-003", "Karoo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-003.png", null),
    CatalogCardSeed("OPST01-ST01-004", "OPST01", "ST01-004", "Sanji", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-004.png", null),
    CatalogCardSeed("OPST01-ST01-005", "OPST01", "ST01-005", "Jinbe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-005.png", null),
    CatalogCardSeed("OPST01-ST01-006", "OPST01", "ST01-006", "Tony Tony.Chopper", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-006.png", null),
    CatalogCardSeed("OPST01-ST01-007", "OPST01", "ST01-007", "Nami", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-007.png", null),
    CatalogCardSeed("OPST01-ST01-008", "OPST01", "ST01-008", "Nico Robin", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-008.png", null),
    CatalogCardSeed("OPST01-ST01-009", "OPST01", "ST01-009", "Nefeltari Vivi", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-009.png", null),
    CatalogCardSeed("OPST01-ST01-010", "OPST01", "ST01-010", "Franky", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-010.png", null),
    CatalogCardSeed("OPST01-ST01-011", "OPST01", "ST01-011", "Brook", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-011.png", null),
    CatalogCardSeed("OPST01-ST01-012", "OPST01", "ST01-012", "Monkey.D.Luffy", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-012.png", null),
    CatalogCardSeed("OPST01-ST01-013", "OPST01", "ST01-013", "Roronoa Zoro", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-013.png", null),
    CatalogCardSeed("OPST01-ST01-014", "OPST01", "ST01-014", "Guard Point", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-014.png", null),
    CatalogCardSeed("OPST01-ST01-015", "OPST01", "ST01-015", "Gum-Gum Jet Pistol", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-015.png", null),
    CatalogCardSeed("OPST01-ST01-016", "OPST01", "ST01-016", "Diable Jambe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-016.png", null),
    CatalogCardSeed("OPST01-ST01-017", "OPST01", "ST01-017", "Thousand Sunny", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST01-017.png", null),
)
