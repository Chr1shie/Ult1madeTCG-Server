package com.tcgportfolio.companion.data

// Sealed-Produkt-Bilder (11.08., Nutzer-Vorgabe "wir haben ja eine Funktion
// um selbst ein Foto hinzuzufügen, also würde ich sagen reicht ein
// Set-Logo") - anders als bei Einzelkarten gibt es für Sealed-Produkt-
// Fotografie keine freie "Datenbank" analog zu Scryfall/TCGdex/YGOPRODeck
// (das sind Karten-APIs, keine Produktfoto-Archive), die Alt-CDN bleibt
// dafür die einzige bekannte Bildquelle mit Vollabdeckung. Statt eines
// echten Kistenfotos deshalb das offizielle Set-Logo von TCGdex (dieselbe
// bereits genutzte Quelle wie beim Katalog) als Platzhalter - eindeutig
// zuordenbar, garantiert lizenzfrei nutzbar, kein Alt-CDN-Hotlink mehr.
// Bei Bedarf kann der Nutzer wie bei fehlenden Katalogbildern jederzeit ein
// eigenes Foto hochladen (siehe CustomCardPhotoEntity/setCustomCardPhoto()).
// 2 von 52 Sets (Dragon Majesty, Shining Legends) haben bei TCGdex weder Logo
// noch Symbol hinterlegt - fallen auf den etablierten "no-image"-Sentinel
// zurück (triggert automatisch den eigenen-Foto-Upload-Hinweis in der App).
val pokemonEliteTrainerBoxesCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("ETB001", "XY BREAKpoint Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/xy/xy9/logo.png", 766.93),
    SealedCatalogSeed("ETB002", "Burning Shadows Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm3/logo.png", 318.87),
    SealedCatalogSeed("ETB003", "Celebrations Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/cel25/logo.png", 437.23),
    SealedCatalogSeed("ETB004", "Celestial Storm Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm7/logo.png", 1961.95),
    SealedCatalogSeed("ETB005", "Champion's Path Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh3.5/logo.png", 211.31),
    SealedCatalogSeed("ETB006", "Cosmic Eclipse Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm12/logo.png", 1883.0),
    SealedCatalogSeed("ETB007", "Crimson Invasion Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm4/logo.png", 211.75),
    SealedCatalogSeed("ETB008", "Crown Zenith Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh12.5/logo.png", 335.76),
    SealedCatalogSeed("ETB009", "Dragon Majesty Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://no-image.tcgportfolio.internal/etb-no-photo-yet", 1053.37),
    SealedCatalogSeed("ETB010", "Fates Collide Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/xy/xy10/logo.png", 753.97),
    SealedCatalogSeed("ETB011", "Forbidden Light Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm6/logo.png", 638.69),
    SealedCatalogSeed("ETB012", "Generations Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/xy/g1/logo.png", 2741.7),
    SealedCatalogSeed("ETB013", "Guardians Rising Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm2/logo.png", 402.48),
    SealedCatalogSeed("ETB014", "Hidden Fates Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm115/logo.png", 552.91),
    SealedCatalogSeed("ETB015", "Lost Thunder Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm8/logo.png", 688.5),
    SealedCatalogSeed("ETB016", "Pokemon GO Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh10.5/logo.png", 161.51),
    SealedCatalogSeed("ETB017", "Shining Fates Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh4.5/logo.png", 149.67),
    SealedCatalogSeed("ETB018", "Shining Legends Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://no-image.tcgportfolio.internal/etb-no-photo-yet", 893.83),
    SealedCatalogSeed("ETB019", "Sun & Moon Elite Trainer Box [Lunala]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm1/logo.png", 250.03),
    SealedCatalogSeed("ETB020", "Steam Siege Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/xy/xy11/logo.png", 1800.0),
    SealedCatalogSeed("ETB021", "Scarlet & Violet Elite Trainer Box [Koraidon]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv01/logo.png", 137.51),
    SealedCatalogSeed("ETB022", "Paldea Evolved Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv02/logo.png", 247.25),
    SealedCatalogSeed("ETB023", "Obsidian Flames Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv03/logo.png", 321.55),
    SealedCatalogSeed("ETB024", "Paradox Rift Elite Trainer Box [Iron Valiant]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv04/logo.png", 138.27),
    SealedCatalogSeed("ETB025", "Temporal Forces Elite Trainer Box [Iron Leaves ex]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/univ/sv/sv05/symbol.png", 130.99),
    SealedCatalogSeed("ETB026", "Twilight Masquerade Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv06/logo.png", 112.27),
    SealedCatalogSeed("ETB027", "Stellar Crown Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv07/logo.png", 154.97),
    SealedCatalogSeed("ETB028", "Surging Sparks Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv08/logo.png", 128.47),
    SealedCatalogSeed("ETB029", "Journey Together Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv09/logo.png", 132.67),
    SealedCatalogSeed("ETB030", "Destined Rivals Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv10/logo.png", 158.06),
    SealedCatalogSeed("ETB031", "151 Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv03.5/logo.png", 617.1),
    SealedCatalogSeed("ETB032", "Black Bolt Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv10.5b/logo.png", 177.06),
    SealedCatalogSeed("ETB033", "Paldean Fates Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv04.5/logo.png", 480.11),
    SealedCatalogSeed("ETB034", "Prismatic Evolutions Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv08.5/logo.png", 170.87),
    SealedCatalogSeed("ETB035", "Shrouded Fable Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv06.5/logo.png", 127.53),
    SealedCatalogSeed("ETB036", "White Flare Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv10.5w/logo.png", 149.04),
    SealedCatalogSeed("ETB037", "Sword & Shield Elite Trainer Box [Zacian]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh1/logo.png", 152.97),
    SealedCatalogSeed("ETB038", "Rebel Clash Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh2/logo.png", 304.63),
    SealedCatalogSeed("ETB039", "Darkness Ablaze Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh3/logo.png", 133.42),
    SealedCatalogSeed("ETB040", "Sword & Shield Elite Trainer Box Plus [Zacian]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh1/logo.png", 252.74),
    SealedCatalogSeed("ETB041", "Battle Styles Elite Trainer Box [Rapid Strike Urshifu] (Blue)", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh5/logo.png", 127.18),
    SealedCatalogSeed("ETB042", "Chilling Reign Elite Trainer Box [Ice Rider Calyrex]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh6/logo.png", 141.73),
    SealedCatalogSeed("ETB043", "Evolving Skies Elite Trainer Box [Flareon/Jolteon/Umbreon/Leafeon]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh7/logo.png", 606.22),
    SealedCatalogSeed("ETB044", "Fusion Strike Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh8/logo.png", 359.6),
    SealedCatalogSeed("ETB045", "Brilliant Stars Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh9/logo.png", 171.96),
    SealedCatalogSeed("ETB046", "Astral Radiance Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh10/logo.png", 154.07),
    SealedCatalogSeed("ETB047", "Lost Origin Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh11/logo.png", 230.62),
    SealedCatalogSeed("ETB048", "Silver Tempest Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh12/logo.png", 164.43),
    SealedCatalogSeed("ETB049", "Team Up Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm9/logo.png", 4000.0),
    SealedCatalogSeed("ETB050", "Ultra Prism Elite Trainer Box [Dawn Wings Necrozma]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm5/logo.png", 799.99),
    SealedCatalogSeed("ETB051", "Unified Minds Elite Trainer Box", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm11/logo.png", 1820.0),
    SealedCatalogSeed("ETB052", "XY Evolutions Elite Trainer Box [Mega Blastoise]", "Elite Trainer Box", "Pokemon", "https://assets.tcgdex.net/en/xy/xy12/logo.png", 569.68),
)
