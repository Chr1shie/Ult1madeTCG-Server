package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-11 "Uta" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569011): 15 Einträge, davon 5 mit eigenem
// ST11-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt11SetSeed = CardSetSeed(id = "OPST11", name = "ST11: Uta", game = "OnePiece", totalCards = 15)

val opSt11CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST11-OP02-028-AA", "OPST11", "OP02-028", "Usopp", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-028_p1.png", null),
    CatalogCardSeed("OPST11-OP02-033-AA", "OPST11", "OP02-033", "Jinbe", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-033_p1.png", null),
    CatalogCardSeed("OPST11-OP02-034-AA", "OPST11", "OP02-034", "Tony Tony.Chopper", "Alternate Art", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-034_p1.png", null),
    CatalogCardSeed("OPST11-OP02-035-AA2", "OPST11", "OP02-035", "Trafalgar Law", "Alternate Art 2", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-035_p2.png", null),
    CatalogCardSeed("OPST11-OP02-037-AA", "OPST11", "OP02-037", "Nico Robin", "Alternate Art", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-037_p1.png", null),
    CatalogCardSeed("OPST11-OP02-039-AA", "OPST11", "OP02-039", "Franky", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-039_p1.png", null),
    CatalogCardSeed("OPST11-OP02-040-AA", "OPST11", "OP02-040", "Brook", "Alternate Art", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-040_p1.png", null),
    CatalogCardSeed("OPST11-OP02-041-AA2", "OPST11", "OP02-041", "Monkey.D.Luffy", "Alternate Art 2", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-041_p2.png", null),
    CatalogCardSeed("OPST11-OP02-043-AA", "OPST11", "OP02-043", "Roronoa Zoro", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-043_p1.png", null),
    CatalogCardSeed("OPST11-OP02-045-AA", "OPST11", "OP02-045", "Three Sword Style Oni Giri", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-045_p1.png", null),
    CatalogCardSeed("OPST11-ST11-001", "OPST11", "ST11-001", "Uta", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-001.png", null),
    CatalogCardSeed("OPST11-ST11-002", "OPST11", "ST11-002", "Uta", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-002.png", null),
    CatalogCardSeed("OPST11-ST11-003", "OPST11", "ST11-003", "Backlight", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-003.png", null),
    CatalogCardSeed("OPST11-ST11-004", "OPST11", "ST11-004", "New Genesis", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-004.png", null),
    CatalogCardSeed("OPST11-ST11-005", "OPST11", "ST11-005", "I'm invincible", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-005.png", null),
)
