package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-19 "Black Smoker" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569019): 15 Einträge, davon 5 mit eigenem
// ST19-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt19SetSeed = CardSetSeed(id = "OPST19", name = "ST19: Black Smoker", game = "OnePiece", totalCards = 15)

val opSt19CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST19-OP02-093-AA2", "OPST19", "OP02-093", "Smoker", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-093_p2.png", null),
    CatalogCardSeed("OPST19-OP02-098-R", "OPST19", "OP02-098", "Koby", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-098_r1.png", null),
    CatalogCardSeed("OPST19-OP02-106-R", "OPST19", "OP02-106", "Tsuru", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-106_r1.png", null),
    CatalogCardSeed("OPST19-OP02-108-R", "OPST19", "OP02-108", "Donquixote Rosinante", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-108_r1.png", null),
    CatalogCardSeed("OPST19-OP02-109-R", "OPST19", "OP02-109", "Jaguar.D.Saul", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-109_r1.png", null),
    CatalogCardSeed("OPST19-OP02-113-R", "OPST19", "OP02-113", "Helmeppo", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-113_r1.png", null),
    CatalogCardSeed("OPST19-OP02-116-R", "OPST19", "OP02-116", "Yamakaji", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-116_r1.png", null),
    CatalogCardSeed("OPST19-OP02-117-R", "OPST19", "OP02-117", "Ice Age", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP02-117_r1.png", null),
    CatalogCardSeed("OPST19-OP03-079-R", "OPST19", "OP03-079", "Vergo", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-079_r1.png", null),
    CatalogCardSeed("OPST19-OP03-089-R", "OPST19", "OP03-089", "Brannew", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP03-089_r1.png", null),
    CatalogCardSeed("OPST19-ST19-001", "OPST19", "ST19-001", "Smoker", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST19-001.png", null),
    CatalogCardSeed("OPST19-ST19-002", "OPST19", "ST19-002", "Sengoku", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST19-002.png", null),
    CatalogCardSeed("OPST19-ST19-003", "OPST19", "ST19-003", "Tashigi", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST19-003.png", null),
    CatalogCardSeed("OPST19-ST19-004", "OPST19", "ST19-004", "Hina", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST19-004.png", null),
    CatalogCardSeed("OPST19-ST19-005", "OPST19", "ST19-005", "Monkey.D.Garp", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST19-005.png", null),
)
