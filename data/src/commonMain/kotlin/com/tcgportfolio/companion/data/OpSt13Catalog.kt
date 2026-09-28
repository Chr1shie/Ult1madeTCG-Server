package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-13 "Ultra Deck: The Three Brothers" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569013): 35 Einträge, davon 35 mit eigenem
// ST13-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt13SetSeed = CardSetSeed(id = "OPST13", name = "ST13: Ultra Deck: The Three Brothers", game = "OnePiece", totalCards = 35)

val opSt13CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST13-ST13-001", "OPST13", "ST13-001", "Sabo", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-001.png", null),
    CatalogCardSeed("OPST13-ST13-001-AA", "OPST13", "ST13-001", "Sabo", "Alternate Art", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-001_p1.png", null),
    CatalogCardSeed("OPST13-ST13-002", "OPST13", "ST13-002", "Portgas.D.Ace", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-002.png", null),
    CatalogCardSeed("OPST13-ST13-002-AA", "OPST13", "ST13-002", "Portgas.D.Ace", "Alternate Art", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-002_p1.png", null),
    CatalogCardSeed("OPST13-ST13-003", "OPST13", "ST13-003", "Monkey.D.Luffy", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-003.png", null),
    CatalogCardSeed("OPST13-ST13-003-AA", "OPST13", "ST13-003", "Monkey.D.Luffy", "Alternate Art", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-003_p1.png", null),
    CatalogCardSeed("OPST13-ST13-004", "OPST13", "ST13-004", "Edward.Newgate", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-004.png", null),
    CatalogCardSeed("OPST13-ST13-004-AA", "OPST13", "ST13-004", "Edward.Newgate", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-004_p1.png", null),
    CatalogCardSeed("OPST13-ST13-005", "OPST13", "ST13-005", "Emporio.Ivankov", "Normal", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-005.png", null),
    CatalogCardSeed("OPST13-ST13-005-AA", "OPST13", "ST13-005", "Emporio.Ivankov", "Alternate Art", "R", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-005_p1.png", null),
    CatalogCardSeed("OPST13-ST13-006", "OPST13", "ST13-006", "Curly.Dadan", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-006.png", null),
    CatalogCardSeed("OPST13-ST13-006-AA", "OPST13", "ST13-006", "Curly.Dadan", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-006_p1.png", null),
    CatalogCardSeed("OPST13-ST13-007", "OPST13", "ST13-007", "Sabo", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-007.png", null),
    CatalogCardSeed("OPST13-ST13-007-AA", "OPST13", "ST13-007", "Sabo", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-007_p1.png", null),
    CatalogCardSeed("OPST13-ST13-008", "OPST13", "ST13-008", "Sabo", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-008.png", null),
    CatalogCardSeed("OPST13-ST13-008-AA", "OPST13", "ST13-008", "Sabo", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-008_p1.png", null),
    CatalogCardSeed("OPST13-ST13-009", "OPST13", "ST13-009", "Shanks", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-009.png", null),
    CatalogCardSeed("OPST13-ST13-009-AA", "OPST13", "ST13-009", "Shanks", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-009_p1.png", null),
    CatalogCardSeed("OPST13-ST13-010", "OPST13", "ST13-010", "Portgas.D.Ace", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-010.png", null),
    CatalogCardSeed("OPST13-ST13-010-AA", "OPST13", "ST13-010", "Portgas.D.Ace", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-010_p1.png", null),
    CatalogCardSeed("OPST13-ST13-011", "OPST13", "ST13-011", "Portgas.D.Ace", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-011.png", null),
    CatalogCardSeed("OPST13-ST13-011-AA", "OPST13", "ST13-011", "Portgas.D.Ace", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-011_p1.png", null),
    CatalogCardSeed("OPST13-ST13-012", "OPST13", "ST13-012", "Makino", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-012.png", null),
    CatalogCardSeed("OPST13-ST13-012-AA", "OPST13", "ST13-012", "Makino", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-012_p1.png", null),
    CatalogCardSeed("OPST13-ST13-013", "OPST13", "ST13-013", "Monkey.D.Garp", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-013.png", null),
    CatalogCardSeed("OPST13-ST13-013-AA", "OPST13", "ST13-013", "Monkey.D.Garp", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-013_p1.png", null),
    CatalogCardSeed("OPST13-ST13-014", "OPST13", "ST13-014", "Monkey.D.Luffy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-014.png", null),
    CatalogCardSeed("OPST13-ST13-014-AA", "OPST13", "ST13-014", "Monkey.D.Luffy", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-014_p1.png", null),
    CatalogCardSeed("OPST13-ST13-015", "OPST13", "ST13-015", "Monkey.D.Luffy", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-015.png", null),
    CatalogCardSeed("OPST13-ST13-015-AA", "OPST13", "ST13-015", "Monkey.D.Luffy", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-015_p1.png", null),
    CatalogCardSeed("OPST13-ST13-016", "OPST13", "ST13-016", "Yamato", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-016.png", null),
    CatalogCardSeed("OPST13-ST13-016-AA", "OPST13", "ST13-016", "Yamato", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-016_p1.png", null),
    CatalogCardSeed("OPST13-ST13-017", "OPST13", "ST13-017", "Flame Dragon King", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-017.png", null),
    CatalogCardSeed("OPST13-ST13-018", "OPST13", "ST13-018", "Gum-Gum Jet Spear", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-018.png", null),
    CatalogCardSeed("OPST13-ST13-019", "OPST13", "ST13-019", "The Three Brothers' Bond", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST13-019.png", null),
)
