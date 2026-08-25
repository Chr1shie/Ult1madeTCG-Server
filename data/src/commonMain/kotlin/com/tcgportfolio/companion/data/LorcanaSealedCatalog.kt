package com.tcgportfolio.companion.data

// Disney Lorcana Booster Boxen + Starter Decks (Stand 2026-07-22), Preise
// von der Alt-Katalog-API. Starter Decks kommen pro Set als 2-3 Farbpaar-Decks (z.B.
// "Amber & Amethyst"), neuere Sets teils als "2-Player Starter Set"/
// "Collection Starter Set" statt Farbpaaren.
//
// Bilder (11.08.) - echte offizielle Packungsfotos von Ravensburgers eigener
// Disney-Lorcana-Seite (disneylorcana.com/en-US/product/{set}), per alt-Text-
// Suche ("...Booster..."/"...Starter...") pro Produktseite ermittelt - alle
// 13 Booster Boxen gefunden, bei den Starter Decks 19 von 25 (Farbpaar-Decks
// teilen sich pro Set ein gemeinsames Foto, wie bei den anderen TCGs). Für
// Wilds Unknown, Whispers in the Well, Reign of Jafar und die Rapunzel-
// Edition von Attack of the Vine hat die Seite keine eigenen Starter-Deck-Fotos - "no-image"-
// Sentinel für diese.
val lorcanaBoosterBoxesCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("BB-4500011", "Disney Lorcana: The First Chapter Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/s1-booster-wraps-carousel.png", 487.28),
    SealedCatalogSeed("BB-4500007", "Disney Lorcana: Azurite Sea Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s6/products/lorcana_set_6_booster_850x850_en.png", 113.36),
    SealedCatalogSeed("BB-4500016", "Disney Lorcana: Shimmering Skies Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s5/en_ranuk9gopc.png", 148.19),
    SealedCatalogSeed("BB-4500017", "Disney Lorcana: Rise of the Floodborn Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/product-s2/qfa7voktz5.png", 169.7),
    SealedCatalogSeed("BB-4500001", "Disney Lorcana: Into the Inklands Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/product-s3/en_brp1nnq4m1.png", 112.82),
    SealedCatalogSeed("BB-4500014", "Disney Lorcana: Wilds Unknown Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s12-wild-unknown/product-photos/svvuwbf6ph_en.png", 227.35),
    SealedCatalogSeed("BB-4500002", "Disney Lorcana: Winterspell Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s11-winterspell/en/y6nssuvv_en.png", 181.21),
    SealedCatalogSeed("BB-4500018", "Disney Lorcana: Attack of the Vine! Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s13-attack-of-the-vine/products/booster-display/en_b9letumqjp.png", 206.31),
    SealedCatalogSeed("BB-4500015", "Disney Lorcana: Whispers in the Well Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s10/dlc_s10_boosterdisplay-en.png", 178.71),
    SealedCatalogSeed("BB-4500008", "Disney Lorcana: Fabled Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/fabled/product/en/dlc_web_s09_assets_product-shots_1920x1080_booster-box.png", 1033.18),
    SealedCatalogSeed("BB-4500012", "Disney Lorcana: Archazia's Island Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/arch/lorcana_set_7_booster_850x850_en.png", 115.72),
    SealedCatalogSeed("BB-4500004", "Disney Lorcana: Ursula's Return Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s4/en_9xh3537ysdn.png", 125.54),
    SealedCatalogSeed("BB-4500013", "Disney Lorcana: Reign of Jafar Booster Box", "Booster Box", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/jafar/skus/en/en_ladx3fsdhw.png", 135.4),
)

val lorcanaStarterDecksCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("SD-4500011-AmberAmethyst", "Disney Lorcana: The First Chapter Starter Deck (Amber & Amethyst)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/s1-starter-decks-carousel.png", 21.38),
    SealedCatalogSeed("SD-4500011-EmeraldRuby", "Disney Lorcana: The First Chapter Starter Deck (Emerald & Ruby)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/s1-starter-decks-carousel.png", 16.8),
    SealedCatalogSeed("SD-4500011-SapphireSteel", "Disney Lorcana: The First Chapter Starter Deck (Sapphire & Steel)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/s1-starter-decks-carousel.png", 16.76),
    SealedCatalogSeed("SD-4500007-AmberRuby", "Disney Lorcana: Azurite Sea Starter Deck (Amber & Ruby)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s6/products/set_6_starter_deck_850x850_starter_deck_en.png", 10.78),
    SealedCatalogSeed("SD-4500007-EmeraldSapphire", "Disney Lorcana: Azurite Sea Starter Deck (Emerald & Sapphire)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s6/products/set_6_starter_deck_850x850_starter_deck_en.png", 8.64),
    SealedCatalogSeed("SD-4500016-AmethystRuby", "Disney Lorcana: Shimmering Skies Starter Deck (Amethyst & Ruby)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s5/en_ut5xxni3cv.png", 14.19),
    SealedCatalogSeed("SD-4500016-EmeraldSteel", "Disney Lorcana: Shimmering Skies Starter Deck (Emerald & Steel)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s5/en_ut5xxni3cv.png", 11.23),
    SealedCatalogSeed("SD-4500017-AmberSapphire", "Disney Lorcana: Rise of the Floodborn Starter Deck (Amber & Sapphire)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/product-s2/nqtza4jj7l.png", 5.83),
    SealedCatalogSeed("SD-4500017-AmethystSteel", "Disney Lorcana: Rise of the Floodborn Starter Deck (Amethyst & Steel)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/product-s2/nqtza4jj7l.png", 8.62),
    SealedCatalogSeed("SD-4500001-AmberEmerald", "Disney Lorcana: Into the Inklands Starter Deck (Amber & Emerald)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/product-s3/en_ibtfmlcktv.png", 6.51),
    SealedCatalogSeed("SD-4500001-RubySapphire", "Disney Lorcana: Into the Inklands Starter Deck (Ruby & Sapphire)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/product-s3/en_ibtfmlcktv.png", 5.72),
    SealedCatalogSeed("SD-4500014-WildsUnknown2PlayerStarterSet", "Disney Lorcana: Wilds Unknown 2-Player Starter Set", "Starter Deck", "Lorcana", "https://no-image.tcgportfolio.internal/lorcana-sealed-no-photo-yet", 22.21),
    SealedCatalogSeed("SD-4500002-WinterspellCollectionStarterSetStitchEdition", "Disney Lorcana: Winterspell Collection Starter Set - Stitch Edition", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/collection-starter-set/en/5qldbn4n_en.png", 33.16),
    SealedCatalogSeed("SD-4500018-AttackoftheVineCollectionStarterSetRapunzelEdition", "Disney Lorcana: Attack of the Vine! Collection Starter Set - Rapunzel Edition", "Starter Deck", "Lorcana", "https://no-image.tcgportfolio.internal/lorcana-sealed-no-photo-yet", 81.82),
    SealedCatalogSeed("SD-4500015-AmberEmerald", "Disney Lorcana: Whispers in the Well Starter Deck (Amber & Emerald)", "Starter Deck", "Lorcana", "https://no-image.tcgportfolio.internal/lorcana-sealed-no-photo-yet", 12.36),
    SealedCatalogSeed("SD-4500015-SapphireSteel", "Disney Lorcana: Whispers in the Well Starter Deck (Sapphire & Steel)", "Starter Deck", "Lorcana", "https://no-image.tcgportfolio.internal/lorcana-sealed-no-photo-yet", 11.83),
    SealedCatalogSeed("SD-4500008-FabledCollectionStarterSet", "Disney Lorcana: Fabled Collection Starter Set", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/fabled/product/en/dlc_web_s09_assets_product-shots_1920x1080_starters.png", 133.82),
    SealedCatalogSeed("SD-4500008-AmberSapphire", "Disney Lorcana: Fabled Starter Deck (Amber & Sapphire)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/fabled/product/en/dlc_web_s09_assets_product-shots_1920x1080_starters.png", 31.24),
    SealedCatalogSeed("SD-4500008-EmeraldRuby", "Disney Lorcana: Fabled Starter Deck (Emerald & Ruby)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/fabled/product/en/dlc_web_s09_assets_product-shots_1920x1080_starters.png", 39.52),
    SealedCatalogSeed("SD-4500012-AmethystSteel", "Disney Lorcana: Archazia's Island Starter Deck (Amethyst & Steel)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/arch/set_7_850x850_starter_deck_en.png", 9.04),
    SealedCatalogSeed("SD-4500012-RubySapphire", "Disney Lorcana: Archazia's Island Starter Deck (Ruby & Sapphire)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/arch/set_7_850x850_starter_deck_en.png", 9.84),
    SealedCatalogSeed("SD-4500004-AmberAmethyst", "Disney Lorcana: Ursula's Return Starter Deck (Amber & Amethyst)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s4/en_5ytmai9uxj.png", 8.08),
    SealedCatalogSeed("SD-4500004-SapphireSteel", "Disney Lorcana: Ursula's Return Starter Deck (Sapphire & Steel)", "Starter Deck", "Lorcana", "https://ravensburger.cloud/cms/gallery/lorcana-web/products/s4/en_5ytmai9uxj.png", 13.07),
    SealedCatalogSeed("SD-4500013-AmberAmethyst", "Disney Lorcana: Reign of Jafar Starter Deck (Amber & Amethyst)", "Starter Deck", "Lorcana", "https://no-image.tcgportfolio.internal/lorcana-sealed-no-photo-yet", 9.5),
    SealedCatalogSeed("SD-4500013-RubySteel", "Disney Lorcana: Reign of Jafar Starter Deck (Ruby & Steel)", "Starter Deck", "Lorcana", "https://no-image.tcgportfolio.internal/lorcana-sealed-no-photo-yet", 13.67),
)
