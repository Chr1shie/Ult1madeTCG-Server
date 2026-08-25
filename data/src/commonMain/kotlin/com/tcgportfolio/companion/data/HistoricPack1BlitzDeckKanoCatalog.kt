package com.tcgportfolio.companion.data

val historicPack1BlitzDeckKanoSetSeed = CardSetSeed(id = "6400050", name = "Historic Pack 1 Blitz Deck: Kano", game = "FleshAndBlood", totalCards = 27)

// Bilder (11.08.): die meisten Karten dieses Sets waren schon auf echte
// storage.googleapis.com/fabmaster-Scans umgestellt. Die restlichen
// Farbvarianten (Red/Yellow/Blue) dieser konkreten "Historic Pack 1"-
// Nachdrucke fehlen sowohl im vorhandenen FAB-Kartendatenbank-Dump (4941
// Karten, aber ohne diese spezifischen 1HP-Drucke) als auch direkt bei
// fabtcg.com (Cloudflare blockt dort jeden Zugriff). Bleiben auf dem
// "no-image"-Sentinel.
val historicPack1BlitzDeckKanoCatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("6400050-1HK001", "6400050", "1HK001", "Kano", "Normal", "Token", "https://storage.googleapis.com/fabmaster/media/images/1HP302.width-450.png", 3.1),
    CatalogCardSeed("6400050-1HK002", "6400050", "1HK002", "Crucible of Aetherweave", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP303.width-450.png", 2.0),
    CatalogCardSeed("6400050-1HK003", "6400050", "1HK003", "Talismanic Lens", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP354.width-450.png", null),
    CatalogCardSeed("6400050-1HK004", "6400050", "1HK004", "Robe of Rapture", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP306.width-450.png", null),
    CatalogCardSeed("6400050-1HK005", "6400050", "1HK005", "Nullrune Gloves", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP348.width-450.png", 2.0),
    CatalogCardSeed("6400050-1HK006", "6400050", "1HK006", "Mage Master Boots", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP357.width-450.png", null),
    CatalogCardSeed("6400050-1HK007", "6400050", "1HK007", "Aether Flare (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP302.width-450.png", null),
    CatalogCardSeed("6400050-1HK008", "6400050", "1HK008", "Aether Spindle (Red)", "Normal", "Rare", "https://storage.googleapis.com/fabmaster/media/images/1HP314.width-450.png", null),
    CatalogCardSeed("6400050-1HK009", "6400050", "1HK009", "Reverberate (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP326.width-450.png", 2.76),
    CatalogCardSeed("6400050-1HK010", "6400050", "1HK010", "Scalding Rain (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP329.width-450.png", null),
    CatalogCardSeed("6400050-1HK011", "6400050", "1HK011", "Timekeeper's Whim (Red)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 1.45),
    CatalogCardSeed("6400050-1HK012", "6400050", "1HK012", "Voltic Bolt (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP335.width-450.png", null),
    CatalogCardSeed("6400050-1HK013", "6400050", "1HK013", "Zap (Red)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP338.width-450.png", null),
    CatalogCardSeed("6400050-1HK014", "6400050", "1HK014", "Scalding Rain (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP329.width-450.png", null),
    CatalogCardSeed("6400050-1HK015", "6400050", "1HK015", "Voltic Bolt (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP335.width-450.png", null),
    CatalogCardSeed("6400050-1HK016", "6400050", "1HK016", "Zap (Yellow)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP338.width-450.png", null),
    CatalogCardSeed("6400050-1HK017", "6400050", "1HK017", "Aether Flare (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP323.width-450.png", null),
    CatalogCardSeed("6400050-1HK018", "6400050", "1HK018", "Emeritus Scolding (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 2.0),
    CatalogCardSeed("6400050-1HK019", "6400050", "1HK019", "Pry (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", null),
    CatalogCardSeed("6400050-1HK020", "6400050", "1HK020", "Reverberate (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP326.width-450.png", 2.7),
    CatalogCardSeed("6400050-1HK021", "6400050", "1HK021", "Scalding Rain (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP329.width-450.png", null),
    CatalogCardSeed("6400050-1HK022", "6400050", "1HK022", "Timekeeper's Whim (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 1.45),
    CatalogCardSeed("6400050-1HK023", "6400050", "1HK023", "Voltic Bolt (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP335.width-450.png", null),
    CatalogCardSeed("6400050-1HK024", "6400050", "1HK024", "Zap (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP338.width-450.png", null),
    CatalogCardSeed("6400050-1HK025", "6400050", "1HK025", "Whisper of the Oracle (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP423.width-450.png", null),
    CatalogCardSeed("6400050-1HK026", "6400050", "1HK026", "Energy Potion (Blue)", "Normal", "Common", "https://storage.googleapis.com/fabmaster/media/images/1HP381.width-450.png", 2.6),
    CatalogCardSeed("6400050-1HK027", "6400050", "1HK027", "Potion of Deja Vu (Blue)", "Normal", "Common", "https://no-image.tcgportfolio.internal/fab-hp1-no-photo-yet", 2.0),
)
