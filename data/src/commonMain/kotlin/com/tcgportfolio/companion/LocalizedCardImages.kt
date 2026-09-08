package com.tcgportfolio.companion

import com.tcgportfolio.companion.data.alteredGermanImageChunks
import com.tcgportfolio.companion.data.mtgGermanImageChunks

// Sprachbewusste Kartenbilder (1.1-Paket, 07.09., Nutzer-Design vom 31.08.,
// siehe CONCEPT.md "GEPLANT für 1.1"): der Scanner erkennt, ob eine deutsche
// oder englische Karte gescannt wurde - je nachdem soll auch das passende
// Kartenbild erscheinen. Globale Einstellung "cardImageLanguageMode":
// "asScanned" (Standard, 1:1 wie gescannt) | "de" | "en"; dazu ein
// Pro-Karte-Override (PortfolioItemEntity.imageLanguage), alles gesynct.
//
// Diese Datei ist die EINE Auflösungsstelle für App UND Server (beide
// hängen am :data-Modul), damit Weboberfläche und App nie auseinanderlaufen.
// Der Katalog selbst bleibt englisch - deutsche Bilder entstehen erst bei
// der Anzeige aus der englischen URL:
// - Pokémon (TCGdex): identische URL mit /de/ statt /en/ (>99 % Abdeckung,
//   verifiziert 31.08.). CDN-Eigenheit: fehlende Bilder hängen statt 404 -
//   die Anzeige braucht deshalb einen Rückfall mit Zeitlimit (siehe
//   rememberImageUrlWithFallback in LocalizedCardImage.kt / img.onerror im Web).
// - Final Fantasy TCG: offizieller Square-Enix-CDN mit Sprach-Suffix,
//   {kartennummer}_de.jpg (verifiziert 07.09. für 1-001H, 7-034L, 29-001R,
//   PR-001; Nachdrucke "Re-…" liefern 403 -> Rückfall auf Englisch).
// - Magic (Scryfall): deutsche Drucke sind eigene Kartenobjekte mit eigener
//   Bild-Id -> einmaliges Mapping aus tools/fetch_mtg_german_images.py
//   (MtgGermanImages.kt), Format "<cardId> <uuid> <ts>".
// - Altered (GitHub-Spiegel): Locale-Ordner mit gehashten Dateinamen ->
//   Mapping aus tools/fetch_altered_german_images.py (AlteredGermanImages.kt).
//   Einschränkung: nur Ordner mit genau EINER deutschen Datei sind eindeutig
//   zuordenbar (Rarity-Varianten teilen sich einen Ordner) - der Rest bleibt
//   englisch, siehe Skript-Kommentar.
// Lorcana/Yu-Gi-Oh/Flesh and Blood/Rest: keine deutsche Quelle, unverändert.

const val CARD_IMAGE_LANGUAGE_MODE_AS_SCANNED = "asScanned"
const val CARD_IMAGE_LANGUAGE_DE = "de"
const val CARD_IMAGE_LANGUAGE_EN = "en"
const val SETTING_CARD_IMAGE_LANGUAGE_MODE = "cardImageLanguageMode"
const val SETTING_CARD_IMAGE_LANGUAGE_MODE_UPDATED_AT = "cardImageLanguageModeUpdatedAt"

object LocalizedCardImages {
    private const val TCGDEX_EN_PREFIX = "https://assets.tcgdex.net/en/"
    private const val TCGDEX_DE_PREFIX = "https://assets.tcgdex.net/de/"
    private const val FFTCG_CDN_PREFIX = "https://fftcg.cdn.sewest.net/images/cards/full/"
    private const val KUPODB_PREFIX = "https://images.kupodb.com/"
    private const val SCRYFALL_PREFIX = "https://cards.scryfall.io/"
    private const val ALTERED_PREFIX = "https://raw.githubusercontent.com/AlteredEquinox/cards-nonunique/"

    // Erst beim ersten Zugriff geparst (die Chunks sind reine String-
    // Literale, siehe Generator-Skripte) - kostet einmalig wenige ms
    private val mtgDe: Map<String, String> by lazy { parseChunks(mtgGermanImageChunks) }
    private val alteredDe: Map<String, String> by lazy { parseChunks(alteredGermanImageChunks) }

    private fun parseChunks(chunks: List<String>): Map<String, String> {
        val map = HashMap<String, String>()
        for (chunk in chunks) {
            for (line in chunk.lineSequence()) {
                val sp = line.indexOf(' ')
                if (sp <= 0) continue
                map[line.substring(0, sp)] = line.substring(sp + 1)
            }
        }
        return map
    }

