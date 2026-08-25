package com.tcgportfolio.companion.data

// Booster Boxen aller 30 importierten Digimon-Hauptsets (Stand 2026-07-21),
// Preise von der Alt-Katalog-API. Bilder (11.08., Nutzer-Vorgabe "restliche Sealed-
// Produkte durchgehen") - echte offizielle Packungsfotos direkt von Bandais
// eigener Digimon-Card-Game-Seite (en.digimoncard.com/products/pack/), über
// deren Produktlisting ermittelt - kein Alt-CDN-Hotlink mehr.
val digimonBoosterBoxesCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("BB-BT12", "Across Time Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver12/img_pkg.png", 79.17),
    SealedCatalogSeed("BB-EX05", "Animal Colosseum Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-05/img_pkg.png", 72.09),
    SealedCatalogSeed("BB-BT05", "Battle of Omni Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver5/img_pkg.png", 53.57),
    SealedCatalogSeed("BB-BT14", "Blast Ace Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver14/img_pkg.png", 58.95),
    SealedCatalogSeed("BB-BT16", "Beginning Observer Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver16/img_pkg.png", 274.49),
    SealedCatalogSeed("BB-EX08", "Chain of Liberation Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-08/img_pkg.png", 140.35),
    SealedCatalogSeed("BB-EX03", "Draconic Roar Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-03/img_pkg.png", 56.54),
    SealedCatalogSeed("BB-BT22", "Cyber Eden Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver22/img_pkg.png", 74.11),
    SealedCatalogSeed("BB-EX11", "Dawn of Liberator Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-11/img_pkg.png", 88.88),
    SealedCatalogSeed("BB-EX07", "Digimon LIBERATOR Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-07/img_pkg.png", 210.77),
    SealedCatalogSeed("BB-EX12", "Digital World Shambala Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-12/img_pkg.png", 111.99),
    SealedCatalogSeed("BB-EX02", "Digital Hazard Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-02/img_pkg.png", 100.32),
    SealedCatalogSeed("BB-BT06", "Double Diamond Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver6/img_pkg.png", 86.7),
    SealedCatalogSeed("BB-BT11", "Dimensional Phase Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver11/img_pkg.png", 125.52),
    SealedCatalogSeed("BB-BT25", "Dual Revolution Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver25/img_pkg.png", 98.82),
    SealedCatalogSeed("BB-BT15", "Exceed Apocalypse Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver15/img_pkg.png", 73.7),
    SealedCatalogSeed("BB-BT23", "Hackers' Slumber Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver23/img_pkg.png", 53.81),
    SealedCatalogSeed("BB-BT04", "Great Legend Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver4/img_pkg.png", 45.41),
    SealedCatalogSeed("BB-EX06", "Infernal Ascension Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-06/img_pkg.png", 272.99),
    SealedCatalogSeed("BB-BT08", "New Awakening Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver8/img_pkg.png", 48.69),
    SealedCatalogSeed("BB-BT07", "Next Adventure Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver7/img_pkg.png", 55.07),
    SealedCatalogSeed("BB-BT17", "Secret Crisis Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver17/img_pkg.png", 193.57),
    SealedCatalogSeed("BB-EX10", "Sinister Order Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-10/img_pkg.png", 57.96),
    SealedCatalogSeed("BB-BT24", "Time Stranger Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver24/img_pkg.png", 86.79),
    SealedCatalogSeed("BB-BT26", "Timeless Bonds Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver26/img_pkg.png", 143.67),
    SealedCatalogSeed("BB-BT13", "Versus Royal Knight Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver13/img_pkg.png", 133.16),
    SealedCatalogSeed("BB-BT10", "Xros Encounter Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver10/img_pkg.png", 76.27),
    SealedCatalogSeed("BB-EX09", "Versus Monsters Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-09/img_pkg.png", 63.01),
    SealedCatalogSeed("BB-BT21", "World Convergence Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver21/img_pkg.png", 84.24),
    SealedCatalogSeed("BB-BT09", "X Record Booster Box", "Booster Box", "Digimon", "https://en.digimoncard.com/images/products/pack/ver9/img_pkg.png", 129.45),
)
