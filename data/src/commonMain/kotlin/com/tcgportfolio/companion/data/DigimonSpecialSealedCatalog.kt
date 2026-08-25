package com.tcgportfolio.companion.data

// Bilder (11.08.) - echte offizielle Packungsfotos von Bandais Digimon-Card-
// Game-Seite. DGSB1/DGSB2 (die kombinierten "Special Booster Ver.2.0/2.5"
// [BT18-19]/[BT19-20]) sind auf en.digimoncard.com nicht mehr eigenständig
// gelistet, nur noch auf der Schwesterseite world.digimoncard.com - dieselbe
// Bandai-Domain-Familie, deshalb trotzdem verwendet statt eine dritte Quelle
// zu suchen. DGSB4 "Resurgence Booster" bleibt auf den "no-image"-Sentinel
// zurückgestellt: der Produktcode RB-01 wurde inzwischen für ein komplett
// anderes, neueres Produkt ("Reboot Booster Rising Wind") wiederverwendet -
// ein Bild von dort zu nehmen wäre schlicht falsch zugeordnet.
val digimonSpecialSealedCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("DGSB1", "Release Special Booster 2.0", "Special Booster", "Digimon", "https://world.digimoncard.com/images/products/pack/ver18-19/img_pkg.png", 87.33),
    SealedCatalogSeed("DGSB2", "Release Special Booster 2.5", "Special Booster", "Digimon", "https://world.digimoncard.com/images/products/pack/ver19-20/img_pkg.png", 240.6),
    SealedCatalogSeed("DGSB3", "Alternative Being Booster", "Special Booster", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-04/img_pkg.png", 69.63),
    SealedCatalogSeed("DGSB4", "Resurgence Booster", "Special Booster", "Digimon", "https://no-image.tcgportfolio.internal/dg-no-photo-yet", 50.99),
    SealedCatalogSeed("DGSB5", "Classic Collection", "Special Booster", "Digimon", "https://en.digimoncard.com/images/products/pack/ex-01/img_pkg.png", 53.5),
    SealedCatalogSeed("DGLP1", "Limited Card Pack -Billion Bullet-", "Limited Pack", "Digimon", "https://en.digimoncard.com/images/products/goods/limited-lm-06/img_pkg.png", 58.04),
    SealedCatalogSeed("DGLP2", "Limited Card Pack -Another Knight-", "Limited Pack", "Digimon", "https://en.digimoncard.com/images/products/goods/limited-lm-07/img_pkg.png", 64.32),
)
