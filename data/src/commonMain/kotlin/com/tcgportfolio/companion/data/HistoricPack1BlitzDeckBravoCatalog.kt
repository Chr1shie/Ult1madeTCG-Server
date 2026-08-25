package com.tcgportfolio.companion.data

val historicPack1BlitzDeckBravoSetSeed = CardSetSeed(id = "6400014", name = "Historic Pack 1 Blitz Deck: Bravo", game = "FleshAndBlood", totalCards = 27)

// Bilder (11.08.): die meisten Karten dieses Sets waren schon auf echte
// storage.googleapis.com/fabmaster-Scans umgestellt. Die restlichen
// Farbvarianten (Red/Yellow/Blue) dieser konkreten "Historic Pack 1"-
// Nachdrucke fehlen sowohl im vorhandenen FAB-Kartendatenbank-Dump (4941
// Karten, aber ohne diese spezifischen 1HP-Drucke) als auch direkt bei
// fabtcg.com (Cloudflare blockt dort jeden Zugriff). Bleiben auf dem
// "no-image"-Sentinel.
val historicPack1BlitzDeckBravoCatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("6400014-1HB001", "6400014", "1HB001", "Bravo", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP044.width-450.png", null),
    CatalogCardSeed("6400014-1HB002", "6400014", "1HB002", "Anothos", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP045.width-450.png", null),
    CatalogCardSeed("6400014-1HB003", "6400014", "1HB003", "Helm of Isen's Peak", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP048.width-450.png", null),
    CatalogCardSeed("6400014-1HB004", "6400014", "1HB004", "Nullrune Robe", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP347.width-450.png", null),
    CatalogCardSeed("6400014-1HB005", "6400014", "1HB005", "Goliath Gauntlet", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP352.width-450.png", null),
    CatalogCardSeed("6400014-1HB006", "6400014", "1HB006", "Nullrune Boots", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP349.width-450.png", null),
    CatalogCardSeed("6400014-1HB007", "6400014", "1HB007", "Cartilage Crush (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP044.width-450.png", null),
    CatalogCardSeed("6400014-1HB008", "6400014", "1HB008", "Crush Confidence (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP073.width-450.png", null),
    CatalogCardSeed("6400014-1HB009", "6400014", "1HB009", "Debilitate (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP076.width-450.png", null),
    CatalogCardSeed("6400014-1HB010", "6400014", "1HB010", "Thump (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400014-1HB011", "6400014", "1HB011", "Sink Below (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP408.width-450.png", 2.86),
    CatalogCardSeed("6400014-1HB012", "6400014", "1HB012", "Cartilage Crush (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP067.width-450.png", null),
    CatalogCardSeed("6400014-1HB014", "6400014", "1HB014", "Debilitate (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP076.width-450.png", null),
    CatalogCardSeed("6400014-1HB015", "6400014", "1HB015", "Cartilage Crush (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP067.width-450.png", null),
    CatalogCardSeed("6400014-1HB016", "6400014", "1HB016", "Chokeslam (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP070.width-450.png", null),
    CatalogCardSeed("6400014-1HB017", "6400014", "1HB017", "Debilitate (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP076.width-450.png", null),
    CatalogCardSeed("6400014-1HB018", "6400014", "1HB018", "Disable (Blue)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP055.width-450.png", null),
    CatalogCardSeed("6400014-1HB019", "6400014", "1HB019", "Macho Grande (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400014-1HB020", "6400014", "1HB020", "Thunder Quake (Blue)", "Normal", "Rare", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400014-1HB021", "6400014", "1HB021", "Rally the Rearguard (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400014-1HB022", "6400014", "1HB022", "Pummel (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP399.width-450.png", null),
    CatalogCardSeed("6400014-1HB023", "6400014", "1HB023", "Staunch Response (Blue)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP058.width-450.png", null),
    CatalogCardSeed("6400014-1HB024", "6400014", "1HB024", "Unmovable (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP411.width-450.png", 3.0),
    CatalogCardSeed("6400014-1HB025", "6400014", "1HB025", "Emerging Dominance (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP079.width-450.png", null),
    CatalogCardSeed("6400014-1HB026", "6400014", "1HB026", "Sloggism (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP420.width-450.png", null),
    CatalogCardSeed("6400014-1HB027", "6400014", "1HB027", "Seismic Surge", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP085.width-450.png", 1.83),
    CatalogCardSeed("6400014-1hb013", "6400014", "1hb013", "Crush Confidence (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP085.width-450.png", null),)
