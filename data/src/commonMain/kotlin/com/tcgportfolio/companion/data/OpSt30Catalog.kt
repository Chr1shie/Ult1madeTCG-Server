package com.tcgportfolio.companion.data

// One Piece Starter Deck ST-30 "Starter Deck EX: Luffy & Ace" (28.09., Katalog-Nachtrag - Nutzer-
// Entscheidung: Starter-Deck-Karten als eigene Sets). Quelle: offizielle Kartenliste
// en.onepiece-cardgame.com (series 569030): 34 Einträge, davon 34 mit eigenem
// ST30-Code, der Rest Nachdrucke/Parallelen anderer Sets mit ihrem Original-Code
// (_rN -> "Reprint N", _pN -> "Alternate Art N"). Set-Id "OPSTnn" ohne Bindestrich,
// weil "ST01".."ST10" von Gundam belegt sind; Karten-Id "OPSTnn-<Code>".
// totalCards = Einträge. Generator: tools/catalog/generate_onepiece_starter_decks.py
val opSt30SetSeed = CardSetSeed(id = "OPST30", name = "ST30: Starter Deck EX: Luffy & Ace", game = "OnePiece", totalCards = 34)

val opSt30CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("OPST30-ST30-001", "OPST30", "ST30-001", "Luffy & Ace", "Normal", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-001.png", null),
    CatalogCardSeed("OPST30-ST30-001-AA", "OPST30", "ST30-001", "Luffy & Ace", "Alternate Art", "L", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-001_p1.png", null),
    CatalogCardSeed("OPST30-ST30-002", "OPST30", "ST30-002", "Inazuma", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-002.png", null),
    CatalogCardSeed("OPST30-ST30-002-AA", "OPST30", "ST30-002", "Inazuma", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-002_p1.png", null),
    CatalogCardSeed("OPST30-ST30-003", "OPST30", "ST30-003", "Edward.Newgate", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-003.png", null),
    CatalogCardSeed("OPST30-ST30-003-AA", "OPST30", "ST30-003", "Edward.Newgate", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-003_p1.png", null),
    CatalogCardSeed("OPST30-ST30-004", "OPST30", "ST30-004", "Emporio.Ivankov", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-004.png", null),
    CatalogCardSeed("OPST30-ST30-004-AA", "OPST30", "ST30-004", "Emporio.Ivankov", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-004_p1.png", null),
    CatalogCardSeed("OPST30-ST30-005", "OPST30", "ST30-005", "Jozu", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-005.png", null),
    CatalogCardSeed("OPST30-ST30-005-AA", "OPST30", "ST30-005", "Jozu", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-005_p1.png", null),
    CatalogCardSeed("OPST30-ST30-006", "OPST30", "ST30-006", "Jinbe", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-006.png", null),
    CatalogCardSeed("OPST30-ST30-006-AA", "OPST30", "ST30-006", "Jinbe", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-006_p1.png", null),
    CatalogCardSeed("OPST30-ST30-007", "OPST30", "ST30-007", "Portgas.D.Ace", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-007.png", null),
    CatalogCardSeed("OPST30-ST30-007-AA", "OPST30", "ST30-007", "Portgas.D.Ace", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-007_p1.png", null),
    CatalogCardSeed("OPST30-ST30-008", "OPST30", "ST30-008", "Marco", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-008.png", null),
    CatalogCardSeed("OPST30-ST30-008-AA", "OPST30", "ST30-008", "Marco", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-008_p1.png", null),
    CatalogCardSeed("OPST30-ST30-009", "OPST30", "ST30-009", "LittleOars Jr.", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-009.png", null),
    CatalogCardSeed("OPST30-ST30-009-AA", "OPST30", "ST30-009", "LittleOars Jr.", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-009_p1.png", null),
    CatalogCardSeed("OPST30-ST30-010", "OPST30", "ST30-010", "Crocodile", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-010.png", null),
    CatalogCardSeed("OPST30-ST30-010-AA", "OPST30", "ST30-010", "Crocodile", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-010_p1.png", null),
    CatalogCardSeed("OPST30-ST30-011", "OPST30", "ST30-011", "Buggy", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-011.png", null),
    CatalogCardSeed("OPST30-ST30-011-AA", "OPST30", "ST30-011", "Buggy", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-011_p1.png", null),
    CatalogCardSeed("OPST30-ST30-012", "OPST30", "ST30-012", "Monkey.D.Luffy", "Normal", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-012.png", null),
    CatalogCardSeed("OPST30-ST30-012-AA", "OPST30", "ST30-012", "Monkey.D.Luffy", "Alternate Art", "SR", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-012_p1.png", null),
    CatalogCardSeed("OPST30-ST30-013", "OPST30", "ST30-013", "Mr.2.Bon.Kurei(Bentham)", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-013.png", null),
    CatalogCardSeed("OPST30-ST30-013-AA", "OPST30", "ST30-013", "Mr.2.Bon.Kurei(Bentham)", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-013_p1.png", null),
    CatalogCardSeed("OPST30-ST30-014", "OPST30", "ST30-014", "Mr.3(Galdino)", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-014.png", null),
    CatalogCardSeed("OPST30-ST30-014-AA", "OPST30", "ST30-014", "Mr.3(Galdino)", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-014_p1.png", null),
    CatalogCardSeed("OPST30-ST30-015", "OPST30", "ST30-015", "The Name of This Era Is \"Whitebeard\"!!", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-015.png", null),
    CatalogCardSeed("OPST30-ST30-015-AA", "OPST30", "ST30-015", "The Name of This Era Is \"Whitebeard\"!!", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-015_p1.png", null),
    CatalogCardSeed("OPST30-ST30-016", "OPST30", "ST30-016", "Can You Still Fight, Luffy?! Of Course!!", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-016.png", null),
    CatalogCardSeed("OPST30-ST30-016-AA", "OPST30", "ST30-016", "Can You Still Fight, Luffy?! Of Course!!", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-016_p1.png", null),
    CatalogCardSeed("OPST30-ST30-017", "OPST30", "ST30-017", "And You Get Yourself in Big Trouble!!", "Normal", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-017.png", null),
    CatalogCardSeed("OPST30-ST30-017-AA", "OPST30", "ST30-017", "And You Get Yourself in Big Trouble!!", "Alternate Art", "C", "https://en.onepiece-cardgame.com/images/cardlist/card/ST30-017_p1.png", null),
)
