package com.tcgportfolio.companion.data

// Booster Boxen (24-Pack-Displays) aller DBFW-Hauptsets (Stand 2026-07-21),
// Preise von der Alt-Katalog-API. Die Alt-Katalog-API nennt sie "Booster Box" (Alt-Quellen-
// Terminologie); dasselbe Produkt, das man hierzulande als "Display" kennt.
// Bilder (11.08., Nutzer-Vorgabe "DBFW Sealed-Produkte checken") - echte
// offizielle Packungsfotos direkt von Bandais eigener Fusion-World-Seite
// (dbs-cardgame.com/fw/en/products/, "itemBoxImg"-Bereich jeder Produktseite),
// ermittelt über deren Sitemap statt geraten - kein Alt-CDN-Hotlink mehr.
val boosterBoxesCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("BB-FB01", "Awakened Pulse Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2023/12/14/x8m1DyEefYT7nV2T/FB01.png", 355.19),
    SealedCatalogSeed("BB-FB02", "Blazing Aura Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2024/03/07/vBnzyMddBNnLcft5/FB02.png", 157.37),
    SealedCatalogSeed("BB-FB03", "Raging Roar Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2024/06/11/sh3qmtllBHUe1FYa/FB03_en.png", 160.97),
    SealedCatalogSeed("BB-FB04", "Ultra Limit Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2024/08/28/wz1C1te5TTC8xPBs/FB04.png", 132.95),
    SealedCatalogSeed("BB-FB05", "New Adventure Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2024/11/28/bznoOD2ho8X4M8rb/FB05.png", 196.0),
    SealedCatalogSeed("BB-FB06", "Rivals Clash Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/02/03/kUojrv7IQ7GcUj53/FB06.png", 499.81),
    SealedCatalogSeed("BB-FB07", "Wish For Shenron Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/06/06/M3LgrWwQ7EgG87eg/FB07.png", 265.24),
    SealedCatalogSeed("BB-FB08", "Saiyan's Pride Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/09/17/8lb43IDYb8Hxk7e0/FB08.png", 224.94),
    SealedCatalogSeed("BB-FB09", "Dual Evolution Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/12/17/0zjxD5yLyDvHgl42/FB09.webp", 484.65),
    SealedCatalogSeed("BB-FB10", "Cross Force Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2026/03/06/JS4ULo0ymDGIxniT/FB10_en.png", 244.18),
    SealedCatalogSeed("BB-MB01", "Manga Booster 01 Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/05/02/NrK1DpHk1J4cl1aW/SB01.png", 1648.68),
    SealedCatalogSeed("BB-MB02", "Manga Booster 02 Booster Box", "Booster Box", "DBFW", "https://www.dbs-cardgame.com/fw/bccard/en/news/2025/07/15/juWqXLzujCKVtKRM/SB02.png", 899.17),
)
