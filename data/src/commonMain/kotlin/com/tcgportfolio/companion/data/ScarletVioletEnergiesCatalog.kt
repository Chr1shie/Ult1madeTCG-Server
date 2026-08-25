package com.tcgportfolio.companion.data

// Scarlet & Violet Energies (04.08., Nutzer-Fund "beim Deck erkennt er bei
// Pokemon keine Energy Karten") - eigenes, von den nummerierten Hauptsets
// (SVI/OBF/PAL/...) getrenntes Produkt: Pokémon druckt Basic Energy in der
// aktuellen Ära nicht als Teil des jeweiligen Haupt-Sets, sondern als
// eigene, wiederkehrende Beilage zu Starter-/Theme-Decks - genau DAS fehlte
// bisher komplett im Katalog (nicht ein Scanner-Bug, siehe CONCEPT.md).
// Daten von api.pokemontcg.io/v2/cards (set.id "sve", kostenlos, ohne
// Schlüssel, siehe PokemonCardRulesSync.kt für dieselbe Quelle) - zwei
// Druckläufe (1-8 und 9-16) mit denselben acht Typen, beide real im Umlauf.
val scarletVioletEnergiesSetSeed = CardSetSeed(id = "SVE", name = "SV: Scarlet & Violet Energies", game = "Pokemon", totalCards = 16)

val scarletVioletEnergiesCatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("SVE-1/16", "SVE", "1/16", "Basic Grass Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/1_hires.png", null),
    CatalogCardSeed("SVE-2/16", "SVE", "2/16", "Basic Fire Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/2_hires.png", null),
    CatalogCardSeed("SVE-3/16", "SVE", "3/16", "Basic Water Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/3_hires.png", null),
    CatalogCardSeed("SVE-4/16", "SVE", "4/16", "Basic Lightning Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/4_hires.png", null),
    CatalogCardSeed("SVE-5/16", "SVE", "5/16", "Basic Psychic Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/5_hires.png", null),
    CatalogCardSeed("SVE-6/16", "SVE", "6/16", "Basic Fighting Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/6_hires.png", null),
    CatalogCardSeed("SVE-7/16", "SVE", "7/16", "Basic Darkness Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/7_hires.png", null),
    CatalogCardSeed("SVE-8/16", "SVE", "8/16", "Basic Metal Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/8_hires.png", null),
    CatalogCardSeed("SVE-9/16", "SVE", "9/16", "Basic Grass Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/9_hires.png", null),
    CatalogCardSeed("SVE-10/16", "SVE", "10/16", "Basic Fire Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/10_hires.png", null),
    CatalogCardSeed("SVE-11/16", "SVE", "11/16", "Basic Water Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/11_hires.png", null),
    CatalogCardSeed("SVE-12/16", "SVE", "12/16", "Basic Lightning Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/12_hires.png", null),
    CatalogCardSeed("SVE-13/16", "SVE", "13/16", "Basic Psychic Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/13_hires.png", null),
    CatalogCardSeed("SVE-14/16", "SVE", "14/16", "Basic Fighting Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/14_hires.png", null),
    CatalogCardSeed("SVE-15/16", "SVE", "15/16", "Basic Darkness Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/15_hires.png", null),
    CatalogCardSeed("SVE-16/16", "SVE", "16/16", "Basic Metal Energy", "Normal", "Common", "https://images.pokemontcg.io/sve/16_hires.png", null)
)
