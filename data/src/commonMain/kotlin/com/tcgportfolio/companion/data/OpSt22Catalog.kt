package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-22 "Ace & Newgate" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569022): 31 Einträge, davon 31 mit eigenem
// ST22-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt22SetSeed = CardSetSeed(id = "OPST22", name = "ST22: Ace & Newgate", game = "OnePiece", totalCards = 31)

val opSt22CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST22-ST22-001", "OPST22", "ST22-001", "Ace & Newgate", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-001.png", null),
    CatalogCardSeed("OPST22-ST22-001-AA", "OPST22", "ST22-001", "Ace & Newgate", "Alternate Art", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-001_p1.png", null),
    CatalogCardSeed("OPST22-ST22-002", "OPST22", "ST22-002", "Izo", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-002.png", null),
    CatalogCardSeed("OPST22-ST22-002-AA", "OPST22", "ST22-002", "Izo", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-002_p1.png", null),
    CatalogCardSeed("OPST22-ST22-003", "OPST22", "ST22-003", "Edward.Newgate", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-003.png", null),
    CatalogCardSeed("OPST22-ST22-003-AA", "OPST22", "ST22-003", "Edward.Newgate", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-003_p1.png", null),
    CatalogCardSeed("OPST22-ST22-004", "OPST22", "ST22-004", "Elmy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-004.png", null),
    CatalogCardSeed("OPST22-ST22-004-AA", "OPST22", "ST22-004", "Elmy", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-004_p1.png", null),
    CatalogCardSeed("OPST22-ST22-005", "OPST22", "ST22-005", "Kouzuki Oden", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-005.png", null),
    CatalogCardSeed("OPST22-ST22-005-AA", "OPST22", "ST22-005", "Kouzuki Oden", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-005_p1.png", null),
    CatalogCardSeed("OPST22-ST22-006", "OPST22", "ST22-006", "Jozu", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-006.png", null),
    CatalogCardSeed("OPST22-ST22-006-AA", "OPST22", "ST22-006", "Jozu", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-006_p1.png", null),
    CatalogCardSeed("OPST22-ST22-007", "OPST22", "ST22-007", "Squard", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-007.png", null),
    CatalogCardSeed("OPST22-ST22-007-AA", "OPST22", "ST22-007", "Squard", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-007_p1.png", null),
    CatalogCardSeed("OPST22-ST22-008", "OPST22", "ST22-008", "Decalvan Brothers", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-008.png", null),
    CatalogCardSeed("OPST22-ST22-008-AA", "OPST22", "ST22-008", "Decalvan Brothers", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-008_p1.png", null),
    CatalogCardSeed("OPST22-ST22-009", "OPST22", "ST22-009", "Vista", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-009.png", null),
    CatalogCardSeed("OPST22-ST22-009-AA", "OPST22", "ST22-009", "Vista", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-009_p1.png", null),
    CatalogCardSeed("OPST22-ST22-010", "OPST22", "ST22-010", "Portgas.D.Ace", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-010.png", null),
    CatalogCardSeed("OPST22-ST22-010-AA", "OPST22", "ST22-010", "Portgas.D.Ace", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-010_p1.png", null),
    CatalogCardSeed("OPST22-ST22-011", "OPST22", "ST22-011", "Whitey Bay", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-011.png", null),
    CatalogCardSeed("OPST22-ST22-011-AA", "OPST22", "ST22-011", "Whitey Bay", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-011_p1.png", null),
    CatalogCardSeed("OPST22-ST22-012", "OPST22", "ST22-012", "Marco", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-012.png", null),
    CatalogCardSeed("OPST22-ST22-012-AA", "OPST22", "ST22-012", "Marco", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-012_p1.png", null),
    CatalogCardSeed("OPST22-ST22-013", "OPST22", "ST22-013", "LittleOars Jr.", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-013.png", null),
    CatalogCardSeed("OPST22-ST22-013-AA", "OPST22", "ST22-013", "LittleOars Jr.", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-013_p1.png", null),
    CatalogCardSeed("OPST22-ST22-014", "OPST22", "ST22-014", "A.O.", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-014.png", null),
    CatalogCardSeed("OPST22-ST22-014-AA", "OPST22", "ST22-014", "A.O.", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-014_p1.png", null),
    CatalogCardSeed("OPST22-ST22-015", "OPST22", "ST22-015", "I Am Whitebeard!!", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-015.png", null),
    CatalogCardSeed("OPST22-ST22-016", "OPST22", "ST22-016", "Take That Back!! Take Back What You Said!!", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-016.png", null),
    CatalogCardSeed("OPST22-ST22-017", "OPST22", "ST22-017", "Fire Fist", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST22-017.png", null),
)
