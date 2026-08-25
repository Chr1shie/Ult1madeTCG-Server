package com.tcgportfolio.companion.data

// Star Wars: Unlimited Sealed-Produkte (13.08., Nutzer-Vorgabe "gleich
// sealed produkte") - echte Produktnamen von Cardmarkets eigener,
// oeffentlicher Produktliste (game-id 21, ueber die Kategorienamen
// "Star Wars Unlimited Display/Starter Decks/Prerelease Boxes"
// identifiziert - siehe CARDMARKET_GAME_IDS in CardmarketPriceSync.kt).
// Cardmarket liefert zwar Namen + Preise, aber KEINE Produktbilder (siehe
// CardmarketProductEntry-DTO) - Bilder bleiben auf dem "no-image"-
// Sentinel wie bei den anderen TCGs ohne offizielle Bildquelle (YuGiOh/
// MTG/FAB/Riftbound). Kein Preis hier hart hinterlegt (bewusst null) -
// der Name matcht 1:1 gegen Cardmarkets eigenen Produktnamen, der
// normale automatische Preisabgleich (applyCardmarketSealedPrices())
// zieht den echten EUR-Preis beim ersten Abgleich direkt.
val starWarsUnlimitedSealedCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("SWU-758269", "Spark of Rebellion Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-760225", "Spark of Rebellion Case (6x Booster Box)", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-772215", "Shadows of the Galaxy Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-772216", "Shadows of the Galaxy Case (6x Booster Box)", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-786841", "Twilight of the Republic Case (6x Booster Box)", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-786842", "Twilight of the Republic Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-804793", "Jump to Lightspeed Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-804794", "Jump to Lightspeed Case (6x Booster Box)", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-804795", "Jump to Lightspeed Carbonite Edition Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-820225", "Legends of the Force Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-820226", "Legends of the Force Case (6x Booster Box)", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-820227", "Legends of the Force Carbonite Edition Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-852026", "Secrets of Power Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-852030", "Secrets of Power Carbonite Edition Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-852135", "Secrets of Power Case (6x Booster Box)", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-867884", "A Lawless Time Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-867891", "A Lawless Time Case (6x Booster Box)", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-867899", "A Lawless Time Carbonite Edition Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-888614", "Ashes of the Empire Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-888615", "Ashes of the Empire Case (6x Booster Box)", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-888616", "Ashes of the Empire Carbonite Edition Booster Box", "Booster Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-758266", "Spark of Rebellion Two Player Starter Deck", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-772212", "Shadows of the Galaxy Two Player Starter Deck", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-786837", "Twilight of the Republic Two Player Starter", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-807084", "Jump to Lightspeed: Spotlight Deck: Han Solo", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-807087", "Jump to Lightspeed: Spotlight Deck: Boba Fett", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-820229", "Legends of the Force: Spotlight Deck: Qui-Gon Jinn", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-820230", "Legends of the Force: Spotlight Deck: Darth Maul", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-843582", "Intro Battle: Hoth", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-852031", "Secrets of Power: Spotlight Deck: Padmé Amidala", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-852040", "Secrets of Power: Spotlight Deck: Chancellor Palpatine", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-867909", "A Lawless Time: Spotlight Deck: Leia Organa", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-867910", "A Lawless Time: Spotlight Deck: Jabba the Hutt", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-880072", "Aggressive Negotiations: Anakin Skywalker and Padme Amidala Twin Suns Deck", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-880073", "Master and Apprentice: Count Dooku and Asajj Ventress Twin Suns Deck", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-880074", "Against the Odds: Ahsoka Tano and Captain Rex Twin Suns Deck", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-880075", "Blood Brothers: Maul and Savage Opress Twin Suns Deck", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-880095", "Star Wars: Unlimited 2026 Twin Suns Deck Display", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-888617", "Ashes of the Empire: Spotlight Deck: Luke Skywalker", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-888618", "Ashes of the Empire: Spotlight Deck: Emperor Palpatine", "Starter Deck", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-758267", "Spark of Rebellion Prerelease Box", "Prerelease Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-772213", "Shadows of the Galaxy Prerelease Box", "Prerelease Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-786839", "Twilight of the Republic Prerelease Box", "Prerelease Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-804796", "Jump to Lightspeed Prerelease Box", "Prerelease Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-820228", "Legends of the Force Prerelease Box", "Prerelease Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-852096", "Secrets of Power Prerelease Box", "Prerelease Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-867911", "A Lawless Time Prerelease Box", "Prerelease Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
    SealedCatalogSeed("SWU-888619", "Ashes of the Empire Prerelease Box", "Prerelease Box", "StarWarsUnlimited", "https://no-image.tcgportfolio.internal/swu-sealed-no-photo-yet", null),
)
