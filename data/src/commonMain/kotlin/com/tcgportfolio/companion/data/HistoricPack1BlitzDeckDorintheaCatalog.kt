package com.tcgportfolio.companion.data

val historicPack1BlitzDeckDorintheaSetSeed = CardSetSeed(id = "6400030", name = "Historic Pack 1 Blitz Deck: Dorinthea", game = "FleshAndBlood", totalCards = 27)

// Bilder (11.08.): die meisten Karten dieses Sets waren schon auf echte
// storage.googleapis.com/fabmaster-Scans umgestellt. Die restlichen
// Farbvarianten (Red/Yellow/Blue) dieser konkreten "Historic Pack 1"-
// Nachdrucke fehlen sowohl im vorhandenen FAB-Kartendatenbank-Dump (4941
// Karten, aber ohne diese spezifischen 1HP-Drucke) als auch direkt bei
// fabtcg.com (Cloudflare blockt dort jeden Zugriff). Bleiben auf dem
// "no-image"-Sentinel.
val historicPack1BlitzDeckDorintheaCatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("6400030-1HT001", "6400030", "1HT001", "Dorinthea", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP139.width-450.png", 2.0),
    CatalogCardSeed("6400030-1HT002", "6400030", "1HT002", "Dawnblade", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP143.width-450.png", 2.0),
    CatalogCardSeed("6400030-1HT003", "6400030", "1HT003", "Nullrune Hood", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP346.width-450.png", 1.0),
    CatalogCardSeed("6400030-1HT004", "6400030", "1HT004", "Nullrune Robe", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP347.width-450.png", null),
    CatalogCardSeed("6400030-1HT005", "6400030", "1HT005", "Gallantry Gold", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP139.width-450.png", null),
    CatalogCardSeed("6400030-1HT006", "6400030", "1HT006", "Refraction Bolters", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP145.width-450.png", null),
    CatalogCardSeed("6400030-1HT007", "6400030", "1HT007", "In the Swing (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP145.width-450.png", null),
    CatalogCardSeed("6400030-1HT008", "6400030", "1HT008", "Ironsong Response (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP162.width-450.png", null),
    CatalogCardSeed("6400030-1HT009", "6400030", "1HT009", "Out for Blood (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP165.width-450.png", null),
    CatalogCardSeed("6400030-1HT010", "6400030", "1HT010", "Stroke of Foresight (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP168.width-450.png", null),
    CatalogCardSeed("6400030-1HT011", "6400030", "1HT011", "Razor Reflex (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP402.width-450.png", 1.0),
    CatalogCardSeed("6400030-1HT012", "6400030", "1HT012", "Driving Blade (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP171.width-450.png", null),
    CatalogCardSeed("6400030-1HT013", "6400030", "1HT013", "Slice and Dice (Red)", "Normal", "Rare", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400030-1HT014", "6400030", "1HT014", "Warrior's Valor (Red)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP159.width-450.png", null),
    CatalogCardSeed("6400030-1HT015", "6400030", "1HT015", "Flock of the Feather Walkers (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP390.width-450.png", 0.23),
    CatalogCardSeed("6400030-1HT016", "6400030", "1HT016", "Ironsong Response (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP390.width-450.png", null),
    CatalogCardSeed("6400030-1HT017", "6400030", "1HT017", "Sink Below (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP408.width-450.png", 1.0),
    CatalogCardSeed("6400030-1HT018", "6400030", "1HT018", "Driving Blade (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP171.width-450.png", null),
    CatalogCardSeed("6400030-1HT019", "6400030", "1HT019", "Hit and Run (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP174.width-450.png", null),
    CatalogCardSeed("6400030-1HT020", "6400030", "1HT020", "In the Swing (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400030-1HT021", "6400030", "1HT021", "Ironsong Response (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP162.width-450.png", null),
    CatalogCardSeed("6400030-1HT022", "6400030", "1HT022", "Out for Blood (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP165.width-450.png", null),
    CatalogCardSeed("6400030-1HT023", "6400030", "1HT023", "Overpower (Blue)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP153.width-450.png", 2.2),
    CatalogCardSeed("6400030-1HT024", "6400030", "1HT024", "Stroke of Foresight (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP168.width-450.png", null),
    CatalogCardSeed("6400030-1HT025", "6400030", "1HT025", "Driving Blade (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP171.width-450.png", null),
    CatalogCardSeed("6400030-1HT026", "6400030", "1HT026", "Hit and Run (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP174.width-450.png", null),
    CatalogCardSeed("6400030-1HT027", "6400030", "1HT027", "Quicken", "Normal", "Token", "https://legendstory-production-s3-public.s3.amazonaws.com/media/cards/large/1HP427.webp", null),)
