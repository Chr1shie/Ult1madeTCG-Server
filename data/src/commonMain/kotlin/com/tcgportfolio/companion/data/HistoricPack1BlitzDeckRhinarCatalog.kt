package com.tcgportfolio.companion.data

val historicPack1BlitzDeckRhinarSetSeed = CardSetSeed(id = "6400040", name = "Historic Pack 1 Blitz Deck: Rhinar", game = "FleshAndBlood", totalCards = 26)

// Bilder (11.08.): die meisten Karten dieses Sets waren schon auf echte
// storage.googleapis.com/fabmaster-Scans umgestellt. Die restlichen
// Farbvarianten (Red/Yellow/Blue) dieser konkreten "Historic Pack 1"-
// Nachdrucke fehlen sowohl im vorhandenen FAB-Kartendatenbank-Dump (4941
// Karten, aber ohne diese spezifischen 1HP-Drucke) als auch direkt bei
// fabtcg.com (Cloudflare blockt dort jeden Zugriff). Bleiben auf dem
// "no-image"-Sentinel.
val historicPack1BlitzDeckRhinarCatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("6400040-1HR001", "6400040", "1HR001", "Rhinar", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP002.width-450.png", null),
    CatalogCardSeed("6400040-1HR002", "6400040", "1HR002", "Romping Club", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP006.width-450.png", null),
    CatalogCardSeed("6400040-1HR003", "6400040", "1HR003", "Nullrune Hood", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP346.width-450.png", 1.99),
    CatalogCardSeed("6400040-1HR004", "6400040", "1HR004", "Barkbone Strapping", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP008.width-450.png", null),
    CatalogCardSeed("6400040-1HR005", "6400040", "1HR005", "Goliath Gauntlet", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP352.width-450.png", null),
    CatalogCardSeed("6400040-1HR006", "6400040", "1HR006", "Nullrune Boots", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP349.width-450.png", 1.16),
    CatalogCardSeed("6400040-1HR007", "6400040", "1HR007", "Bare Fangs (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP002.width-450.png", null),
    CatalogCardSeed("6400040-1HR008", "6400040", "1HR008", "Pack Hunt (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP025.width-450.png", null),
    CatalogCardSeed("6400040-1HR009", "6400040", "1HR009", "Pulping (Red)", "Normal", "Rare", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 1.2),
    CatalogCardSeed("6400040-1HR010", "6400040", "1HR010", "Riled Up (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP028.width-450.png", null),
    CatalogCardSeed("6400040-1HR011", "6400040", "1HR011", "Savage Swing (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP031.width-450.png", null),
    CatalogCardSeed("6400040-1HR012", "6400040", "1HR012", "Smash Instinct (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP034.width-450.png", null),
    CatalogCardSeed("6400040-1HR013", "6400040", "1HR013", "Wild Ride (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400040-1HR014", "6400040", "1HR014", "Wrecker Romp (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP037.width-450.png", 0.5),
    CatalogCardSeed("6400040-1HR015", "6400040", "1HR015", "Rally the Rearguard (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400040-1HR016", "6400040", "1HR016", "Barraging Beatdown (Red)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP022.width-450.png", 0.75),
    CatalogCardSeed("6400040-1HR017", "6400040", "1HR017", "Riled Up (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP028.width-450.png", null),
    CatalogCardSeed("6400040-1HR018", "6400040", "1HR018", "Savage Swing (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP031.width-450.png", null),
    CatalogCardSeed("6400040-1HR019", "6400040", "1HR019", "Smash Instinct (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP034.width-450.png", null),
    CatalogCardSeed("6400040-1HR020", "6400040", "1HR020", "Wrecker Romp (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP037.width-450.png", 0.5),
    CatalogCardSeed("6400040-1HR021", "6400040", "1HR021", "Pummel (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP399.width-450.png", 0.5),
    CatalogCardSeed("6400040-1HR022", "6400040", "1HR022", "Wrecker Romp (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP037.width-450.png", null),
    CatalogCardSeed("6400040-1HR023", "6400040", "1HR023", "Pummel (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP399.width-450.png", 0.5),
    CatalogCardSeed("6400040-1HR024", "6400040", "1HR024", "Come to Fight (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP414.width-450.png", null),
    CatalogCardSeed("6400040-1HR025", "6400040", "1HR025", "High Roller (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400040-1HR026", "6400040", "1HR026", "Primeval Bellow (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP040.width-450.png", null),)
