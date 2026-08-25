package com.tcgportfolio.companion.data

// Naruto Kayou (18.08., Nutzer-Vorgabe "lass uns das mit rein nehmen, aber
// nur die NA-Releases") - Kayous nordamerikanische Naruto-Sammelkarten
// (reine Sammelkarten, KEIN Spiel). Quelle: narutodb.com, ein
// handverifiziertes Fan-Projekt mit offener JSON-API (api.narutodb.com,
// siehe CONCEPT.md "Recherche: Kayou-Naruto-Sammelkarten"). Bilder von
// deren eigener CDN (cdn.narutodb.com) - bewusst die Thumb-Variante
// (~200 KB) statt der 1,5-MB-Vollbilder; wer volle Auflösung will:
// gleiche URL ohne "-thumb". Kartennummern sind narutodb-Codes
// ("NRSA01-SE-001L5") - was PHYSISCH auf den Karten gedruckt ist, wird
// wie immer erst am echten Exemplar verifiziert (Scan-Regex folgt dann).
// Keine Preise: Cardmarket listet Kayou nicht (wie Altered/Gundam).


val narutoKayouPromoSetSeed = CardSetSeed(id = "NRPROMO", name = "Kayou Promos", game = "NarutoKayou", totalCards = 14)

val narutoKayouPromoCatalogSeed: List<CatalogCardSeed> = listOf(
    CatalogCardSeed("NRSA-◇PR-011", "NRPROMO", "NRSA-◇PR-011", "Naruto Uzumaki", "Normal", "◇PR", "https://cdn.narutodb.com/storage/cards/NRCCNA/NRSA-◇PR-011_thumb.png?v=1786658610", null),
    CatalogCardSeed("NRSA-PR-011", "NRPROMO", "NRSA-PR-011", "Naruto Uzumaki", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRCCNA/NRSA-PR-011_thumb.png?v=1786658603", null),
    CatalogCardSeed("NRSA-◇PR-012", "NRPROMO", "NRSA-◇PR-012", "Sasuke Uchiha", "Normal", "◇PR", "https://cdn.narutodb.com/storage/cards/NRCCNA/NRSA-◇PR-012_thumb.png?v=1786658613", null),
    CatalogCardSeed("NRSA-PR-012", "NRPROMO", "NRSA-PR-012", "Sasuke Uchiha", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRCCNA/NRSA-PR-012_thumb.png?v=1786658607", null),
    CatalogCardSeed("NREA-PR-001", "NRPROMO", "NREA-PR-001", "Boruto Uzumaki", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NREA/NREA-PR-001_thumb.png?v=1779478783", null),
    CatalogCardSeed("NREA-PR-003", "NRPROMO", "NREA-PR-003", "Naruto Uzumaki", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NREA/NREA-PR-003_thumb.png?v=1779725309", null),
    CatalogCardSeed("NRSA-PR-001", "NRPROMO", "NRSA-PR-001", "Konan", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRSA01/NRSA-PR-001_thumb.png?v=1780299399", null),
    CatalogCardSeed("NRSA-PR-003", "NRPROMO", "NRSA-PR-003", "Naruto Uzumaki (Nine-Tails / Gamakichi)", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRSA/NRSA-PR-003_thumb.png?v=1779208089", null),
    CatalogCardSeed("NRSA02-PR-005", "NRPROMO", "NRSA02-PR-005", "Naruto Uzumaki", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRSA02/NRSA02-PR-005_thumb.png?v=1780299418", null),
    CatalogCardSeed("NRSA02-PR-006", "NRPROMO", "NRSA02-PR-006", "Kakashi Hatake", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRSA02/NRSA02-PR-006_thumb.png?v=1780299418", null),
    CatalogCardSeed("NRSA-PR-002", "NRPROMO", "NRSA-PR-002", "Naruto Uzumaki", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRSA/NRSA-PR-002_thumb.png?v=1779208085", null),
    CatalogCardSeed("NRSA-PR-004", "NRPROMO", "NRSA-PR-004", "Naruto Uzumaki", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRSA/NRSA-PR-004_thumb.png?v=1779208088", null),
    CatalogCardSeed("NRSA-PR-009", "NRPROMO", "NRSA-PR-009", "Naruto Uzumaki", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRSA03/NRSA-PR-009_thumb.png?v=1780299433", null),
    CatalogCardSeed("NRSA-PR-010", "NRPROMO", "NRSA-PR-010", "Sasuke Uchiha", "Normal", "PR", "https://cdn.narutodb.com/storage/cards/NRSA03/NRSA-PR-010_thumb.png?v=1780299433", null),
)
