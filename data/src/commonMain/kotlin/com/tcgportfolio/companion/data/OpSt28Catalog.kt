package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-28 "Green/Yellow Yamato" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569028): 15 Einträge, davon 5 mit eigenem
// ST28-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt28SetSeed = CardSetSeed(id = "OPST28", name = "ST28: Green/Yellow Yamato", game = "OnePiece", totalCards = 15)

val opSt28CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST28-OP06-022-AA3", "OPST28", "OP06-022", "Yamato", "Alternate Art 3", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/OP06-022_p3.png", null),
    CatalogCardSeed("OPST28-OP06-100-R2", "OPST28", "OP06-100", "Inuarashi", "Reprint 2", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP06-100_r2.png", null),
    CatalogCardSeed("OPST28-OP06-103-R", "OPST28", "OP06-103", "Kawamatsu", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP06-103_r1.png", null),
    CatalogCardSeed("OPST28-OP06-104-R", "OPST28", "OP06-104", "Kikunojo", "Reprint", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP06-104_r1.png", null),
    CatalogCardSeed("OPST28-OP06-109-R", "OPST28", "OP06-109", "Denjiro", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP06-109_r1.png", null),
    CatalogCardSeed("OPST28-OP06-110-R2", "OPST28", "OP06-110", "Nekomamushi", "Reprint 2", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP06-110_r2.png", null),
    CatalogCardSeed("OPST28-OP06-112-R", "OPST28", "OP06-112", "Raizo", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/OP06-112_r1.png", null),
    CatalogCardSeed("OPST28-OP07-116-AA", "OPST28", "OP07-116", "Blaze Slice", "Alternate Art", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/OP07-116_p1.png", null),
    CatalogCardSeed("OPST28-OP09-035-R", "OPST28", "OP09-035", "Portgas.D.Ace", "Reprint", "UC", "https://en.onepiece-cardgame.com/images/cardlist/card/OP09-035_r1.png", null),
    CatalogCardSeed("OPST28-ST13-016-R", "OPST28", "ST13-016", "Yamato", "Reprint", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-016_r1.png", null),
    CatalogCardSeed("OPST28-ST28-001", "OPST28", "ST28-001", "Ashura Doji", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST28-001.png", null),
    CatalogCardSeed("OPST28-ST28-002", "OPST28", "ST28-002", "Izo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST28-002.png", null),
    CatalogCardSeed("OPST28-ST28-003", "OPST28", "ST28-003", "Kin'emon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST28-003.png", null),
    CatalogCardSeed("OPST28-ST28-004", "OPST28", "ST28-004", "Kouzuki Momonosuke", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST28-004.png", null),
    CatalogCardSeed("OPST28-ST28-005", "OPST28", "ST28-005", "Yamato", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST28-005.png", null),
)
