package com.tcgportfolio.companion.data

// Pokémon Battle Decks (Stand 2026-07-22) - seltenes Produkt, nur bei drei
// Sets je aufgelegt (Guardians Rising, Steam Siege, Fusion Strike) plus die
// V-Battle-Deck-Linie (Pokémon GO, Blastoise/Venusaur V). "Deluxe Battle Deck"
// existiert unter diesem Namen nicht bei der Alt-Katalog-API - komplett geprüft.
// Bilder (11.08.) - anders als die Booster Boxen/ETBs sind Battle Decks
// KEINEM Set zugeordnet, ein TCGdex-Set-Logo lässt sich hier also nicht
// wiederverwenden. pokemon.com liefert seine Produktseiten nur clientseitig
// per JavaScript aus (leeres HTML-Grundgerüst, kein Foto per einfachem
// Abruf erreichbar) - anders als bei Bandai (DBFW/OnePiece/Digimon) gibt es
// hier keine statisch abrufbare offizielle Quelle. Bewusst auf den "no-
// image"-Sentinel zurückgefallen statt eine Fan-Wiki (z.B. Bulbapedia) als
// Bildquelle zu nehmen - dieselbe Konsequenz wie bei den beiden Pokemon-ETB-
// Sets ohne TCGdex-Logo (Dragon Majesty/Shining Legends): der Nutzer kann
// jederzeit ein eigenes Foto hochladen (siehe CustomCardPhotoEntity).
val pokemonBattleDecksCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("BD01", "League Battle Deck [Mew VMAX]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", 55.12),
    SealedCatalogSeed("BD02", "Legendary Battle Decks [Ho-Oh]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", 38.42),
    SealedCatalogSeed("BD03", "Legendary Battle Decks [Lugia]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", 46.19),
    SealedCatalogSeed("BD04", "V Battle Deck [Melmetal V]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", 11.6),
    SealedCatalogSeed("BD05", "V Battle Deck [Mewtwo V]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", 23.17),
    SealedCatalogSeed("BD06", "Legendary Battle Decks [Articuno]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", null),
    SealedCatalogSeed("BD07", "Legendary Battle Decks [Moltres]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", 94.95),
    SealedCatalogSeed("BD08", "Legendary Battle Decks [Zapdos]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", 70.99),
    SealedCatalogSeed("BD09", "Blastoise V/Venusaur V Battle Decks [Set of 2]", "Battle Deck", "Pokemon", "https://no-image.tcgportfolio.internal/bd-no-photo-yet", 57.82),
)
