package com.tcgportfolio.companion.data

// Alle 12 Fusion-World-Starter-Decks (Stand 2026-07-21). marketPriceUsd =
// Preis des Einzel-Decks selbst (nicht Display/Set-of-4-Bundle), per exaktem
// Namensabgleich aus dem jeweiligen Set-Katalog von der Alt-Katalog-API ermittelt.
// Bilder (11.08., Nutzer-Vorgabe "DBFW Sealed-Produkte checken") - echte
// offizielle Packungsfotos direkt von Bandais eigener Fusion-World-Seite
// (dbs-cardgame.com/fw/en/products/, "itemBoxImg"-Bereich jeder Produktseite),
// ermittelt über deren Sitemap statt geraten - kein Alt-CDN-Hotlink mehr.
// SDEX1 (FS12) hat auf der englischen Seite bisher nur ein japanisch
// beschriftetes Packungsfoto hinterlegt (kein separates EN-Bild vorhanden) -
// trotzdem das echte offizielle Produktfoto, kein Platzhalter.
val starterDecksCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("SD01", "Starter Deck 1: Son Goku", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2023/12/14/zEmytA4OoV5sIyks/FS01.png", 9.13),
    SealedCatalogSeed("SD02", "Starter Deck 2: Vegeta", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2023/12/14/j83eO9x0ahKCCYhP/FS02.png", 10.92),
    SealedCatalogSeed("SD03", "Starter Deck 3: Broly", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2023/12/14/x3JzLeKEq8lNDDP2/FS03.png", 17.78),
    SealedCatalogSeed("SD04", "Starter Deck 4: Frieza", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2023/12/14/2oS3XnflPA7yyLUP/FS04.png", 12.17),
    SealedCatalogSeed("SD05", "Starter Deck 5: Bardock", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2024/06/12/dyGmi4HzLcebc0h0/FS05.png", 10.31),
    SealedCatalogSeed("SD06", "Starter Deck 6: Son Goku (Mini)", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2024/08/01/FaXCXJPYZi48zgMf/FS06.png", 7.09),
    SealedCatalogSeed("SD07", "Starter Deck 7: Vegeta (Mini)", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2024/08/01/FdbjCAZDfUq2g9fA/FS07.png", 6.45),
    SealedCatalogSeed("SD08", "Starter Deck 8: Vegeta (Mini) Super Saiyan 3", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/02/05/O9dVggbjsfdOXlIq/FS08.png", 13.26),
    SealedCatalogSeed("SD09", "Starter Deck 9: Shallot", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/05/22/rlbvW3C2QedcdQwl/FS09.png", 83.56),
    SealedCatalogSeed("SD10", "Starter Deck 10: Giblet", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/05/22/zb1kHRxdFg4xvgzG/FS10_en.png", 68.6),
    SealedCatalogSeed("SDEX1", "Starter Deck EX: The Beat of Ki", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2026/01/07/8QtfKLrXGRlNkKvE/FS12_jp.png", 24.64),
    SealedCatalogSeed("SDEX2", "Starter Deck EX: The Phase of Evolution", "Starter Deck", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2026/01/07/zbWLU6s5chJjCGWi/FS11.png", 33.87)
)
