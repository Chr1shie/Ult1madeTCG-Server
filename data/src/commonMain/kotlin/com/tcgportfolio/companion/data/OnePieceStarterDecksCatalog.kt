package com.tcgportfolio.companion.data

// Bilder (11.08., Nutzer-Vorgabe "OnePiece Sealed-Produkte checken, ist auch
// Bandai") - echte offizielle Packungsfotos direkt von Bandais eigener One-
// Piece-Card-Game-Seite (en.onepiece-cardgame.com/products/decks/), über
// deren Produktseiten-Listing ermittelt - kein Alt-CDN-Hotlink mehr. Die
// ersten 4 (Straw Hat Crew/Worst Generation/Seven Warlords/Animal Kingdom
// Pirates) sowie die 6 Farb-Decks RED/GREEN/BLUE/PURPLE/BLACK/YELLOW teilen
// sich jeweils EIN offizielles Sammelfoto (Bandai selbst führt diese Decks
// nur gebündelt auf einer gemeinsamen Produktseite, keine einzelnen
// Packungsfotos verfügbar) - trotzdem ein echtes offizielles Foto, kein
// Platzhalter.
val onePieceStarterDecksCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("OPSD01", "Starter Deck 1: Straw Hat Crew", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st01-04/mv_01.jpg", 54.79),
    SealedCatalogSeed("OPSD02", "Starter Deck 2: Worst Generation", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st01-04/mv_01.jpg", 20.7),
    SealedCatalogSeed("OPSD03", "Starter Deck 3: The Seven Warlords of The Sea", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st01-04/mv_01.jpg", 20.4),
    SealedCatalogSeed("OPSD04", "Starter Deck 4: Animal Kingdom Pirates", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st01-04/mv_01.jpg", 26.03),
    SealedCatalogSeed("OPSD05", "Starter Deck 5: Film Edition", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st05/mv_01.jpg", 25.91),
    SealedCatalogSeed("OPSD06", "Starter Deck 6: Absolute Justice", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st06/mv_01.jpg", 24.47),
    SealedCatalogSeed("OPSD07", "Starter Deck 7: Big Mom Pirates", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st07/mv_01.jpg", 45.0),
    SealedCatalogSeed("OPSD08", "Starter Deck 8: Monkey.D.Luffy", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st08/mv_01.jpg", 18.38),
    SealedCatalogSeed("OPSD09", "Starter Deck 9: Yamato", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st09/mv_01.jpg", 16.9),
    SealedCatalogSeed("OPSD11", "Starter Deck 11: Uta", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st11/mv_01.jpg", 19.26),
    SealedCatalogSeed("OPSD12", "Starter Deck 12: Zoro and Sanji", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st12/mv_01.jpg", 52.42),
    SealedCatalogSeed("OPSD14", "Starter Deck 14: 3D2Y", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st14/mv_01.jpg", 21.41),
    SealedCatalogSeed("OPSD15", "Starter Deck 15: RED Edward.Newgate", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st15-20/mv_01.jpg", 60.08),
    SealedCatalogSeed("OPSD16", "Starter Deck 16: GREEN Uta", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st15-20/mv_01.jpg", 50.92),
    SealedCatalogSeed("OPSD17", "Starter Deck 17: BLUE Donquixote Doflamingo", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st15-20/mv_01.jpg", 78.74),
    SealedCatalogSeed("OPSD18", "Starter Deck 18: PURPLE Monkey.D.Luffy", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st15-20/mv_01.jpg", 156.84),
    SealedCatalogSeed("OPSD19", "Starter Deck 19: BLACK Smoker", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st15-20/mv_01.jpg", 48.31),
    SealedCatalogSeed("OPSD20", "Starter Deck 20: YELLOW Charlotte Katakuri", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st15-20/mv_01.jpg", 50.91),
    SealedCatalogSeed("OPSDEX1", "Starter Deck EX: Gear 5", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st21/mv_01.jpg", 88.64),
    SealedCatalogSeed("OPSD22", "Starter Deck 22: Ace & Newgate", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/images/products/decks/st22/mv_01.jpg", 18.35),
    SealedCatalogSeed("OPSD23", "Starter Deck 23: RED Shanks", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/renewal/images/products/decks/st23/img_item01.webp", 44.36),
    SealedCatalogSeed("OPSD24", "Starter Deck 24: GREEN Jewelry Bonney", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/renewal/images/products/decks/st24/img_item01.webp", 29.74),
    SealedCatalogSeed("OPSD25", "Starter Deck 25: BLUE Buggy", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/renewal/images/products/decks/st25/img_item01.webp", 31.19),
    SealedCatalogSeed("OPSD26", "Starter Deck 26: PURPLE/BLACK Monkey.D.Luffy", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/renewal/images/products/decks/st26/img_item01.webp", 39.52),
    SealedCatalogSeed("OPSD27", "Starter Deck 27: BLACK Marshall.D.Teach", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/renewal/images/products/decks/st27/img_item01.webp", 35.58),
    SealedCatalogSeed("OPSD28", "Starter Deck 28: GREEN/YELLOW Yamato", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/renewal/images/products/decks/st28/img_item01.webp", 32.34),
    SealedCatalogSeed("OPSD29", "Starter Deck 29: Egghead", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/renewal/images/products/decks/st29/img_item01.webp", 40.25),
    SealedCatalogSeed("OPSDEX2", "Starter Deck EX: Luffy & Ace", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/onepiececg/bccard/en/product/2026/05/19/7gK7Er2nPF2EiZov/img_item01.webp", 40.72),
    // ST-31 bis ST-36 (18.08.2026, Nutzer-Fund "Starter Deck ST-33 und
    // ST-35 sind nicht im Katalog"): die Sechs-Farben-Welle vom 31.07.2026
    // (EN) - ein Deck pro Spielfarbe, jeweils mit einem OP-16-Booster im
    // Karton. ST-30 ist KEIN Teil der Welle, sondern das EX-Deck Luffy &
    // Ace (= OPSDEX2 oben). Bild-URLs von den offiziellen Produktseiten
    // (gehashte Pfade, alle sechs per HTTP 200 verifiziert; st31 liegt
    // unter /product/, die übrigen unter /products/ - kein Tippfehler).
    // Preis: UVP-Niveau (~13 EUR), Cardmarket-Trend gibt es so kurz nach
    // Release noch nicht.
    SealedCatalogSeed("OPSD31", "Starter Deck 31: RED Monkey.D.Luffy", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/onepiececg/bccard/en/product/2026/07/08/GNjaEnN1nylgLQ0B/img_item01.webp", 12.99),
    SealedCatalogSeed("OPSD32", "Starter Deck 32: GREEN Roronoa Zoro", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/onepiececg/bccard/en/products/2026/07/08/OBo5ApiaFqYjXuL9/img_item01.webp", 12.99),
    SealedCatalogSeed("OPSD33", "Starter Deck 33: BLUE Kuzan", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/onepiececg/bccard/en/products/2026/07/08/nEq2o760PGK6eiey/img_item01.webp", 12.99),
    SealedCatalogSeed("OPSD34", "Starter Deck 34: PURPLE Charlotte Katakuri", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/onepiececg/bccard/en/products/2026/07/08/TBnEnTa0vjjCEWx6/img_item01.webp", 12.99),
    SealedCatalogSeed("OPSD35", "Starter Deck 35: RED/BLACK Sabo", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/onepiececg/bccard/en/products/2026/07/08/HtuUKv9pCAGqiFvq/img_item01.webp", 12.99),
    SealedCatalogSeed("OPSD36", "Starter Deck 36: YELLOW Eustass \"Captain\" Kid", "Starter Deck", "OnePiece", "https://en.onepiece-cardgame.com/onepiececg/bccard/en/products/2026/07/08/e9kPAYzpFy1Ic3Tm/img_item01.webp", 12.99),
)
