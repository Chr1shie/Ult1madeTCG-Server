package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-24 "Green Jewelry Bonney" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569024): 15 Einträge, davon 5 mit eigenem
// ST24-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt24SetSeed = CardSetSeed(id = "OPST24", name = "ST24: Green Jewelry Bonney", game = "OnePiece", totalCards = 15)

val opSt24CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST24-EB01-015-AA3", "OPST24", "EB01-015", "Scratchmen Apoo", "Alternate Art 3", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/EB01-015_p3.png", null),
    CatalogCardSeed("OPST24-OP07-019-AA3", "OPST24", "OP07-019", "Jewelry Bonney", "Alternate Art 3", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-019_p3.png", null),
    CatalogCardSeed("OPST24-OP07-021-R", "OPST24", "OP07-021", "Urouge", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-021_r1.png", null),
    CatalogCardSeed("OPST24-OP07-023-R", "OPST24", "OP07-023", "Caribou", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-023_r1.png", null),
    CatalogCardSeed("OPST24-OP07-025-R", "OPST24", "OP07-025", "Coribou", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-025_r1.png", null),
    CatalogCardSeed("OPST24-OP07-031-R", "OPST24", "OP07-031", "Bartolomeo", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-031_r1.png", null),
    CatalogCardSeed("OPST24-OP07-033-R", "OPST24", "OP07-033", "Monkey.D.Luffy", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-033_r1.png", null),
    CatalogCardSeed("OPST24-OP07-034-R", "OPST24", "OP07-034", "Roronoa Zoro", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-034_r1.png", null),
    CatalogCardSeed("OPST24-OP07-036-AA", "OPST24", "OP07-036", "Demonic Aura Nine-Sword Style Asura Demon Nine Flash", "Alternate Art", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-036_p1.png", null),
    CatalogCardSeed("OPST24-OP07-037-R", "OPST24", "OP07-037", "More Pizza!!", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-037_r1.png", null),
    CatalogCardSeed("OPST24-ST24-001", "OPST24", "ST24-001", "Capone\"Gang\"Bege", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST24-001.png", null),
    CatalogCardSeed("OPST24-ST24-002", "OPST24", "ST24-002", "Kid & Killer", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST24-002.png", null),
    CatalogCardSeed("OPST24-ST24-003", "OPST24", "ST24-003", "Basil Hawkins", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST24-003.png", null),
    CatalogCardSeed("OPST24-ST24-004", "OPST24", "ST24-004", "Law & Bepo", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST24-004.png", null),
    CatalogCardSeed("OPST24-ST24-005", "OPST24", "ST24-005", "X.Drake", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST24-005.png", null),
)
