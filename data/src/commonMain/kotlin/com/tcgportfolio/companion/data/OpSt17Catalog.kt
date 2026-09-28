package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-17 "Blue Donquixote Doflamingo" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569017): 15 Einträge, davon 5 mit eigenem
// ST17-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt17SetSeed = CardSetSeed(id = "OPST17", name = "ST17: Blue Donquixote Doflamingo", game = "OnePiece", totalCards = 15)

val opSt17CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST17-OP01-060-AA2", "OPST17", "OP01-060", "Donquixote Doflamingo", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP01-060_p2.png", null),
    CatalogCardSeed("OPST17-OP01-073-R", "OPST17", "OP01-073", "Donquixote Doflamingo", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP01-073_r1.png", null),
    CatalogCardSeed("OPST17-OP01-086-R", "OPST17", "OP01-086", "Overheat", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP01-086_r1.png", null),
    CatalogCardSeed("OPST17-OP02-054-R", "OPST17", "OP02-054", "Gecko Moria", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-054_r1.png", null),
    CatalogCardSeed("OPST17-OP02-057-R", "OPST17", "OP02-057", "Bartholomew Kuma", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-057_r1.png", null),
    CatalogCardSeed("OPST17-P-030-R", "OPST17", "P-030", "Jinbe", "Reprint", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-030_r1.png", null),
    CatalogCardSeed("OPST17-ST03-002-R", "OPST17", "ST03-002", "Edward Weevil", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-002_r1.png", null),
    CatalogCardSeed("OPST17-ST03-004-R", "OPST17", "ST03-004", "Gecko Moria", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-004_r1.png", null),
    CatalogCardSeed("OPST17-ST03-005-R", "OPST17", "ST03-005", "Dracule Mihawk", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-005_r1.png", null),
    CatalogCardSeed("OPST17-ST03-008-R", "OPST17", "ST03-008", "Trafalgar Law", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST03-008_r1.png", null),
    CatalogCardSeed("OPST17-ST17-001", "OPST17", "ST17-001", "Crocodile", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST17-001.png", null),
    CatalogCardSeed("OPST17-ST17-002", "OPST17", "ST17-002", "Trafalgar Law", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST17-002.png", null),
    CatalogCardSeed("OPST17-ST17-003", "OPST17", "ST17-003", "Buggy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST17-003.png", null),
    CatalogCardSeed("OPST17-ST17-004", "OPST17", "ST17-004", "Boa Hancock", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST17-004.png", null),
    CatalogCardSeed("OPST17-ST17-005", "OPST17", "ST17-005", "Marshall.D.Teach", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST17-005.png", null),
)
