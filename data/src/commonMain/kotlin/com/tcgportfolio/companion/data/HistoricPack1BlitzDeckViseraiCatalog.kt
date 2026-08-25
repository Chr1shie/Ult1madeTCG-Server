package com.tcgportfolio.companion.data

val historicPack1BlitzDeckViseraiSetSeed = CardSetSeed(id = "6400006", name = "Historic Pack 1 Blitz Deck: Viserai", game = "FleshAndBlood", totalCards = 27)

// Bilder (11.08.): die meisten Karten dieses Sets waren schon auf echte
// storage.googleapis.com/fabmaster-Scans umgestellt. Die restlichen
// Farbvarianten (Red/Yellow/Blue) dieser konkreten "Historic Pack 1"-
// Nachdrucke fehlen sowohl im vorhandenen FAB-Kartendatenbank-Dump (4941
// Karten, aber ohne diese spezifischen 1HP-Drucke) als auch direkt bei
// fabtcg.com (Cloudflare blockt dort jeden Zugriff). Bleiben auf dem
// "no-image"-Sentinel.
val historicPack1BlitzDeckViseraiCatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("6400006-1HV001", "6400006", "1HV001", "Viserai", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP259.width-450.png", null),
    CatalogCardSeed("6400006-1HV002", "6400006", "1HV002", "Nebula Blade", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP260.width-450.png", 2.0),
    CatalogCardSeed("6400006-1HV003", "6400006", "1HV003", "Crown of Dichotomy", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP263.width-450.png", null),
    CatalogCardSeed("6400006-1HV004", "6400006", "1HV004", "Aether Ironweave", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP259.width-450.png", 1.99),
    CatalogCardSeed("6400006-1HV005", "6400006", "1HV005", "Nullrune Gloves", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP348.width-450.png", 1.24),
    CatalogCardSeed("6400006-1HV006", "6400006", "1HV006", "Sutcliffe's Suede Hides", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP348.width-450.png", 2.0),
    CatalogCardSeed("6400006-1HV007", "6400006", "1HV007", "Amplify the Arknight (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP282.width-450.png", 0.5),
    CatalogCardSeed("6400006-1HV008", "6400006", "1HV008", "Arcanic Crackle (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 0.5),
    CatalogCardSeed("6400006-1HV009", "6400006", "1HV009", "Meat and Greet (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP285.width-450.png", 0.5),
    CatalogCardSeed("6400006-1HV010", "6400006", "1HV010", "Rune Flash (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP288.width-450.png", null),
    CatalogCardSeed("6400006-1HV011", "6400006", "1HV011", "Shrill of Skullform (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400006-1HV012", "6400006", "1HV012", "Read the Runes (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP294.width-450.png", null),
    CatalogCardSeed("6400006-1HV013", "6400006", "1HV013", "Captain's Call (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 1.0),
    CatalogCardSeed("6400006-1HV014", "6400006", "1HV014", "Come to Fight (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP414.width-450.png", 0.5),
    CatalogCardSeed("6400006-1HV015", "6400006", "1HV015", "Belittle (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400006-1HV016", "6400006", "1HV016", "Minnowism (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 0.45),
    CatalogCardSeed("6400006-1HV017", "6400006", "1HV017", "Meat and Greet (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP285.width-450.png", null),
    CatalogCardSeed("6400006-1HV018", "6400006", "1HV018", "Shrill of Skullform (Yellow)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400006-1HV019", "6400006", "1HV019", "Spellblade Assault (Yellow)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP270.width-450.png", 1.0),
    CatalogCardSeed("6400006-1HV020", "6400006", "1HV020", "Arcanic Crackle (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400006-1HV021", "6400006", "1HV021", "Meat and Greet (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP285.width-450.png", null),
    CatalogCardSeed("6400006-1HV022", "6400006", "1HV022", "Shrill of Skullform (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400006-1HV023", "6400006", "1HV023", "Bloodspill Invocation (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP291.width-450.png", null),
    CatalogCardSeed("6400006-1HV024", "6400006", "1HV024", "Mauvrion Skies (Blue)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP276.width-450.png", 1.08),
    CatalogCardSeed("6400006-1HV025", "6400006", "1HV025", "Belittle (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400006-1HV026", "6400006", "1HV026", "Minnowism (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 0.5),
    CatalogCardSeed("6400006-1HV027", "6400006", "1HV027", "Runechant", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP300.width-450.png", 0.69),)
