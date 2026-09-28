package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-16 "Green Uta" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569016): 15 Einträge, davon 5 mit eigenem
// ST16-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt16SetSeed = CardSetSeed(id = "OPST16", name = "ST16: Green Uta", game = "OnePiece", totalCards = 15)

val opSt16CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST16-P-029-R", "OPST16", "P-029", "Bartolomeo", "Reprint", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-029_r1.png", null),
    CatalogCardSeed("OPST16-P-057-AA", "OPST16", "P-057", "Fleeting Lullaby", "Alternate Art", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-057_p1.png", null),
    CatalogCardSeed("OPST16-P-058-AA", "OPST16", "P-058", "Where the Wind Blows", "Alternate Art", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-058_p1.png", null),
    CatalogCardSeed("OPST16-P-059-AA", "OPST16", "P-059", "The World's Continuation", "Alternate Art", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-059_p1.png", null),
    CatalogCardSeed("OPST16-P-060-AA", "OPST16", "P-060", "Tot Musica", "Alternate Art", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-060_p1.png", null),
    CatalogCardSeed("OPST16-P-061-R", "OPST16", "P-061", "Monkey.D.Luffy", "Reprint", "P", "https://en.onepiece-cardgame.com/images/cardlist/card/P-061_r1.png", null),
    CatalogCardSeed("OPST16-ST11-001-AA", "OPST16", "ST11-001", "Uta", "Alternate Art", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-001_p1.png", null),
    CatalogCardSeed("OPST16-ST11-003-AA2", "OPST16", "ST11-003", "Backlight", "Alternate Art 2", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-003_p2.png", null),
    CatalogCardSeed("OPST16-ST11-004-AA2", "OPST16", "ST11-004", "New Genesis", "Alternate Art 2", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-004_p2.png", null),
    CatalogCardSeed("OPST16-ST11-005-AA2", "OPST16", "ST11-005", "I'm invincible", "Alternate Art 2", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST11-005_p2.png", null),
    CatalogCardSeed("OPST16-ST16-001", "OPST16", "ST16-001", "Uta", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST16-001.png", null),
    CatalogCardSeed("OPST16-ST16-002", "OPST16", "ST16-002", "Gordon", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST16-002.png", null),
    CatalogCardSeed("OPST16-ST16-003", "OPST16", "ST16-003", "Charlotte Katakuri", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST16-003.png", null),
    CatalogCardSeed("OPST16-ST16-004", "OPST16", "ST16-004", "Shanks", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST16-004.png", null),
    CatalogCardSeed("OPST16-ST16-005", "OPST16", "ST16-005", "Monkey.D.Luffy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST16-005.png", null),
)
