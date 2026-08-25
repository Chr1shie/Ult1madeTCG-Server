package com.tcgportfolio.companion.data

val historicPack1BlitzDeckDashSetSeed = CardSetSeed(id = "6400076", name = "Historic Pack 1 Blitz Deck: Dash", game = "FleshAndBlood", totalCards = 28)

// Bilder (11.08.): die meisten Karten dieses Sets waren schon auf echte
// storage.googleapis.com/fabmaster-Scans umgestellt. Die restlichen
// Farbvarianten (Red/Yellow/Blue) dieser konkreten "Historic Pack 1"-
// Nachdrucke fehlen sowohl im vorhandenen FAB-Kartendatenbank-Dump (4941
// Karten, aber ohne diese spezifischen 1HP-Drucke) als auch direkt bei
// fabtcg.com (Cloudflare blockt dort jeden Zugriff). Bleiben auf dem
// "no-image"-Sentinel.
val historicPack1BlitzDeckDashCatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("6400076-1HD001", "6400076", "1HD001", "Dash", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP181.width-450.png", null),
    CatalogCardSeed("6400076-1HD002", "6400076", "1HD002", "Teklo Plasma Pistol", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP184.width-450.png", null),
    CatalogCardSeed("6400076-1HD003", "6400076", "1HD003", "Nullrune Hood", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP346.width-450.png", null),
    CatalogCardSeed("6400076-1HD004", "6400076", "1HD004", "Deep Blue", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP181.width-450.png", null),
    CatalogCardSeed("6400076-1HD005", "6400076", "1HD005", "Goliath Gauntlet", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP352.width-450.png", null),
    CatalogCardSeed("6400076-1HD006", "6400076", "1HD006", "Achilles Accelerator", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP186.width-450.png", null),
    CatalogCardSeed("6400076-1HD007", "6400076", "1HD007", "Cognition Nodes (Blue)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP352.width-450.png", 1.0),
    CatalogCardSeed("6400076-1HD008", "6400076", "1HD008", "Convection Amplifier (Red)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP201.width-450.png", null),
    CatalogCardSeed("6400076-1HD009", "6400076", "1HD009", "Hyper Driver (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP218.width-450.png", null),
    CatalogCardSeed("6400076-1HD010", "6400076", "1HD010", "High Speed Impact (Red)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP193.width-450.png", null),
    CatalogCardSeed("6400076-1HD011", "6400076", "1HD011", "Over Loop (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP205.width-450.png", null),
    CatalogCardSeed("6400076-1HD012", "6400076", "1HD012", "Payload (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 0.53),
    CatalogCardSeed("6400076-1HD013", "6400076", "1HD013", "Pedal to the Metal (Red)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP196.width-450.png", null),
    CatalogCardSeed("6400076-1HD014", "6400076", "1HD014", "Throttle (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP208.width-450.png", 0.25),
    CatalogCardSeed("6400076-1HD015", "6400076", "1HD015", "Zero to Sixty (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP211.width-450.png", null),
    CatalogCardSeed("6400076-1HD016", "6400076", "1HD016", "Zipper Hit (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP214.width-450.png", null),
    CatalogCardSeed("6400076-1HD017", "6400076", "1HD017", "Rotary Ram (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400076-1HD018", "6400076", "1HD018", "Over Loop (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP205.width-450.png", null),
    CatalogCardSeed("6400076-1HD019", "6400076", "1HD019", "Payload (Yellow)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400076-1HD020", "6400076", "1HD020", "Throttle (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP208.width-450.png", null),
    CatalogCardSeed("6400076-1HD021", "6400076", "1HD021", "Zipper Hit (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP214.width-450.png", null),
    CatalogCardSeed("6400076-1HD022", "6400076", "1HD022", "Combustible Courier (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP202.width-450.png", null),
    CatalogCardSeed("6400076-1HD023", "6400076", "1HD023", "Over Loop (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP205.width-450.png", null),
    CatalogCardSeed("6400076-1HD024", "6400076", "1HD024", "Payload (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400076-1HD025", "6400076", "1HD025", "Throttle (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP208.width-450.png", 0.53),
    CatalogCardSeed("6400076-1HD026", "6400076", "1HD026", "Zero to Sixty (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP211.width-450.png", 0.25),
    CatalogCardSeed("6400076-1HD027", "6400076", "1HD027", "Zipper Hit (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP214.width-450.png", null),
    CatalogCardSeed("6400076-1HD028", "6400076", "1HD028", "Zoom In (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
)
