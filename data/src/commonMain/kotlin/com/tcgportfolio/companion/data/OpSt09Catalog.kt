package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-09 "Yamato" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569009): 15 Einträge, davon 15 mit eigenem
// ST09-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt09SetSeed = CardSetSeed(id = "OPST09", name = "ST09: Yamato", game = "OnePiece", totalCards = 15)

val opSt09CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST09-ST09-001", "OPST09", "ST09-001", "Yamato", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-001.png", null),
    CatalogCardSeed("OPST09-ST09-002", "OPST09", "ST09-002", "Uzuki Tempura", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-002.png", null),
    CatalogCardSeed("OPST09-ST09-003", "OPST09", "ST09-003", "Ulti", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-003.png", null),
    CatalogCardSeed("OPST09-ST09-004", "OPST09", "ST09-004", "Kaido", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-004.png", null),
    CatalogCardSeed("OPST09-ST09-005", "OPST09", "ST09-005", "Kouzuki Oden", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-005.png", null),
    CatalogCardSeed("OPST09-ST09-006", "OPST09", "ST09-006", "Kouzuki Momonosuke", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-006.png", null),
    CatalogCardSeed("OPST09-ST09-007", "OPST09", "ST09-007", "Shinobu", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-007.png", null),
    CatalogCardSeed("OPST09-ST09-008", "OPST09", "ST09-008", "Shimotsuki Ushimaru", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-008.png", null),
    CatalogCardSeed("OPST09-ST09-009", "OPST09", "ST09-009", "Fugetsu Omusubi", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-009.png", null),
    CatalogCardSeed("OPST09-ST09-010", "OPST09", "ST09-010", "Portgas.D.Ace", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-010.png", null),
    CatalogCardSeed("OPST09-ST09-011", "OPST09", "ST09-011", "Monkey.D.Luffy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-011.png", null),
    CatalogCardSeed("OPST09-ST09-012", "OPST09", "ST09-012", "Yamato", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-012.png", null),
    CatalogCardSeed("OPST09-ST09-013", "OPST09", "ST09-013", "Yamato", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-013.png", null),
    CatalogCardSeed("OPST09-ST09-014", "OPST09", "ST09-014", "Narikabura Arrow", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-014.png", null),
    CatalogCardSeed("OPST09-ST09-015", "OPST09", "ST09-015", "Thunder Bagua", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST09-015.png", null),
)
