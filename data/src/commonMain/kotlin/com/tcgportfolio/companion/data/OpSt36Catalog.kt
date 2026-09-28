package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-36 "Yellow Eustass"Captain"Kid" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569036): 15 Einträge, davon 5 mit eigenem
// ST36-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt36SetSeed = CardSetSeed(id = "OPST36", name = "ST36: Yellow Eustass\"Captain\"Kid", game = "OnePiece", totalCards = 15)

val opSt36CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST36-OP10-099-AA2", "OPST36", "OP10-099", "Eustass\"Captain\"Kid", "Alternate Art 2", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP10-099_p2.png", null),
    CatalogCardSeed("OPST36-OP10-101-R", "OPST36", "OP10-101", "Urouge", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP10-101_r1.png", null),
    CatalogCardSeed("OPST36-OP10-103-R", "OPST36", "OP10-103", "Capone\"Gang\"Bege", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP10-103_r1.png", null),
    CatalogCardSeed("OPST36-OP10-109-R2", "OPST36", "OP10-109", "Basil Hawkins", "Reprint 2", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP10-109_r2.png", null),
    CatalogCardSeed("OPST36-OP10-111-R", "OPST36", "OP10-111", "Monkey.D.Luffy", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP10-111_r1.png", null),
    CatalogCardSeed("OPST36-OP10-114-R2", "OPST36", "OP10-114", "X.Drake", "Reprint 2", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP10-114_r2.png", null),
    CatalogCardSeed("OPST36-OP12-113-R", "OPST36", "OP12-113", "Roronoa Zoro", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP12-113_r1.png", null),
    CatalogCardSeed("OPST36-OP13-116-R", "OPST36", "OP13-116", "The One Who Is the Most Free Is the Pirate King!!!", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP13-116_r1.png", null),
    CatalogCardSeed("OPST36-P-085-R2", "OPST36", "P-085", "Jewelry Bonney", "Reprint 2", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-085_r2.png", null),
    CatalogCardSeed("OPST36-P-088-R2", "OPST36", "P-088", "Trafalgar Law", "Reprint 2", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-088_r2.png", null),
    CatalogCardSeed("OPST36-ST36-001", "OPST36", "ST36-001", "Cavendish", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST36-001.png", null),
    CatalogCardSeed("OPST36-ST36-002", "OPST36", "ST36-002", "Killer", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST36-002.png", null),
    CatalogCardSeed("OPST36-ST36-003", "OPST36", "ST36-003", "Scratchmen Apoo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST36-003.png", null),
    CatalogCardSeed("OPST36-ST36-004", "OPST36", "ST36-004", "Bartolomeo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST36-004.png", null),
    CatalogCardSeed("OPST36-ST36-005", "OPST36", "ST36-005", "Eustass\"Captain\"Kid", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST36-005.png", null),
)
