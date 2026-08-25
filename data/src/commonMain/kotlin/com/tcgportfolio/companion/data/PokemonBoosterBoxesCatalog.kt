package com.tcgportfolio.companion.data

// Booster Boxen aller aktuell importierten Pokémon-Hauptsets, soweit
// die Alt-Katalog-API ein reguläres "Booster Box"-Produkt dafür hat (Stand 2026-07-21).
// Manche Sets (z.B. Celebrations, Hidden/Shining/Paldean Fates, 151,
// Prismatic Evolutions) haben real kein reguläres Booster-Box-Produkt -
// fehlen deshalb hier bewusst, kein Datenfehler.
// Bilder (11.08.) - wie schon bei PokemonEliteTrainerBoxesCatalog.kt: TCGdex-
// Set-Logo statt echtem Kistenfoto (für Sealed-Produkte gibt es keine freie
// Datenbank analog zu Scryfall/TCGdex bei Einzelkarten) - direkt aus der
// bereits im eigenen Kartenkatalog verwendeten TCGdex-Set-Zuordnung
// übernommen (setId "ASR" -> tcgdex "swsh/swsh10" usw., siehe
// AstralRadianceCatalog.kt & Geschwister), kein erneutes Namens-Matching
// nötig. TEF (Temporal Forces) hat bei TCGdex kein Logo hinterlegt, dafür
// aber ein Set-Symbol - das wird hier ersatzweise verwendet.
val pokemonBoosterBoxesCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("BB-ASR", "Astral Radiance Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh10/logo.png", 415.84),
    SealedCatalogSeed("BB-BST", "Battle Styles Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh5/logo.png", 281.05),
    SealedCatalogSeed("BB-BKP", "XY BREAKpoint Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/xy/xy9/logo.png", null),
    SealedCatalogSeed("BB-BRS", "Brilliant Stars Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh9/logo.png", 630.35),
    SealedCatalogSeed("BB-BUS", "Burning Shadows Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm3/logo.png", 1220.0),
    SealedCatalogSeed("BB-CIN", "Crimson Invasion Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm4/logo.png", 614.0),
    SealedCatalogSeed("BB-CES", "Celestial Storm Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm7/logo.png", 2432.66),
    SealedCatalogSeed("BB-ME04", "Chaos Rising Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/me/me04/logo.png", 192.17),
    SealedCatalogSeed("BB-CRE", "Chilling Reign Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh6/logo.png", 499.91),
    SealedCatalogSeed("BB-DAA", "Darkness Ablaze Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh3/logo.png", 366.86),
    SealedCatalogSeed("BB-CEC", "Cosmic Eclipse Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm12/logo.png", 3935.5),
    SealedCatalogSeed("BB-DRI", "Destined Rivals Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv10/logo.png", 546.24),
    SealedCatalogSeed("BB-FCO", "XY Fates Collide Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/xy/xy10/logo.png", 2754.99),
    SealedCatalogSeed("BB-EVS", "Evolving Skies Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh7/logo.png", 2601.09),
    SealedCatalogSeed("BB-FST", "Fusion Strike Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh8/logo.png", 1046.59),
    SealedCatalogSeed("BB-FLI", "Forbidden Light Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm6/logo.png", 1647.98),
    SealedCatalogSeed("BB-JTG", "Journey Together Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv09/logo.png", 299.22),
    SealedCatalogSeed("BB-GRI", "Guardians Rising Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm2/logo.png", 1737.25),
    SealedCatalogSeed("BB-ME01", "Mega Evolution Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/me/me01/logo.png", 328.4),
    SealedCatalogSeed("BB-LOT", "Lost Thunder Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm8/logo.png", 1505.55),
    SealedCatalogSeed("BB-LOR", "Lost Origin Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh11/logo.png", 748.99),
    SealedCatalogSeed("BB-OBF", "Obsidian Flames Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv03/logo.png", 392.1),
    SealedCatalogSeed("BB-PAR", "Paradox Rift Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv04/logo.png", 291.5),
    SealedCatalogSeed("BB-ME02", "Phantasmal Flames Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/me/me02/logo.png", 436.15),
    SealedCatalogSeed("BB-PAL", "Paldea Evolved Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv02/logo.png", 499.47),
    SealedCatalogSeed("BB-RCL", "Rebel Clash Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh2/logo.png", 495.76),
    SealedCatalogSeed("BB-SUM", "Sun & Moon Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm1/logo.png", 942.35),
    SealedCatalogSeed("BB-SIT", "Silver Tempest Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh12/logo.png", 540.97),
    SealedCatalogSeed("BB-SCR", "Stellar Crown Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv07/logo.png", 353.17),
    SealedCatalogSeed("BB-STS", "Steam Siege Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/xy/xy11/logo.png", 1177.49),
    SealedCatalogSeed("BB-SVI", "Scarlet & Violet Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv01/logo.png", 314.32),
    SealedCatalogSeed("BB-SSP", "Surging Sparks Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv08/logo.png", 293.76),
    SealedCatalogSeed("BB-TEF", "Temporal Forces Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/univ/sv/sv05/symbol.png", 319.1),
    SealedCatalogSeed("BB-TEU", "Team Up Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm9/logo.png", 11333.47),
    SealedCatalogSeed("BB-SSH", "Sword & Shield Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh1/logo.png", 680.46),
    SealedCatalogSeed("BB-UNB", "Unbroken Bonds Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm10/logo.png", 2509.28),
    SealedCatalogSeed("BB-UNM", "Unified Minds Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm11/logo.png", 2923.73),
    SealedCatalogSeed("BB-VIV", "Vivid Voltage Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/swsh/swsh4/logo.png", 344.16),
    SealedCatalogSeed("BB-TWM", "Twilight Masqueade Half Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sv/sv06/logo.png", 194.86),
    SealedCatalogSeed("BB-UPR", "Ultra Prism Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/sm/sm5/logo.png", 1506.25),
    SealedCatalogSeed("BB-EVO", "XY Evolutions Booster Box", "Booster Box", "Pokemon", "https://assets.tcgdex.net/en/xy/xy12/logo.png", 2501.13),
)
