package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-03 "The Seven Warlords of the Sea" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569003): 17 Einträge, davon 17 mit eigenem
// ST03-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt03SetSeed = CardSetSeed(id = "OPST03", name = "ST03: The Seven Warlords of the Sea", game = "OnePiece", totalCards = 17)

val opSt03CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST03-ST03-001", "OPST03", "ST03-001", "Crocodile", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-001.png", null),
    CatalogCardSeed("OPST03-ST03-002", "OPST03", "ST03-002", "Edward Weevil", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-002.png", null),
    CatalogCardSeed("OPST03-ST03-003", "OPST03", "ST03-003", "Crocodile", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-003.png", null),
    CatalogCardSeed("OPST03-ST03-004", "OPST03", "ST03-004", "Gecko Moria", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-004.png", null),
    CatalogCardSeed("OPST03-ST03-005", "OPST03", "ST03-005", "Dracule Mihawk", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-005.png", null),
    CatalogCardSeed("OPST03-ST03-006", "OPST03", "ST03-006", "Jinbe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-006.png", null),
    CatalogCardSeed("OPST03-ST03-007", "OPST03", "ST03-007", "Sentomaru", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-007.png", null),
    CatalogCardSeed("OPST03-ST03-008", "OPST03", "ST03-008", "Trafalgar Law", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-008.png", null),
    CatalogCardSeed("OPST03-ST03-009", "OPST03", "ST03-009", "Donquixote Doflamingo", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-009.png", null),
    CatalogCardSeed("OPST03-ST03-010", "OPST03", "ST03-010", "Bartholomew Kuma", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-010.png", null),
    CatalogCardSeed("OPST03-ST03-011", "OPST03", "ST03-011", "Buggy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-011.png", null),
    CatalogCardSeed("OPST03-ST03-012", "OPST03", "ST03-012", "Pacifista", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-012.png", null),
    CatalogCardSeed("OPST03-ST03-013", "OPST03", "ST03-013", "Boa Hancock", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-013.png", null),
    CatalogCardSeed("OPST03-ST03-014", "OPST03", "ST03-014", "Marshall.D.Teach", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-014.png", null),
    CatalogCardSeed("OPST03-ST03-015", "OPST03", "ST03-015", "Sables", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-015.png", null),
    CatalogCardSeed("OPST03-ST03-016", "OPST03", "ST03-016", "Thrust Pad Cannon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-016.png", null),
    CatalogCardSeed("OPST03-ST03-017", "OPST03", "ST03-017", "Love-Love Mellow", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-017.png", null),
)