    // Deutsche Bild-URL zu einer Katalogkarte, null = keine bekannt
    fun germanImageUrl(cardId: String?, imageUrl: String?): String? {
        if (imageUrl == null) return null
        return when {
            imageUrl.startsWith(TCGDEX_EN_PREFIX) ->
                TCGDEX_DE_PREFIX + imageUrl.removePrefix(TCGDEX_EN_PREFIX)
            imageUrl.startsWith(FFTCG_CDN_PREFIX) || imageUrl.startsWith(KUPODB_PREFIX) -> {
                // Katalog-Id "6200035-29-001R" -> Kartennummer "29-001R"
                // (KupoDB-Set-Ids sind rein numerisch, ohne Bindestrich)
                val number = cardId?.substringAfter('-', "")?.takeIf { it.isNotEmpty() } ?: return null
                "$FFTCG_CDN_PREFIX${number}_de.jpg"
            }
            imageUrl.startsWith(SCRYFALL_PREFIX) -> {
                val entry = cardId?.let { mtgDe[it] } ?: return null
                val sp = entry.indexOf(' ')
                val uuid = if (sp > 0) entry.substring(0, sp) else entry
                val ts = if (sp > 0) entry.substring(sp + 1) else ""
                if (uuid.length < 2) return null
                "https://cards.scryfall.io/large/front/${uuid[0]}/${uuid[1]}/$uuid.jpg" + (if (ts.isNotEmpty()) "?$ts" else "")
            }
            imageUrl.startsWith(ALTERED_PREFIX) -> {
                val hash = cardId?.let { alteredDe[it] } ?: return null
                val folder = imageUrl.substringBefore("/en_US/", "")
                if (folder.isEmpty()) return null
                "$folder/de_DE/$hash.jpg"
            }
            else -> null
        }
    }

    fun hasGermanImage(cardId: String?, imageUrl: String?): Boolean = germanImageUrl(cardId, imageUrl) != null

    // Gewünschte Sprache: Pro-Karte-Override > globaler Modus (asScanned ->
    // Scan-Sprache der Karte, sonst Katalog-Standard Englisch)
    fun wantedLanguage(scanLanguage: String?, imageLanguage: String?, mode: String?): String =
        imageLanguage ?: when (mode) {
            CARD_IMAGE_LANGUAGE_DE -> CARD_IMAGE_LANGUAGE_DE
            CARD_IMAGE_LANGUAGE_EN -> CARD_IMAGE_LANGUAGE_EN
            else -> scanLanguage ?: CARD_IMAGE_LANGUAGE_EN
        }

    // Die anzuzeigende URL - Deutsch nur, wenn gewünscht UND bekannt, sonst
    // unverändert (eigene Fotos, lokale Dateien usw. laufen hier unverändert
    // durch, weil sie keinem der Quellmuster entsprechen)
    fun resolve(cardId: String?, imageUrl: String?, scanLanguage: String?, imageLanguage: String?, mode: String?): String? {
        if (wantedLanguage(scanLanguage, imageLanguage, mode) != CARD_IMAGE_LANGUAGE_DE) return imageUrl
        return germanImageUrl(cardId, imageUrl) ?: imageUrl
    }

    // Für den Bild-Proxy des Servers (siehe /images/proxy in Main.kt bzw.
    // PortfolioRepository.isKnownImageUrl): der Proxy darf nur Katalog-
    // Bilder laden - deutsche Varianten stehen aber nicht im Katalog.
    // Deshalb: TCGdex-de -> englische Ursprungs-URL zur Katalog-Prüfung;
    // FFTCG-CDN "_de.jpg" -> erlaubt, wenn es exakt dem CDN-Muster
    // entspricht (Domain fest, nur Kartennummer variabel); Scryfall/Altered
    // -> nur, wenn Bild-Id bzw. Hash aus unserem Mapping stammt.
    fun englishSourceUrl(url: String): String? =
        if (url.startsWith(TCGDEX_DE_PREFIX)) TCGDEX_EN_PREFIX + url.removePrefix(TCGDEX_DE_PREFIX) else null

    private val fftcgGermanUrlRegex = Regex("^https://fftcg\\.cdn\\.sewest\\.net/images/cards/full/[A-Za-z0-9-]{3,12}_de\\.jpg$")
    private val scryfallUrlRegex = Regex("^https://cards\\.scryfall\\.io/large/front/[0-9a-f]/[0-9a-f]/([0-9a-f-]{36})\\.jpg(\\?\\d+)?$")
    private val alteredGermanUrlRegex = Regex("^https://raw\\.githubusercontent\\.com/AlteredEquinox/cards-nonunique/main/assets/[A-Z0-9]+/CARDS/[A-Z0-9_]+/de_DE/([0-9a-f]{32})\\.jpg$")
    private val mtgDeImageIds: Set<String> by lazy { mtgDe.values.map { it.substringBefore(' ') }.toHashSet() }
    private val alteredDeHashes: Set<String> by lazy { alteredDe.values.toHashSet() }

    fun isMappedGermanUrl(url: String): Boolean {
        if (fftcgGermanUrlRegex.matches(url)) return true
        scryfallUrlRegex.find(url)?.let { return it.groupValues[1] in mtgDeImageIds }
        alteredGermanUrlRegex.find(url)?.let { return it.groupValues[1] in alteredDeHashes }
        return false
    }
}
