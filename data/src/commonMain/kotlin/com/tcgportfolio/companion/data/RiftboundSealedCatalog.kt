package com.tcgportfolio.companion.data

// Riftbound Booster Boxen ("Booster Display" bei den Alt-Quellen),
// Champion Decks (inkl. der Vendetta-"Showdown Decks: Zed vs Shen") und der
// eine eigenständige Box-Set-Sonderfall (Origins: Proving Grounds hat keine
// Booster, nur dieses eine Boxprodukt) - aus bereits gecachten Rohdaten, kein
// zusätzlicher API-Aufruf nötig. Displays der Champion Decks (die Box mit
// mehreren Decks drin) und "Case"-Großgebinde bewusst ausgelassen, analog zu
// den anderen TCGs.
//
// Bilder (11.08.) - bewusst auf den "no-image"-Sentinel zurückgestellt statt
// die Alt-CDN. Riots offizieller Merch-Store (merch.riotgames.com) ist
// zwar erreichbar (kein Bot-Block), aber eine schwer greifbare Next.js-Seite
// mit über 120 generischen Galeriebildern pro Produktseite ohne sauber
// extrahierbares Haupt-/og:image - würde echtes JS-Rendering brauchen statt
// eines einfachen Abrufs. Eigene Runde später nötig.
val riftboundSealedCatalogSeed: List<SealedCatalogSeed> = listOf(
    SealedCatalogSeed("BB-RB500001", "Origins Booster Box", "Booster Box", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 264.43),
    SealedCatalogSeed("BB-RB500002", "Spiritforged Booster Box", "Booster Box", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 177.9),
    SealedCatalogSeed("BB-RB500006", "Unleashed Booster Box", "Booster Box", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 130.31),
    SealedCatalogSeed("BB-RB500008", "Vendetta Booster Box", "Booster Box", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 135.09),
    SealedCatalogSeed("CD-RB500001-jinx", "Origins Champion Deck (Jinx)", "Champion Deck", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 22.33),
    SealedCatalogSeed("CD-RB500001-leesin", "Origins Champion Deck (Lee Sin)", "Champion Deck", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 43.5),
    SealedCatalogSeed("CD-RB500001-viktor", "Origins Champion Deck (Viktor)", "Champion Deck", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 58.95),
    SealedCatalogSeed("CD-RB500002-fiora", "Spiritforged Champion Deck (Fiora)", "Champion Deck", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 34.35),
    SealedCatalogSeed("CD-RB500002-rumble", "Spiritforged Champion Deck (Rumble)", "Champion Deck", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 23.29),
    SealedCatalogSeed("CD-RB500008-zedshen", "Vendetta Showdown Decks: Zed vs Shen", "Champion Deck", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 21.99),
    SealedCatalogSeed("CD-RB500006-vex", "Unleashed Champion Deck (Vex)", "Champion Deck", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 22.64),
    SealedCatalogSeed("CD-RB500006-vi", "Unleashed Champion Deck (Vi)", "Champion Deck", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 12.12),
    SealedCatalogSeed("BX-RB500004", "Origins: Proving Grounds Box Set", "Box Set", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 64.01),
    SealedCatalogSeed("PK-RB500002", "Spiritforged Pre-Rift Event Kit", "Pre-Rift Kit", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 275.37),
    SealedCatalogSeed("PK-RB500002b", "Spiritforged Pre-Rift Kit", "Pre-Rift Kit", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 51.01),
    SealedCatalogSeed("PK-RB500006", "Unleashed Pre-Rift Event Kit", "Pre-Rift Kit", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 482.68),
    SealedCatalogSeed("PK-RB500006b", "Unleashed Pre-Rift Kit", "Pre-Rift Kit", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 26.83),
    SealedCatalogSeed("PK-RB500008", "Vendetta Pre-Rift Kit", "Pre-Rift Kit", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 59.84),
    SealedCatalogSeed("BD-RB500005", "Riftbound Worlds Bundle 2025", "Bundle", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 1173.15),
    SealedCatalogSeed("BD-RB500006", "Unleashed Sleeved Booster Pack Art Bundle [Set of 3]", "Bundle", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 25.28),
    SealedCatalogSeed("BD-RB500006b", "Unleashed Vault Bundle", "Bundle", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 42.42),
    SealedCatalogSeed("BD-RB500008", "Vendetta Vault Bundle", "Bundle", "Riftbound", "https://no-image.tcgportfolio.internal/riftbound-sealed-no-photo-yet", 47.93),
)
