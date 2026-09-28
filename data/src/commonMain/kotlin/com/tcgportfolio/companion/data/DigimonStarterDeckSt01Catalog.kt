package com.tcgportfolio.companion.data

// ST1: Gaia Red - offizielle Kartenliste world.digimoncard.com (Stand 28.09.2026),
// Format wie TimelessBondsCatalog.kt (_P1 -> -AA "Alternate Art", _P2 -> -AA2 ...).
// Generiert von tools/catalog/generate_digimon_new_sets.py.
// Offizielle Produktliste: 16 Einträge; hier 16.
// Starterdeck als eigenes Set: Set-Id "DGST1" (ohne Bindestrich), alle Karten-Ids mit
// "DGST1-" vorangestellt (die gedruckte Nummer bleibt in number, z.B. "ST1-01").
val digimonStarterDeckSt01SetSeed = CardSetSeed(id = "DGST1", name = "ST1: Gaia Red", game = "Digimon", totalCards = 16)

val digimonStarterDeckSt01CatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("DGST1-ST1-01", "DGST1", "ST1-01", "Koromon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST1-01.png", null),
    CatalogCardSeed("DGST1-ST1-02", "DGST1", "ST1-02", "Biyomon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST1-02.png", null),
    CatalogCardSeed("DGST1-ST1-03", "DGST1", "ST1-03", "Agumon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST1-03.png", null),
    CatalogCardSeed("DGST1-ST1-04", "DGST1", "ST1-04", "Dracomon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST1-04.png", null),
    CatalogCardSeed("DGST1-ST1-05", "DGST1", "ST1-05", "Birdramon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST1-05.png", null),
    CatalogCardSeed("DGST1-ST1-06", "DGST1", "ST1-06", "Coredramon", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST1-06.png", null),
    CatalogCardSeed("DGST1-ST1-07", "DGST1", "ST1-07", "Greymon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST1-07.png", null),
    CatalogCardSeed("DGST1-ST1-08", "DGST1", "ST1-08", "Garudamon", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST1-08.png", null),
    CatalogCardSeed("DGST1-ST1-09", "DGST1", "ST1-09", "MetalGreymon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST1-09.png", null),
    CatalogCardSeed("DGST1-ST1-10", "DGST1", "ST1-10", "Phoenixmon", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST1-10.png", null),
    CatalogCardSeed("DGST1-ST1-11", "DGST1", "ST1-11", "WarGreymon", "Normal", "SR", "https://world.digimoncard.com/images/cardlist/card/ST1-11.png", null),
    CatalogCardSeed("DGST1-ST1-12", "DGST1", "ST1-12", "Tai Kamiya", "Normal", "R", "https://world.digimoncard.com/images/cardlist/card/ST1-12.png", null),
    CatalogCardSeed("DGST1-ST1-13", "DGST1", "ST1-13", "Shadow Wing", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST1-13.png", null),
    CatalogCardSeed("DGST1-ST1-14", "DGST1", "ST1-14", "Starlight Explosion", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST1-14.png", null),
    CatalogCardSeed("DGST1-ST1-15", "DGST1", "ST1-15", "Giga Destroyer", "Normal", "C", "https://world.digimoncard.com/images/cardlist/card/ST1-15.png", null),
    CatalogCardSeed("DGST1-ST1-16", "DGST1", "ST1-16", "Gaia Force", "Normal", "U", "https://world.digimoncard.com/images/cardlist/card/ST1-16.png", null),
)
