package com.tcgportfolio.server

import com.tcgportfolio.companion.DatabaseDriverFactory
import com.tcgportfolio.companion.DatabaseModule
import com.tcgportfolio.companion.LocalizedCardImages
import com.tcgportfolio.companion.CARD_IMAGE_LANGUAGE_DE
import com.tcgportfolio.companion.CARD_IMAGE_LANGUAGE_EN
import com.tcgportfolio.companion.CARD_IMAGE_LANGUAGE_MODE_AS_SCANNED
import com.tcgportfolio.companion.PortfolioRepository
import com.tcgportfolio.companion.refreshCardmarketPricesIfStale
import com.tcgportfolio.companion.refreshDbfwCardRulesIfStale
import com.tcgportfolio.companion.refreshPokemonCardRulesIfStale
import com.tcgportfolio.companion.searchCardmarketProducts
import com.tcgportfolio.companion.browseCardmarketProductsInSet
import com.tcgportfolio.companion.cardmarketGameIdFor
import com.tcgportfolio.companion.fetchCardmarketPriceGuide
import com.tcgportfolio.companion.fetchCardmarketProductList
import com.tcgportfolio.companion.fetchImageForProxy
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticFiles
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.header
import io.ktor.server.request.path
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.request.receiveText
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.utils.io.core.readBytes
import java.io.File
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.Duration
import javax.jmdns.JmDNS
import javax.jmdns.ServiceInfo

// Grundgerüst für Phase 1e (siehe CONCEPT.md, "Self-hosted Sync-Server").
// Nutzer-Entscheidungen (2026-07-23), auf denen dieses Gerüst aufbaut:
//   - Nur LAN, keine Internet-Erreichbarkeit -> kein HTTPS/Zertifikat-Aufwand
//     hier, Bindung auf 0.0.0.0 ist im Heimnetz-Kontext beabsichtigt (jedes
//     Gerät im selben LAN soll den Server über die Host-IP erreichen können;
//     kein Port-Forwarding einrichten, sonst wäre er auch von außen erreichbar).
//   - Sync-Transport: direkt WebSocket statt REST-Polling.
// Datenschicht (Repository, Katalog, SQLDelight-Schema) kommt jetzt aus dem
// eigenständigen :data-Modul, das auch die App (:shared) nutzt (2026-07-23,
// siehe data/build.gradle.kts) - eigene, lokale SQLite-Datei des Servers
// (siehe DatabaseDriverFactory im :data-Modul), NICHT dieselbe Datei wie am
// Handy.
//
// Pairing/Auth (2026-07-24): einfaches geteiltes Token statt vollem
// Nutzerkonto-System - passt zum "1 Person, mehrere eigene Geräte"-
// Anwendungsfall (siehe CONCEPT.md, Nicht-Ziele). Token wird beim ersten
// Start zufällig erzeugt, in der DB gespeichert (wiederverwendet dieselbe
// AppSettings-Tabelle wie die App) und beim Start ins Log geschrieben - wer
// physischen/Log-Zugriff auf den Server hat, gilt als berechtigt, das Token
// einmalig abzulesen und in App/Weboberfläche einzutragen (dasselbe
// Bootstrap-Vertrauensmodell wie bei vielen Self-Hosted-Tools, z.B. Home
// Assistant). Schützt vor fremden Geräten im selben WLAN, keine echte
// Verschlüsselung (weiterhin nur HTTP, kein TLS - siehe "Nur LAN"-Entscheidung).
fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    registerMdnsService(port)
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::ult1madeServerModule)
        .start(wait = true)
}

// Kündigt den Server per mDNS/Bonjour an ("_ult1made._tcp"), damit die App
// ihn per Server-Erkennung findet statt die IP von Hand einzutippen (Phase
// 1e). Reines Komfort-Feature - schlägt die Ankündigung fehl (z.B. auf einer
// Maschine mit ungewöhnlicher Netzwerkkonfiguration wie VPN/mehreren
// Interfaces), bleibt der Server über die manuell eingetragene IP trotzdem
// ganz normal nutzbar, deshalb kein harter Fehler
private fun registerMdnsService(port: Int) {
    try {
        val jmdns = JmDNS.create()
        val serviceInfo = ServiceInfo.create(
            "_ult1made._tcp.local.",
            "Ult1made TCG Server",
            port,
            "Ult1made TCG Sync-Server"
        )
        jmdns.registerService(serviceInfo)
        Runtime.getRuntime().addShutdownHook(
            Thread {
                jmdns.unregisterAllServices()
                jmdns.close()
            }
        )
        println("mDNS-Ankündigung aktiv: _ult1made._tcp.local. auf Port $port")
    } catch (e: Exception) {
        println("mDNS-Ankündigung fehlgeschlagen (Server läuft trotzdem normal weiter): ${e.message}")
    }
}

private fun generatePairingToken(): String {
    // Ohne leicht verwechselbare Zeichen (0/O, 1/I/L), damit man es bequem
    // von Log/Weboberfläche abtippen kann
    val chars = "ABCDEFGHJKMNPQRSTUVWXYZ23456789"
    return (1..8).map { chars.random() }.joinToString("")
}

// Mandantenfähigkeit (02.08., Nutzer-Vorgabe) - siehe CONCEPT.md
// "Mandantenfähigkeit / Mehrere Accounts". hasPin statt des Hashs selbst in
// der Antwort - die Weboberfläche muss nur wissen OB eine PIN nötig ist, der
// Hash selbst hat dort nichts verloren. Die eigentliche Prüfung läuft über
// /api/accounts/verifyPin serverseitig.
// Nutzer-Vorgabe (08.08.) "IP-Adresse und Pairing-Token direkt sichtbar" -
// bisher stand das Token nur im Server-Log, die IP musste man sich selbst
// zusammensuchen. Absichtlich öffentlich (siehe isPublic-Liste oben) - ohne
// das Token kann man die restlichen /api/*-Endpunkte ohnehin nicht nutzen,
// UND das Token steht ohnehin schon im Klartext im Server-Log; wer die
// Weboberfläche im eigenen Heimnetz erreicht, hätte auch dort Zugriff
// darauf. Die IP selbst liefert der Server nicht mit - der Browser kennt
// sie bereits über window.location, siehe app.js.
@Serializable
data class PairingInfoResponse(val pairingToken: String)

// TCGs pro Account ausblenden (17.08., Nutzer-Vorgabe "Ich möchte beim
// Server unter Einstellungen Pro Account TCGs ausblenden können. Standard-
// mäßig soll aber alles eingeblendet sein") - dieselbe Klasse für Request
// und Response, wie MarketFactorResponse weiter unten.
@Serializable
data class HiddenGamesResponse(val hidden: List<String>)

@Serializable
data class AccountResponse(
    val id: Long,
    val name: String,
    val hasPin: Boolean,
    // uid (10.08., Nutzer-Fund "auf einem zweiten Handy synct sich ein neuer
    // Geister-Account statt sich mit dem bestehenden zu verbinden") - die
    // stabile Sync-Identität des Accounts, siehe Kommentar bei SyncAccount in
    // SyncModels.kt. Wird von der App VOR dem allerersten echten Sync mit
    // einem Server abgefragt (siehe SyncClient.fetchServerAccounts()), damit
    // ein frisch installiertes Gerät seinen eigenen, noch unbenutzten
    // Platzhalter-Account an einen bereits existierenden Server-Account
    // "andocken" kann, statt beim Sync einen zweiten, gleichnamigen Account
    // anzulegen. Unbedenklich, dieser Endpunkt läuft ohnehin schon hinter dem
    // Pairing-Token.
    val uid: String = ""
)

@Serializable
data class CreateAccountRequest(val name: String, val pin: String? = null)

@Serializable
data class RenameAccountRequest(val id: Long, val name: String)

@Serializable
data class SetAccountPinRequest(val id: Long, val pin: String? = null)

@Serializable
data class VerifyAccountPinRequest(val id: Long, val pin: String)

@Serializable
data class VerifyAccountPinResponse(val correct: Boolean)

@Serializable
data class DeleteAccountRequest(val id: Long)

@Serializable
data class CollectionCardResponse(
    // Eigene PortfolioItemEntity-Id (03.08., Nutzer-Vorgabe "Massenauswahl"
    // auf der Weboberfläche) - anders als cardId eindeutig auch bei
    // mehreren Zeilen derselben Katalogkarte (z.B. normal + Holo als
    // getrennte Bestandszeilen), siehe App.kt-Kommentar bei
    // selectedOwnedCardIds für dasselbe Prinzip
    val id: Long,
    val cardId: String?,
    val name: String,
    val quantity: Long,
    val isHolo: Boolean,
    val game: String?,
    val setId: String?,
    val marketPriceUsd: Double?,
    val imageUrl: String?,
    val purchasePrice: Double,
    // Cardmarket-EUR-Preis + Optionen (28.07., Nutzer-Vorgabe "let the user
    // decide") - für die Kartendetailansicht auf der Weboberfläche
    val marketPriceEur: Double? = null,
    val marketPriceEurOptions: List<Double> = emptyList(),
    val marketPriceEurSelectedIndex: Int? = null,
    // Rarität (31.07., Nutzer-Vorgabe) - für den Raritäts-Filter auf der
    // Weboberfläche
    val rarity: String? = null,
    // Eigener Preis (20.08., Nutzer-Fund "beim Server kann man einer Karte
    // noch keinen eigenen Preis geben") - gleiche Semantik wie in der App,
    // siehe Spalten-Kommentar in Portfolio.sq
    val customPriceEur: Double? = null,
    val customPriceInTotal: Long = 1,
    val customPriceInGameTotal: Long = 1,
    // Sprachbewusste Kartenbilder (07.09., siehe LocalizedCardImages.kt):
    // Scan-Sprache, Pro-Karte-Override und die deutsche Bild-URL (null =
    // keine bekannt) - die Weboberfläche entscheidet damit clientseitig
    // (globaler Modus aus /api/cardImageLanguageMode), welches Bild sie zeigt
    val scanLanguage: String? = null,
    val imageLanguage: String? = null,
    val imageUrlDe: String? = null
)

@Serializable
data class SealedProductResponse(
    // Eigene SealedProductEntity-Id (09.08., Nutzer-Vorgabe "Feature-Parität
    // App <-> Web") - fehlte bisher, weil die Weboberfläche Vault-Produkte
    // nur anzeigen, aber nicht bearbeiten/löschen konnte (siehe
    // /api/sealed/update, /api/sealed/delete), dafür braucht es wie bei
    // CollectionCardResponse.id eine eindeutige Zeilen-Referenz.
    val id: Long,
    val name: String,
    val category: String,
    val quantity: Long,
    val isSealed: Boolean,
    val game: String,
    val marketPriceUsd: Double?,
    // Cardmarket-EUR-Preis für Sealed-Produkte (03.08., Nutzer-Vorgabe "die
    // Euro Preise bei Sealed Produkten auf der Web Oberfläche") - siehe
    // applyCardmarketSealedPrices() in PortfolioRepository.kt
    val marketPriceEur: Double? = null,
    // catalogId + Optionen (11.08.) - Pendant zu CollectionCardResponse.cardId/
    // marketPriceEurOptions oben, für die manuelle Cardmarket-Notierungswahl
    // in der Sealed-Produkt-Detailansicht (siehe renderDetailCardmarketOptions()
    // in app.js). catalogId ist NULL bei frei eingetragenen Produkten ohne
    // Katalogbezug (siehe SealedProductEntity-Kommentar in Portfolio.sq) -
    // für die gibt es dann auch keine Notierungswahl.
    val catalogId: String? = null,
    val marketPriceEurOptions: List<Double> = emptyList(),
    val imageUrl: String?,
    val purchasePrice: Double,
    // Eigener Preis (20.08.) - siehe CollectionCardResponse
    val customPriceEur: Double? = null,
    val customPriceInTotal: Long = 1,
    val customPriceInGameTotal: Long = 1
)

// Werte-Tab auf der Weboberfläche (26.07., Nutzer-Vorgabe: "der ganze Rest,
// der unter Werte in der App ist, genau so übernehmen") - der Umrechnungs-
// faktor (USD die Alt-Quelle -> EUR, siehe ValueOverviewScreen.kt in der App) ist
// dort bewusst pro Gerät lokal kalibriert, NICHT Teil des Sync-Protokolls.
// Der Server bekommt hier eine eigene, unabhängige Kalibrierung (eigene
// AppSettings-Zeile in seiner eigenen DB) statt diese vom Handy zu
// übernehmen - beide Werte müssen nicht übereinstimmen.
@Serializable
data class MarketFactorResponse(val factor: Float)

// Set-Tracking auf der Weboberfläche (26.07.) - Sets als eigene Antwort mit
// Fortschritt (owned/total), analog zur Set-Auswahl in der App
@Serializable
data class SetResponse(
    val id: String,
    val name: String,
    val totalCards: Long,
    val ownedCount: Long
)

// Voller Katalog eines einzelnen Sets (nicht nur die eigenen Karten) - für
// "Ganzes Set"/"Fehlende" auf der Weboberfläche, die auch nicht besessene
// Karten als Platzhalter zeigen sollen
@Serializable
data class CatalogCardResponse(
    val id: String,
    val number: String,
    val name: String,
    val imageUrl: String,
    val marketPriceUsd: Double?,
    // Cardmarket-EUR-Preis + alle gefundenen Alternativen (28.07., Nutzer-
    // Vorgabe "let the user decide") - marketPriceEurOptions ist die volle,
    // aufsteigend sortierte Liste, marketPriceEurSelectedIndex zeigt (falls
    // gesetzt) die manuelle Nutzer-Auswahl. Für eine künftige Web-Oberfläche
    // zum Umschalten vorbereitet, siehe /api/cardmarketPrice/select.
    val marketPriceEur: Double? = null,
    val marketPriceEurOptions: List<Double> = emptyList(),
    val marketPriceEurSelectedIndex: Int? = null,
    // Rarität (31.07., Nutzer-Vorgabe) - für den Raritäts-Filter auf der
    // Weboberfläche
    val rarity: String? = null,
    // Art-Variante (Normal/Alternate Art/...) - für den Art-Wechsel in der
    // Kartendetailansicht, siehe changeCardVariant()
    val variant: String? = null,
    // Typ-/Farb-Filter (28.08., Parität zur App vom 19.08.) - Kartentyp
    // (Supertype) + Farben/Subtypen für die Filter-Chips im Set-Grid
    val ruleSupertype: String? = null,
    val ruleSubtypes: List<String> = emptyList()
)

@Serializable
data class SelectCardmarketPriceRequest(val cardId: String, val index: Int)

@Serializable
data class ResetCardmarketPriceRequest(val cardId: String)

// Manuelle Cardmarket-Zuordnung (04.08., Nutzer-Vorgabe "das muss ja beim
// Server dann sowieso auch gebaut werden") - 1:1 dieselbe Funktion wie in
// der App (CardmarketMappingScreen.kt), siehe PortfolioRepository.kt für
// die Datenmodell-Seite.
@Serializable
data class CardmarketMappingCardResponse(
    val id: String,
    val name: String,
    val imageUrl: String,
    val marketPriceEur: Double? = null,
    val manualProductName: String? = null
)

@Serializable
data class CardmarketMappingSearchResponse(val idProduct: Long, val name: String, val priceEur: Double?)

@Serializable
data class AssignCardmarketMappingRequest(val cardId: String, val productId: Long, val productName: String)

@Serializable
data class RemoveCardmarketMappingRequest(val cardId: String)

// Sealed-Pendant zu den Cardmarket-Zuordnungs-DTOs oben (11.08.,
// Nutzer-Vorgabe "können wir das dann so wie bei den Karten machen") - siehe
// SealedCardmarketMappingScreen.kt/PortfolioRepository.kt für die App-/
// Datenmodell-Seite.
@Serializable
data class SelectSealedCardmarketPriceRequest(val sealedId: String, val index: Int)

@Serializable
data class ResetSealedCardmarketPriceRequest(val sealedId: String)

@Serializable
data class SealedCardmarketMappingItemResponse(
    val id: String,
    val name: String,
    val imageUrl: String? = null,
    val marketPriceEur: Double? = null,
    val manualProductName: String? = null
)

@Serializable
data class AssignSealedCardmarketMappingRequest(val sealedId: String, val productId: Long, val productName: String)

@Serializable
data class RemoveSealedCardmarketMappingRequest(val sealedId: String)

// Art-Varianten-Wechsel (31.07., Nutzer-Vorgabe "wenn man einen anderen
// Preis wählt, kann man auch das Bild ändern") - siehe
// PortfolioRepository.changeCardVariant(). Identifiziert die zu ändernde
// Zeile über cardId+isHolo statt einer numerischen id, siehe Kommentar dort.
@Serializable
data class ChangeCardVariantRequest(val cardId: String, val isHolo: Boolean, val newCardId: String)

// "Karte hinzufügen" auf der Weboberfläche (26.07., Nutzer-Vorgabe) - Suche
// im Katalog, dann direktes Hinzufügen ohne Umweg über den Sync (der Server
// schreibt hier direkt in seine eigene DB, exakt wie addCard() das schon für
// die App tut - inklusive automatischem updatedAt-Zeitstempel fürs
// spätere Sync-Protokoll)
@Serializable
data class SearchCatalogResponse(
    val id: String,
    val name: String,
    val number: String,
    val setId: String,
    val setName: String,
    val imageUrl: String,
    val marketPriceUsd: Double?
)

@Serializable
data class AddCardRequest(
    val cardId: String,
    val name: String,
    val imageUrl: String?,
    val quantity: Long = 1,
    val isHolo: Boolean = false
)

// Kartendetail-Bearbeitung auf der Weboberfläche (09.08., Nutzer-Vorgabe
// "Feature-Parität App <-> Web") - Web-Pendant zu updateCard() in
// PortfolioRepository.kt, das die App-Detailansicht (App.kt) schon länger
// nutzt (Anzahl/Holo/Kaufpreis bearbeiten).
@Serializable
data class UpdateCardRequest(val id: Long, val quantity: Long, val isHolo: Boolean, val purchasePrice: Double)

// Eigener Preis (20.08., Nutzer-Fund Feature-Parität) - für Karten UND
// Sealed-Produkte, priceEur = null löscht den Eintrag
@Serializable
data class CustomPriceRequest(val id: Long, val priceEur: Double? = null, val inTotal: Boolean = true, val inGameTotal: Boolean = true)

// Sprachbewusste Kartenbilder (07.09.): Pro-Karte-Override ("de"/"en", null =
// zurück auf die globale Einstellung) und die globale Einstellung selbst
@Serializable
data class ImageLanguageRequest(val id: Long, val language: String? = null)

@Serializable
data class CardImageLanguageModeResponse(val mode: String)

@Serializable
data class RenameWishlistRequest(val id: Long, val name: String)

// Vault-Pendant zur Kartensuche - Katalog-Browsing für ein TCG (dieselbe
// Quelle wie das "Durchstöbern" in der App, getSealedCatalog())
@Serializable
data class SealedCatalogResponse(
    val id: String,
    val name: String,
    val category: String,
    val imageUrl: String?,
    val marketPriceUsd: Double?,
    val marketPriceEur: Double? = null
)

@Serializable
data class AddSealedRequest(
    val catalogId: String?,
    val name: String,
    val category: String,
    val game: String,
    val imageUrl: String?,
    val quantity: Long = 1,
    val isSealed: Boolean = true
)

// Vault-Bearbeitung/-Löschen auf der Weboberfläche (09.08., Nutzer-Vorgabe
// "Feature-Parität App <-> Web") - Web-Pendant zu updateSealedProduct()/
// deleteSealedProduct() in PortfolioRepository.kt, die VaultScreen.kt in der
// App schon länger nutzt.
@Serializable
data class UpdateSealedRequest(
    val id: Long,
    val name: String,
    val category: String,
    val quantity: Long,
    val isSealed: Boolean,
    val purchasePrice: Double,
    val imageUrl: String?
)

// Wunschlisten auf der Weboberfläche (27.07., Nutzer-Vorgabe) - spiegelt
// dieselbe Funktion wie in der App (siehe WishlistScreen.kt), eigene
// REST-Endpunkte statt Sync-Protokoll (Wunschlisten sind bewusst NICHT Teil
// des App<->Server-Sync, siehe PortfolioRepository-Kommentar).
@Serializable
data class WishlistResponse(
    val id: Long,
    val name: String,
    val game: String,
    val itemCount: Long
)

@Serializable
data class WishlistItemResponse(
    val id: Long,
    val wishlistId: Long,
    val cardId: String?,
    val name: String,
    val imageUrl: String?,
    val setId: String?,
    val marketPriceUsd: Double?,
    // Cardmarket-EUR-Preis (01.08., Nutzer-Vorgabe "Cardmarket überall als
    // Standard") - die Alt-Quelle bleibt nur Backup ohne Cardmarket-Notierung
    val marketPriceEur: Double? = null,
    // Rarität (31.07., Nutzer-Vorgabe) - für den Raritäts-Filter auf der
    // Weboberfläche
    val rarity: String? = null
)

@Serializable
data class CreateWishlistRequest(val name: String, val game: String)

@Serializable
data class WishlistCardPayload(val cardId: String?, val name: String, val imageUrl: String?)

@Serializable
data class AddWishlistItemsRequest(val wishlistId: Long, val cards: List<WishlistCardPayload>)

@Serializable
data class IdListRequest(val ids: List<Long>)

// Sealed-Wantslisten (28.08., Server-Parität zur App vom 25.08.) - Köpfe,
// Einträge (mit Katalog-Join für Bild/Preis) und der Preis-Alarm
@Serializable
data class SealedWishlistResponse(val id: Long, val name: String, val game: String, val itemCount: Long)

@Serializable
data class SealedWishlistItemResponse(
    val id: Long,
    val wishlistId: Long,
    val catalogId: String,
    val name: String?,
    val category: String?,
    val imageUrl: String?,
    val marketPriceEur: Double?,
    val priceAlarmEur: Double?
)

@Serializable
data class CreateSealedWishlistRequest(val name: String, val game: String)

@Serializable
data class AddSealedWishlistItemRequest(val wishlistId: Long, val game: String, val catalogId: String)

@Serializable
data class SealedWishlistAlarmRequest(val id: Long, val priceEur: Double?)

@Serializable
data class IdRequest(val id: Long)

@Serializable
data class ShiftBinderItemsRequest(val binderId: Long, val atPosition: Long)

@Serializable
data class TriggeredSealedAlarmResponse(
    val game: String,
    val name: String?,
    val priceAlarmEur: Double?,
    val marketPriceEur: Double?
)

// Binder auf der Weboberfläche (31.07., Nutzer-Vorgabe) - spiegelt dieselbe
// Funktion wie in der App (siehe BinderScreen.kt), REST-Endpunkte 1:1
// analog zu den Wunschlisten-Endpunkten oben.
@Serializable
data class BinderResponse(
    val id: Long,
    val name: String,
    val game: String,
    val itemCount: Long,
    val pageSize: Long,
    // Binder-Optik (25.08., Nutzer-Vorgabe "echte Binder da stehen") -
    // Hex-Farbe des gezeichneten Buchdeckels, null = Akzentfarbe des TCGs.
    // Farbe synct seit 25.08. per LWW mit der App (colorUpdatedAt);
    // coverImageUrl kommt über den Foto-Sync-Kanal (/api/photoSync/*) und
    // zeigt hier auf die server-lokale /images/custom-Kopie.
    val color: String? = null,
    val coverImageUrl: String? = null
)

@Serializable
data class BinderColorRequest(val id: Long, val color: String? = null)

// Foto-Sync (25.08., Nutzer-Korrektur "eigene Fotos müssen mitsyncen") -
// Inventar-Eintrag, Feldnamen müssen 1:1 zu PhotoSyncEntry in
// shared/SyncClient.kt passen (server/ ist reines JVM-Modul, kein Import
// aus shared möglich - gleiche Lage wie RemoteAccountPreview dort)
@Serializable
data class PhotoSyncEntryResponse(val kind: String, val key: String, val updatedAt: Long)

@Serializable
data class BinderItemResponse(
    val id: Long,
    val binderId: Long,
    val cardId: String?,
    val name: String,
    val imageUrl: String?,
    val setId: String?,
    val marketPriceUsd: Double?,
    // Cardmarket-EUR-Preis (01.08., Nutzer-Vorgabe "Cardmarket überall als
    // Standard") - die Alt-Quelle bleibt nur Backup ohne Cardmarket-Notierung
    val marketPriceEur: Double? = null,
    val rarity: String?,
    val position: Long
)

// pageSize (31.07., Nutzer-Vorgabe "9, 12 oder 16 Karten pro Seite") - siehe
// Kommentar bei BinderEntity in Portfolio.sq. Default 9 nur als Fallback,
// die Weboberfläche schickt immer einen der drei Werte mit.
@Serializable
data class CreateBinderRequest(val name: String, val game: String, val pageSize: Long = 9)

@Serializable
data class RenameBinderRequest(val id: Long, val name: String)

@Serializable
data class BinderCardPayload(val cardId: String?, val name: String, val imageUrl: String?)

@Serializable
data class AddBinderItemsRequest(val binderId: Long, val cards: List<BinderCardPayload>)

// Kartentausch (31.07., Nutzer-Vorgabe "Kartenplätze ändern können, damit
// man die Realität abbilden kann") - siehe PortfolioRepository.swapBinderItemPositions()
@Serializable
data class SwapBinderItemsRequest(val itemId1: Long, val itemId2: Long)

// Verschieben auf einen leeren Platz (kein Tausch nötig) - siehe
// PortfolioRepository.moveBinderItemToPosition()
@Serializable
data class MoveBinderItemRequest(val itemId: Long, val newPosition: Long)

// Vollständiges JSON-Backup (10.08., Feature-Parität App <-> Web) - die
// Funktionen selbst (PortfolioRepository.exportData()/importData()) gab es
// schon lange (siehe BackupDialog.kt in der App), nur der Server hatte dafür
// noch keine REST-Route. Bewusst GETRENNT von /sync (siehe webSocket("/sync")
// unten) - das ist reine Server<->Server/App-Zusammenführung, hier geht es um
// eine herunterladbare/wieder einlesbare Datei wie beim App-Backup-Dialog.
@Serializable
data class BackupImportResponse(
    val cardsAdded: Int,
    val sealedAdded: Int,
    // Backup v2 (28.08.) - Binder/Wants/Decks stellt importData seither mit her
    val bindersAdded: Int = 0,
    val binderItemsAdded: Int = 0,
    val wishlistsAdded: Int = 0,
    val wishlistItemsAdded: Int = 0,
    val decksAdded: Int = 0,
    val deckCardsAdded: Int = 0,
    // Backup v3 (28.08.) - wiederhergestellte eigene Fotos
    val photosRestored: Int = 0
)

// Eigenes Foto (10.08.) - siehe /api/customPhoto/* weiter unten
@Serializable
data class CustomPhotoUploadResponse(val imageUrl: String)

@Serializable
data class ClearCustomPhotoRequest(val cardId: String)

// Eigenes Foto für Sealed-/Vault-Produkte (12.08., Nutzer-Vorgabe "fehlt
// noch bei Vault Produkten") - im Unterschied zu Karten braucht es hier
// keine eigene CustomXPhotoEntity-Tabelle, da SealedProductEntity.imageUrl
// schon eine direkt beschreibbare Spalte auf der besessenen Zeile ist
// (keine Neu-Seeding-Gefahr wie beim Kartenkatalog). "Zurücksetzen" heisst
// hier deshalb auch nicht "löschen", sondern "zurück auf das Katalogbild",
// falls eines existiert - siehe /api/sealedPhoto/clear unten.
@Serializable
data class SealedPhotoUploadResponse(val imageUrl: String)

@Serializable
data class ClearSealedPhotoRequest(val sealedId: Long)

// Leere Seite einfügen (10.08., Feature-Parität App <-> Web) - die Funktion
// selbst (PortfolioRepository.insertBinderPage()) gab es schon seit der
// Binder-Seiten-Einführung, nur der Server hatte dafür noch keinen
// Endpunkt. pageSize kommt vom Client mit (nicht aus der DB nachgeladen),
// exakt wie beim bestehenden Kartentausch/Verschieben-Fluss.
@Serializable
data class InsertBinderPageRequest(val binderId: Long, val atPage: Int, val pageSize: Long)

// Decks (02.08., Phase 3 Deckbuilding, Nutzer-Vorgabe) - siehe
// CONCEPT.md "Deckbuilding". cardCount ist die SUMME der quantity-Werte
// (also die Gesamtzahl physischer Karten im Deck, nicht die Anzahl
// unterschiedlicher Kartennamen).
@Serializable
data class DeckResponse(
    val id: Long,
    val name: String,
    val game: String,
    val cardCount: Long
)

@Serializable
data class DeckCardResponse(
    val id: Long,
    val deckId: Long,
    val cardId: String,
    val name: String,
    val imageUrl: String?,
    val setId: String?,
    val number: String?,
    val rarity: String?,
    val ruleSupertype: String?,
    val ruleSubtypes: List<String> = emptyList(),
    val quantity: Long
)

@Serializable
data class CreateDeckRequest(val name: String, val game: String)

@Serializable
data class AddDeckCardRequest(val deckId: Long, val cardId: String, val quantity: Long = 1)

@Serializable
data class SetDeckCardQuantityRequest(val deckCardId: Long, val quantity: Long)

// available = false heißt "für dieses Spiel gibt es (noch) kein Regelwerk"
// (aktuell nur Pokémon, siehe PortfolioRepository.validateDeck()) - bewusst
// von "keine Verstöße gefunden" unterschieden, damit die Oberfläche nicht
// fälschlich "regelkonform" anzeigt, wo eigentlich gar nicht geprüft wurde.
@Serializable
data class DeckValidationResponse(
    val available: Boolean,
    val violations: List<String> = emptyList(),
    val cardsWithoutRuleData: Int = 0,
    val totalDistinctCards: Int = 0
)

fun Application.ult1madeServerModule() {
    val repository = DatabaseModule(DatabaseDriverFactory()).repository
    val logger = log

    // Eigene Fotos (10.08., Feature-Parität App <-> Web) - die App speichert
    // ein selbst aufgenommenes Foto lokal auf dem Gerät (siehe
    // LocalImageStorage.kt/PlainPhotoCaptureOverlay), der Server braucht
    // dafür ein echtes, SCHREIBBARES Verzeichnis auf der Festplatte (NICHT
    // die "static"-Resourcen weiter unten - die liegen im JAR/Classpath und
    // sind zur Laufzeit read-only). Liegt relativ zum Arbeitsverzeichnis,
    // genau wie portfolio.db selbst (siehe DatabaseDriverFactory), damit
    // beide zusammen gesichert/verschoben werden können.
    val customPhotosDir = File(System.getenv("CUSTOM_PHOTOS_DIR") ?: "customPhotos").apply { mkdirs() }

    // Persistenter Bild-Cache (17.08., Nutzer-Vorgabe "Bilder ... speichern,
    // damit sie nicht neu geladen werden müssen") - liegt wie customPhotos/
    // portfolio.db im Arbeitsverzeichnis bzw. Docker-Volume, wandert also
    // beim Backup/Umzug automatisch mit. Bytes liegen unter <name>, der
    // Content-Type daneben unter <name>.ct - zwei triviale Dateien statt
    // Datenbank-Blobs. Dateinamens-Schema identisch zur App
    // (OwnedImageCache.fileNameFor): URL-Hash + die letzten drei
    // Pfadsegmente. Nur das LETZTE Segment reichte nicht (Review-Fund
    // 17.08.): über 10.000 TCGdex-URLs enden identisch auf "high.webp",
    // die Eindeutigkeit hing dort allein am 32-Bit-Hash. Bei Änderungen
    // BEIDE Stellen anpassen (alte Cache-Einträge im vorigen Schema werden
    // schlicht neu geholt - verwaiste Dateien stören nicht).
    val imageCacheDir = File(System.getenv("IMAGE_CACHE_DIR") ?: "imageCache").apply { mkdirs() }
    fun imageCacheFileFor(url: String): File {
        val segments = url.substringAfter("://").substringBefore('?')
            .split('/').filter { it.isNotBlank() }
        val tail = segments.takeLast(3).joinToString("_")
            .filter { it.isLetterOrDigit() || it == '.' || it == '-' || it == '_' }
            .takeLast(80)
        return File(imageCacheDir, "${url.hashCode().toUInt().toString(16)}-$tail")
    }

    // Proaktive Bestands-Sicherung: lädt die Bilder ALLER besessenen Karten/
    // Sealed-Produkte (alle Accounts) in den Platten-Cache - als Versicherung
    // gegen sterbende Bildquellen (siehe Altered in CONCEPT.md), nicht nur
    // als Beschleuniger. Läuft beim Start, danach alle 6h (gleiche
    // Schleifen-Begründung wie beim Cardmarket-Abgleich unten) und nach
    // jedem App-Sync (neue Karten -> gleich sichern). AtomicBoolean statt
    // Mutex: überlappende Anstöße werden einfach übersprungen, der nächste
    // Lauf holt Fehlendes nach. Bewusst gemächlich (200ms Pause pro
    // Download) - Hintergrund-Versicherung, kein Burst auf freie Quellen.
    val imagePrefetchRunning = java.util.concurrent.atomic.AtomicBoolean(false)
    suspend fun prefetchOwnedImagesOnce() {
        if (!imagePrefetchRunning.compareAndSet(false, true)) return
        try {
            var stored = 0
            for (url in repository.getOwnedImageUrls()) {
                val file = imageCacheFileFor(url)
                if (file.exists()) continue
                val fetched = fetchImageForProxy(url) ?: continue
                file.writeBytes(fetched.first)
                fetched.second?.let { File(file.path + ".ct").writeText(it) }
                stored++
                delay(200)
            }
            if (stored > 0) logger.info("Bild-Cache: $stored Bestandsbilder neu gesichert")
        } catch (e: Exception) {
            logger.warn("Bild-Cache-Prefetch fehlgeschlagen: ${e.message}")
        } finally {
            imagePrefetchRunning.set(false)
        }
    }
    launch {
        while (true) {
            prefetchOwnedImagesOnce()
            delay(6 * 60 * 60 * 1000L)
        }
    }

    // Ohne diesen Aufruf bleibt der eigene Karten-/Sealed-Katalog des Servers
    // leer, obwohl er dieselbe :data-Logik wie die App nutzt - die App seedet
    // ihn beim eigenen Start (App.kt), der Server bisher nicht. Das führte
    // dazu, dass importSyncData() zwar den Bestand (PortfolioItemEntity)
    // übernimmt, aber "game"/"marketPriceUsd"/echte "imageUrl" über den JOIN
    // auf CardCatalogEntity/CardSet mangels lokalem Katalog leer blieben - der
    // Spiel-Filter in der Weboberfläche fand dadurch nie eine Übereinstimmung
    // (Nutzer-Fund 24.07.: "Alle" zeigte Karten, der DBFW-Filter nichts).
    // Idempotent (INSERT OR IGNORE), kann bei jedem Start gefahrlos laufen.
    //
    // Startzeit-Fix (17.08., Nutzer-Beschwerde "selbst wenn der Container
    // auf started steht dauert es relativ lange bis er über die
    // Weboberfläche erreichbar ist"): das Seeding lief bisher SYNCHRON vor
    // routing{} - bei einem CATALOG_SEED_VERSION-Sprung (>160.000 Karten,
    // auf der VM-Platte Minuten) war der Server so lange komplett
    // unerreichbar. Jetzt läuft es im Hintergrund, exakt wie der
    // App-Startzeit-Fix vom 03.08. (dort: Katalog-Load raus aus dem Splash):
    // die Weboberfläche ist sofort da; während eines laufenden Nachzugs sind
    // Katalog-Joins schlimmstenfalls kurz unvollständig (gleiches, bewusst
    // akzeptiertes Verhalten wie in der App während des Seedings). Bei
    // unveränderter Version ist ensureCatalogSeeded() ohnehin ein billiger
    // Früh-Return - der Normal-Start bleibt unverändert.
    launch {
        val seedStart = System.currentTimeMillis()
        try {
            repository.ensureCatalogSeeded()
            repository.ensureSealedCatalogSeeded()
            // Reparatur des "One Piece"/"OnePiece"-Tippfehlers (31.07.) -
            // siehe PortfolioRepository.ensureOnePieceGameNameFixed()
            repository.ensureOnePieceGameNameFixed()
            logger.info("Katalog-Seed geprüft/aktualisiert in ${System.currentTimeMillis() - seedStart}ms")
        } catch (e: Exception) {
            logger.warn("Katalog-Seed fehlgeschlagen: ${e.message}")
        }
    }
    // Nachrüsten der stabilen Wunschlisten-uid für Listen, die vor Einführung
    // des Wunschlisten-Syncs angelegt wurden (28.07.) - siehe Kommentar bei
    // WishlistEntity/ensureWishlistUidsBackfilled() in PortfolioRepository.
    // Bleibt wie die Account-Reparaturen darunter bewusst SYNCHRON - alles
    // billige Einzel-Queries, und resolveAccountId()/Wunschlisten-Routen
    // sollen ab der allerersten Anfrage korrekte Daten sehen.
    repository.ensureWishlistUidsBackfilled()
    repository.ensureSealedWishlistUidsBackfilled()
    // Accounts (02.08.) - siehe Kommentare bei ensureDefaultAccountExists()/
    // ensureAccountUidsBackfilled() in PortfolioRepository. Reihenfolge:
    // erst sicherstellen, dass es überhaupt einen Account gibt, dann fehlende
    // uids nachrüsten (betrifft in der Praxis nur sehr alte, direkt in der
    // DB angelegte Zeilen, die es normalerweise nicht gibt).
    repository.ensureDefaultAccountExists()
    repository.ensureAccountUidsBackfilled()

    // Cardmarket-Preisabgleich (28.07., zunächst nur DBFW, siehe
    // CardmarketPriceSync.kt) - läuft beim Start (refreshCardmarketPricesIfStale()
    // bricht selbst ab, falls seit <24h schon aktualisiert wurde) und danach
    // alle 6h erneut geprüft. Ein reiner Start-Check würde bei einem Server,
    // der (anders als die App) oft wochenlang am Stück durchläuft, "täglich"
    // nie erneut auslösen.
    launch {
        while (true) {
            try {
                refreshCardmarketPricesIfStale(repository)
            } catch (e: Exception) {
                logger.warn("Cardmarket-Preisabgleich fehlgeschlagen: ${e.message}")
            }
            delay(6 * 60 * 60 * 1000L)
        }
    }

    // Pokémon-Deckbau-Regel-Metadaten (02.08., siehe PokemonCardRulesSync.kt) -
    // läuft höchstens 1x pro Monat (Kartentyp/Legalität ändern sich viel
    // seltener als Preise), derselbe "eigene Endlosschleife statt nur
    // Start-Check"-Grund wie beim Cardmarket-Abgleich oben.
    launch {
        while (true) {
            try {
                refreshPokemonCardRulesIfStale(repository)
            } catch (e: Exception) {
                logger.warn("Pokémon-Regel-Abgleich fehlgeschlagen: ${e.message}")
            }
            delay(6 * 60 * 60 * 1000L)
        }
    }

    // DBFW-Deckbau-Regel-Metadaten (03.08., Nutzer-Vorgabe "auch für DBFW
    // hinbekommen") - siehe DbfwCardRulesSync.kt, gleiches Muster wie beim
    // Pokémon-Regel-Abgleich oben (eigene, unabhängige monatliche Schleife).
    launch {
        while (true) {
            try {
                refreshDbfwCardRulesIfStale(repository)
            } catch (e: Exception) {
                logger.warn("DBFW-Regel-Abgleich fehlgeschlagen: ${e.message}")
            }
            delay(6 * 60 * 60 * 1000L)
        }
    }

    val pairingToken = repository.getSetting("pairingToken") ?: generatePairingToken().also {
        repository.setSetting("pairingToken", it)
    }
    logger.info("=".repeat(50))
    logger.info("Pairing-Token für App/Weboberfläche: $pairingToken")
    logger.info("=".repeat(50))

    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(30)
    }

    // Prüft das Pairing-Token für alles außer der reinen Seiten-Hülle
    // (HTML/CSS/JS dürfen laden, damit die Weboberfläche überhaupt ein
    // Eingabefeld fürs Token anzeigen kann) und /health (fürs Docker-
    // Healthcheck ohne Token-Kenntnis)
    intercept(ApplicationCallPipeline.Plugins) {
        val path = call.request.path()
        // /images/* dazu (24.07.): die TCG-Button-Fotos werden per normalem
        // <img src="..."> geladen, Browser können dabei keine eigenen Header
        // (X-Pairing-Token) mitschicken - ohne diese Ausnahme wären die
        // Buttons immer 401 und nie sichtbar. Unbedenklich, da nur statische
        // Cover-Bilder (Bälle/Münze), keine Sammlungsdaten.
        val isPublic = path == "/health" ||
            path == "/" ||
            path == "/style.css" ||
            path == "/app.js" ||
            path == "/api/pairingInfo" ||
            path.startsWith("/images/")
        if (!isPublic) {
            val provided = call.request.header("X-Pairing-Token")
            if (provided != pairingToken) {
                call.respond(HttpStatusCode.Unauthorized, "Ungültiges oder fehlendes Pairing-Token")
                finish()
                return@intercept
            }
        }
    }

    // Mandantenfähigkeit (02.08.) - jeder Request trägt optional an, für
    // welchen Account er gilt (?accountId=<id>), die Weboberfläche schickt
    // das über ihren Umschalter mit (siehe app.js). Ohne Angabe (z.B. eine
    // Web-Session, die den Umschalter noch nicht kennt) fällt der Server auf
    // den Account mit der niedrigsten id zurück - das ist immer der beim
    // allerersten Start automatisch angelegte Standard-Account.
    fun resolveAccountId(call: ApplicationCall): Long {
        call.request.queryParameters["accountId"]?.toLongOrNull()?.let { return it }
        // Die Weboberfläche schickt die Account-Wahl als Header statt als
        // Query-Parameter an jedem einzelnen fetch()-Aufruf (siehe
        // authHeaders() in app.js) - beides wird akzeptiert.
        call.request.header("X-Account-Id")?.toLongOrNull()?.let { return it }
        return repository.getAccounts().minByOrNull { it.id }?.id ?: 1L
    }

    routing {
        // Eigenständige Weboberfläche (2026-07-23) - normales HTML/CSS/JS
        // (bewusst KEIN Compose Web/Wasm, siehe CONCEPT.md: Nutzer wollte
        // "stabil und sicher" statt experimentellem Wasm-Tooling), ruft die
        // /api/*-Endpunkte unten per fetch() ab. Liegt unter
        // server/src/main/resources/static/.
        staticResources("/", "static")
        // Eigene Fotos (10.08.) - echtes Verzeichnis auf der Platte statt
        // Classpath-Resourcen, siehe customPhotosDir oben. Bereits über den
        // bestehenden "/images/"-Präfix im isPublic-Check unten abgedeckt.
        staticFiles("/images/custom", customPhotosDir)

        // Bild-Proxy als Rückfalloption für die Weboberfläche (17.08.,
        // Nutzer-Fund "bei Gundam gar kein Bild, bei One Piece nur 2 von 12,
        // in der App alles da"): Safari verliert beim HTTP/2-Multiplexing
        // vieler paralleler Bild-Streams gegen Apaches mod_http2
        // (www.gundam-gcg.com, en.onepiece-cardgame.com - die EINZIGEN zwei
        // Apache-HTTP/2-Bildquellen im Katalog, exakt die zwei betroffenen)
        // Streams ohne HTTP-Status ("roter Strich" im Web-Inspector).
        // Einzelabrufe funktionieren, deshalb greift der Proxy NICHT für den
        // Normalfall, sondern nur als zweiter Versuch nach einem Bild-
        // Ladefehler (siehe globaler error-Handler in app.js) - der Server
        // holt das Bild mit seinem eigenen, nicht betroffenen HTTP-Client
        // und liefert es same-origin über HTTP/1.1 aus.
        // Absichtlich öffentlich (liegt unter dem /images/-Präfix des
        // isPublic-Checks): <img>-Tags können keine eigenen Header schicken.
        // KEIN offener Proxy: nur https-URLs, die exakt so als Katalog-/
        // Sealed-Bild in der eigenen Datenbank stehen (isKnownImageUrl),
        // werden geladen; bestätigte URLs werden im Speicher gecacht, das
        // Bild selbst cached der Browser über Cache-Control.
        val knownProxyUrls = java.util.concurrent.ConcurrentHashMap.newKeySet<String>()
        get("/images/proxy") {
            val url = call.request.queryParameters["url"]
            if (url == null || !url.startsWith("https://")) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            if (url !in knownProxyUrls) {
                if (!repository.isKnownImageUrl(url)) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                knownProxyUrls.add(url)
            }
            // Persistenter Bild-Cache (17.08.): Platte zuerst - was der
            // Prefetch oder ein früherer Proxy-Abruf schon gesichert hat,
            // wird nie wieder von der Quelle geholt (und übersteht damit
            // auch das Sterben einer Quelle). Netz-Treffer werden im selben
            // Zug für die Zukunft gesichert.
            val cacheFile = imageCacheFileFor(url)
            val cachedType = File(cacheFile.path + ".ct")
                .takeIf { it.exists() }?.readText()
            if (cacheFile.exists()) {
                call.response.header(io.ktor.http.HttpHeaders.CacheControl, "public, max-age=604800")
                val contentType = cachedType?.let { runCatching { ContentType.parse(it) }.getOrNull() }
                    ?: ContentType.Application.OctetStream
                call.respondBytes(cacheFile.readBytes(), contentType)
                return@get
            }
            val fetched = fetchImageForProxy(url)
            if (fetched == null) {
                call.respond(HttpStatusCode.BadGateway)
            } else {
                runCatching {
                    cacheFile.writeBytes(fetched.first)
                    fetched.second?.let { File(cacheFile.path + ".ct").writeText(it) }
                }
                call.response.header(io.ktor.http.HttpHeaders.CacheControl, "public, max-age=604800")
                val contentType = fetched.second?.let { runCatching { ContentType.parse(it) }.getOrNull() }
                    ?: ContentType.Application.OctetStream
                call.respondBytes(fetched.first, contentType)
            }
        }

        get("/health") {
            call.respondText("ok")
        }
        // Nutzer-Vorgabe (08.08.) "IP-Adresse und Pairing-Token direkt
        // sichtbar" - siehe PairingInfoResponse/isPublic-Kommentar oben.
        get("/api/pairingInfo") {
            call.respond(PairingInfoResponse(pairingToken))
        }
        // Mandantenfähigkeit (02.08., Nutzer-Vorgabe) - siehe CONCEPT.md.
        // Bewusst OHNE ?accountId-Filter (anders als fast alle anderen
        // Endpunkte unten) - die Weboberfläche braucht die volle Liste aller
        // Accounts, um den Umschalter überhaupt erst zu befüllen.
        get("/api/accounts") {
            val accounts = repository.getAccounts().map {
                AccountResponse(id = it.id, name = it.name, hasPin = it.pinHash != null, uid = it.uid)
            }
            call.respond(accounts)
        }
        post("/api/accounts") {
            val body = call.receive<CreateAccountRequest>()
            val id = repository.addAccount(body.name, body.pin)
            val uid = repository.getAccountById(id)?.uid.orEmpty()
            call.respond(AccountResponse(id = id, name = body.name, hasPin = body.pin != null, uid = uid))
        }
        post("/api/accounts/rename") {
            val body = call.receive<RenameAccountRequest>()
            repository.renameAccount(body.id, body.name)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/accounts/pin") {
            val body = call.receive<SetAccountPinRequest>()
            repository.setAccountPin(body.id, body.pin)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/accounts/verifyPin") {
            val body = call.receive<VerifyAccountPinRequest>()
            call.respond(VerifyAccountPinResponse(correct = repository.verifyAccountPin(body.id, body.pin)))
        }
        // Kaskadierendes Löschen (02.08., Nutzer-Vorgabe "von Anfang an mit
        // einbauen") - siehe PortfolioRepository.deleteAccount()
        post("/api/accounts/delete") {
            val body = call.receive<DeleteAccountRequest>()
            repository.deleteAccount(body.id)
            call.respond(HttpStatusCode.OK)
        }
        // Beweist den eigentlichen Zweck der :data-Extraktion: dieselbe
        // PortfolioRepository-Logik wie in der App liefert hier echte
        // Sammlungsdaten aus der server-eigenen SQLite-Datei
        get("/api/collection") {
            val accountId = resolveAccountId(call)
            val cards = repository.getAllCardsWithGame(accountId).map {
                CollectionCardResponse(
                    id = it.id,
                    cardId = it.cardId,
                    name = it.name,
                    quantity = it.quantity,
                    isHolo = it.isHolo == 1L,
                    game = it.game,
                    setId = it.setId,
                    marketPriceUsd = it.marketPriceUsd,
                    imageUrl = it.imageUrl,
                    purchasePrice = it.purchasePrice,
                    marketPriceEur = it.marketPriceEur,
                    marketPriceEurOptions = repository.decodeCardmarketOptions(it.marketPriceEurOptions),
                    marketPriceEurSelectedIndex = it.marketPriceEurSelectedIndex?.toInt(),
                    rarity = it.rarity,
                    customPriceEur = it.customPriceEur,
                    customPriceInTotal = it.customPriceInTotal,
                    customPriceInGameTotal = it.customPriceInGameTotal,
                    scanLanguage = it.scanLanguage,
                    imageLanguage = it.imageLanguage,
                    imageUrlDe = LocalizedCardImages.germanImageUrl(it.cardId, it.imageUrl)
                )
            }
            call.respond(cards)
        }
        // Sets samt Fortschritt für ein TCG (26.07., Set-Tracking auf der
        // Weboberfläche) - ownedCount kommt aus getSetProgress() (zählt
        // distinct cardId je Set), totalCards/name aus getCardSets()
        get("/api/sets") {
            val game = call.request.queryParameters["game"]
            val accountId = resolveAccountId(call)
            val progressBySetId = repository.getSetProgress(accountId).associateBy { it.setId }
            val sets = repository.getCardSets()
                .filter { game == null || it.game == game }
                .map { set ->
                    SetResponse(
                        id = set.id,
                        name = set.name,
                        totalCards = set.totalCards,
                        ownedCount = progressBySetId[set.id]?.ownedCount ?: 0
                    )
                }
            call.respond(sets)
        }
        // Voller Kartenkatalog eines Sets (26.07.) - für "Ganzes Set"/
        // "Fehlende" auf der Weboberfläche, zeigt auch nicht besessene Karten
        get("/api/catalog") {
            val setId = call.request.queryParameters["setId"]
            if (setId == null) {
                call.respond(HttpStatusCode.BadRequest, "setId fehlt")
                return@get
            }
            val cards = repository.getCatalogForSet(setId).map {
                CatalogCardResponse(
                    id = it.id,
                    number = it.number,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    marketPriceUsd = it.marketPriceUsd,
                    marketPriceEur = it.marketPriceEur,
                    marketPriceEurOptions = repository.decodeCardmarketOptions(it.marketPriceEurOptions),
                    marketPriceEurSelectedIndex = it.marketPriceEurSelectedIndex?.toInt(),
                    rarity = it.rarity,
                    variant = it.variant,
                    ruleSupertype = it.ruleSupertype,
                    ruleSubtypes = repository.decodeRuleSubtypes(it.ruleSubtypes)
                )
            }
            call.respond(cards)
        }
        // Manuelle Cardmarket-Preisauswahl (28.07., Nutzer-Vorgabe "let the
        // user decide") - siehe Kommentar bei CatalogCardResponse/
        // PortfolioRepository.selectCardmarketPriceOption()
        post("/api/cardmarketPrice/select") {
            val body = call.receive<SelectCardmarketPriceRequest>()
            repository.selectCardmarketPriceOption(body.cardId, body.index)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/cardmarketPrice/reset") {
            val body = call.receive<ResetCardmarketPriceRequest>()
            repository.clearCardmarketPriceSelection(body.cardId)
            call.respond(HttpStatusCode.OK)
        }
        // Manuelle Cardmarket-Zuordnung (04.08.) - siehe DTOs oben
        get("/api/cardmarketMapping/unmatched") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val accountId = resolveAccountId(call)
            // Eigener Preis (20.08.): selbst bepreiste Karten gelten nicht
            // mehr als "ohne Preis" - wie in der App
            val customPricedCardIds = repository.getAllCardsWithGame(accountId)
                .filter { it.customPriceEur != null }
                .mapNotNull { it.cardId }
                .toSet()
            val cards = repository.getOwnedCatalogCardsWithoutPrice(game, accountId)
                .filter { it.id !in customPricedCardIds }
                .map { CardmarketMappingCardResponse(id = it.id, name = it.name, imageUrl = it.imageUrl) }
            call.respond(cards)
        }
        get("/api/cardmarketMapping/mapped") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val mappings = repository.getCardmarketManualMappingsForGame(game)
            val catalogById = mappings.mapNotNull { m -> repository.getCatalogCard(m.cardId)?.let { m.cardId to it } }.toMap()
            val cards = mappings.mapNotNull { mapping ->
                val card = catalogById[mapping.cardId] ?: return@mapNotNull null
                CardmarketMappingCardResponse(
                    id = card.id,
                    name = card.name,
                    imageUrl = card.imageUrl,
                    marketPriceEur = card.marketPriceEur,
                    manualProductName = mapping.cardmarketProductName
                )
            }
            call.respond(cards)
        }
        get("/api/cardmarketMapping/search") {
            val game = call.request.queryParameters["game"]
            val query = call.request.queryParameters["query"]
            if (game == null || query.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "game und query erforderlich")
                return@get
            }
            val results = searchCardmarketProducts(game, query).map {
                CardmarketMappingSearchResponse(it.idProduct, it.name, it.priceEur)
            }
            call.respond(results)
        }
        // "Aus dem Set durchstöbern" (12.08., Web-Pendant zur App-Funktion
        // in CardmarketAssignDialog) - siehe findSetExpansionId()/
        // browseCardmarketProductsInSet() in CardmarketPriceSync.kt für die
        // Herleitung ohne Cardmarket-Erweiterungsnamen.
        get("/api/cardmarketMapping/browseSet") {
            val game = call.request.queryParameters["game"]
            val cardId = call.request.queryParameters["cardId"]
            if (game == null || cardId.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "game und cardId erforderlich")
                return@get
            }
            val setId = repository.getCatalogCard(cardId)?.setId
            if (setId == null) {
                call.respond(emptyList<CardmarketMappingSearchResponse>())
                return@get
            }
            val catalog = repository.getCatalogForGame(game)
            val results = browseCardmarketProductsInSet(game, setId, catalog).map {
                CardmarketMappingSearchResponse(it.idProduct, it.name, it.priceEur)
            }
            call.respond(results)
        }
        post("/api/cardmarketMapping/assign") {
            val body = call.receive<AssignCardmarketMappingRequest>()
            repository.setCardmarketManualMapping(body.cardId, body.productId, body.productName)
            // Sofort den Preis ziehen, statt auf den nächsten täglichen
            // Abgleich zu warten (04.08., Nutzer-Vorgabe)
            val game = repository.getGameForCard(body.cardId)
            val gameId = game?.let { cardmarketGameIdFor(it) }
            if (game != null && gameId != null) {
                try {
                    val priceGuide = fetchCardmarketPriceGuide(gameId)
                    val products = fetchCardmarketProductList(gameId)
                    repository.applyCardmarketCardPrices(game, priceGuide, products)
                } catch (e: Exception) {
                    // Best-effort - die Zuordnung selbst ist trotzdem gespeichert
                }
            }
            call.respond(HttpStatusCode.OK)
        }
        post("/api/cardmarketMapping/remove") {
            val body = call.receive<RemoveCardmarketMappingRequest>()
            repository.removeCardmarketManualMapping(body.cardId)
            call.respond(HttpStatusCode.OK)
        }
        // Sealed-Pendant zu den Routen oben (11.08.) - siehe DTOs oben.
        post("/api/sealedCardmarketPrice/select") {
            val body = call.receive<SelectSealedCardmarketPriceRequest>()
            repository.selectSealedCardmarketPriceOption(body.sealedId, body.index)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/sealedCardmarketPrice/reset") {
            val body = call.receive<ResetSealedCardmarketPriceRequest>()
            repository.clearSealedCardmarketPriceSelection(body.sealedId)
            call.respond(HttpStatusCode.OK)
        }
        get("/api/sealedCardmarketMapping/unmatched") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val accountId = resolveAccountId(call)
            // Eigener Preis (20.08.): siehe Karten-Pendant oben
            val customPricedCatalogIds = repository.getSealedProductsWithPrice(accountId)
                .filter { it.customPriceEur != null }
                .mapNotNull { it.catalogId }
                .toSet()
            val items = repository.getOwnedSealedProductsWithoutPrice(game, accountId)
                .filter { it.id !in customPricedCatalogIds }
                .map { SealedCardmarketMappingItemResponse(id = it.id, name = it.name, imageUrl = it.imageUrl) }
            call.respond(items)
        }
        get("/api/sealedCardmarketMapping/mapped") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val mappings = repository.getSealedCardmarketManualMappingsForGame(game)
            val catalogById = mappings.mapNotNull { m -> repository.getSealedCatalogEntry(m.sealedId)?.let { m.sealedId to it } }.toMap()
            val items = mappings.mapNotNull { mapping ->
                val entry = catalogById[mapping.sealedId] ?: return@mapNotNull null
                SealedCardmarketMappingItemResponse(
                    id = entry.id,
                    name = entry.name,
                    imageUrl = entry.imageUrl,
                    marketPriceEur = entry.marketPriceEur,
                    manualProductName = mapping.cardmarketProductName
                )
            }
            call.respond(items)
        }
        get("/api/sealedCardmarketMapping/search") {
            val game = call.request.queryParameters["game"]
            val query = call.request.queryParameters["query"]
            if (game == null || query.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "game und query erforderlich")
                return@get
            }
            val results = searchCardmarketProducts(game, query, singles = false).map {
                CardmarketMappingSearchResponse(it.idProduct, it.name, it.priceEur)
            }
            call.respond(results)
        }
        post("/api/sealedCardmarketMapping/assign") {
            val body = call.receive<AssignSealedCardmarketMappingRequest>()
            repository.setSealedCardmarketManualMapping(body.sealedId, body.productId, body.productName)
            // Sofort den Preis ziehen, statt auf den nächsten täglichen
            // Abgleich zu warten (04.08.-Muster von oben)
            val game = repository.getSealedCatalogEntry(body.sealedId)?.game
            val gameId = game?.let { cardmarketGameIdFor(it) }
            if (game != null && gameId != null) {
                try {
                    val priceGuide = fetchCardmarketPriceGuide(gameId)
                    val products = fetchCardmarketProductList(gameId, singles = false)
                    repository.applyCardmarketSealedPrices(game, priceGuide, products)
                } catch (e: Exception) {
                    // Best-effort - die Zuordnung selbst ist trotzdem gespeichert
                }
            }
            call.respond(HttpStatusCode.OK)
        }
        post("/api/sealedCardmarketMapping/remove") {
            val body = call.receive<RemoveSealedCardmarketMappingRequest>()
            repository.removeSealedCardmarketManualMapping(body.sealedId)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/collection/changeVariant") {
            val body = call.receive<ChangeCardVariantRequest>()
            repository.changeCardVariant(resolveAccountId(call), body.cardId, body.isHolo, body.newCardId)
            call.respond(HttpStatusCode.OK)
        }
        // Katalogsuche fürs "Karte hinzufügen" (26.07.)
        get("/api/searchCatalog") {
            val game = call.request.queryParameters["game"]
            val query = call.request.queryParameters["query"]
            if (game == null || query.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "game und query erforderlich")
                return@get
            }
            val results = repository.searchCatalog(game, query).map {
                SearchCatalogResponse(
                    id = it.id,
                    name = it.name,
                    number = it.number,
                    setId = it.setId,
                    setName = it.setName,
                    imageUrl = it.imageUrl,
                    marketPriceUsd = it.marketPriceUsd
                )
            }
            call.respond(results)
        }
        // Direktes Hinzufügen einer Karte (26.07.) - schreibt sofort in die
        // Server-DB, genau wie addCard() das für die App tut (inkl.
        // automatischem updatedAt fürs Sync-Protokoll). Bewusst kein Umweg
        // über den Sync-Mechanismus - der ist für App<->Server gedacht, hier
        // ist der Server selbst die Quelle der Änderung.
        post("/api/collection/add") {
            val body = call.receive<AddCardRequest>()
            repository.addCard(
                accountId = resolveAccountId(call),
                name = body.name,
                price = 0.0,
                quantity = body.quantity,
                isHolo = body.isHolo,
                imageUrl = body.imageUrl,
                cardId = body.cardId
            )
            call.respond(HttpStatusCode.OK)
        }
        // Mehrfach-Löschen eigener Karten (03.08., Nutzer-Vorgabe
        // "Massenauswahl" auf der Weboberfläche) - Web-Pendant zur
        // Mehrfachauswahl in der App, siehe deleteCards() in
        // PortfolioRepository.kt
        post("/api/collection/delete") {
            val body = call.receive<IdListRequest>()
            repository.deleteCards(body.ids)
            call.respond(HttpStatusCode.OK)
        }
        // Kartendetail-Bearbeitung (09.08., Nutzer-Vorgabe "Feature-Parität
        // App <-> Web") - bisher konnte die Weboberfläche eine Karte nur
        // anzeigen, hinzufügen oder löschen, nicht aber Anzahl/Holo/Kaufpreis
        // NACHTRÄGLICH ändern (die App kann das schon länger, siehe
        // App.kt-Kartendetailansicht). Ruft dieselbe Repository-Funktion wie
        // dort auf.
        post("/api/collection/update") {
            val body = call.receive<UpdateCardRequest>()
            repository.updateCard(
                id = body.id,
                quantity = body.quantity,
                isHolo = body.isHolo,
                purchasePrice = body.purchasePrice
            )
            call.respond(HttpStatusCode.OK)
        }
        // Eigener Preis (20.08., Nutzer-Fund "beim Server kann man einer
        // Karte noch keinen eigenen Preis geben. Überprüfe, dass der Server
        // die gleichen Features hat wie die App") - dieselben Repository-
        // Funktionen wie die App-Detailansicht bzw. das Vault-Formular
        post("/api/collection/customPrice") {
            val body = call.receive<CustomPriceRequest>()
            repository.setCustomPrice(body.id, body.priceEur, body.inTotal, body.inGameTotal)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/sealed/customPrice") {
            val body = call.receive<CustomPriceRequest>()
            repository.setSealedCustomPrice(body.id, body.priceEur, body.inTotal, body.inGameTotal)
            call.respond(HttpStatusCode.OK)
        }
        // Sprachbewusste Kartenbilder (07.09., Nutzer-Design 31.08. "Server/
        // Weboberfläche bekommen alles gleichwertig") - dieselben Repository-
        // Funktionen wie die App-Detailansicht bzw. die App-Einstellungen;
        // der Modus synct per LWW-Zeitstempel mit der App (siehe
        // SyncPayload.cardImageLanguageMode).
        post("/api/collection/imageLanguage") {
            val body = call.receive<ImageLanguageRequest>()
            val language = body.language?.takeIf { it == CARD_IMAGE_LANGUAGE_DE || it == CARD_IMAGE_LANGUAGE_EN }
            repository.setCardImageLanguage(body.id, language)
            call.respond(HttpStatusCode.OK)
        }
        get("/api/cardImageLanguageMode") {
            call.respond(CardImageLanguageModeResponse(repository.getCardImageLanguageMode()))
        }
        post("/api/cardImageLanguageMode") {
            val body = call.receive<CardImageLanguageModeResponse>()
            val mode = body.mode.takeIf {
                it == CARD_IMAGE_LANGUAGE_MODE_AS_SCANNED || it == CARD_IMAGE_LANGUAGE_DE || it == CARD_IMAGE_LANGUAGE_EN
            } ?: CARD_IMAGE_LANGUAGE_MODE_AS_SCANNED
            repository.setCardImageLanguageMode(mode)
            call.respond(CardImageLanguageModeResponse(mode))
        }
        get("/api/sealedCatalog") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val results = repository.getSealedCatalog().filter { it.game == game }.map {
                SealedCatalogResponse(
                    id = it.id,
                    name = it.name,
                    category = it.category,
                    imageUrl = it.imageUrl,
                    marketPriceUsd = it.marketPriceUsd,
                    marketPriceEur = it.marketPriceEur
                )
            }
            call.respond(results)
        }
        post("/api/sealed/add") {
            val body = call.receive<AddSealedRequest>()
            repository.addSealedProduct(
                accountId = resolveAccountId(call),
                name = body.name,
                category = body.category,
                game = body.game,
                quantity = body.quantity,
                isSealed = body.isSealed,
                purchasePrice = 0.0,
                imageUrl = body.imageUrl,
                catalogId = body.catalogId
            )
            call.respond(HttpStatusCode.OK)
        }
        get("/api/sealed") {
            val products = repository.getSealedProductsWithPrice(resolveAccountId(call)).map {
                SealedProductResponse(
                    id = it.id,
                    name = it.name,
                    category = it.category,
                    quantity = it.quantity,
                    isSealed = it.isSealed == 1L,
                    game = it.game,
                    marketPriceUsd = it.marketPriceUsd,
                    marketPriceEur = it.marketPriceEur,
                    catalogId = it.catalogId,
                    marketPriceEurOptions = repository.decodeCardmarketOptions(it.marketPriceEurOptions),
                    imageUrl = it.imageUrl,
                    purchasePrice = it.purchasePrice,
                    customPriceEur = it.customPriceEur,
                    customPriceInTotal = it.customPriceInTotal,
                    customPriceInGameTotal = it.customPriceInGameTotal
                )
            }
            call.respond(products)
        }
        // Vault-Bearbeitung/-Löschen (09.08., Nutzer-Vorgabe "Feature-Parität
        // App <-> Web") - bisher konnte die Weboberfläche Vault-Produkte nur
        // anzeigen/hinzufügen, nicht aber nachträglich bearbeiten oder
        // löschen (die App kann das schon länger, siehe VaultScreen.kt).
        // Rufen dieselben Repository-Funktionen wie dort auf.
        post("/api/sealed/update") {
            val body = call.receive<UpdateSealedRequest>()
            repository.updateSealedProduct(
                id = body.id,
                name = body.name,
                category = body.category,
                quantity = body.quantity,
                isSealed = body.isSealed,
                purchasePrice = body.purchasePrice,
                imageUrl = body.imageUrl
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/api/sealed/delete") {
            val body = call.receive<IdListRequest>()
            body.ids.forEach { repository.deleteSealedProduct(it) }
            call.respond(HttpStatusCode.OK)
        }
        // Wunschlisten (27.07.) - siehe Kommentar bei WishlistResponse oben
        get("/api/wishlists") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val accountId = resolveAccountId(call)
            val items = repository.getWishlistItemsForGame(accountId, game)
            val results = repository.getWishlistsForGame(accountId, game).map { w ->
                WishlistResponse(
                    id = w.id,
                    name = w.name,
                    game = w.game,
                    itemCount = items.count { it.wishlistId == w.id }.toLong()
                )
            }
            call.respond(results)
        }
        get("/api/wishlistItems") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val results = repository.getWishlistItemsForGame(resolveAccountId(call), game).map {
                WishlistItemResponse(
                    id = it.id,
                    wishlistId = it.wishlistId,
                    cardId = it.cardId,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    setId = it.setId,
                    marketPriceUsd = it.marketPriceUsd,
                    marketPriceEur = it.marketPriceEur,
                    rarity = it.rarity
                )
            }
            call.respond(results)
        }
        post("/api/wishlists") {
            val body = call.receive<CreateWishlistRequest>()
            val id = repository.addWishlist(resolveAccountId(call), body.name, body.game)
            call.respond(WishlistResponse(id = id, name = body.name, game = body.game, itemCount = 0))
        }
        // Umbenennen (20.08., Parität zur App vom 19.08. - dort über den
        // Stift-Ball bei genau einer ausgewählten Liste)
        post("/api/wishlists/rename") {
            val body = call.receive<RenameWishlistRequest>()
            if (body.name.isNotBlank()) repository.renameWishlist(body.id, body.name)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/wishlists/delete") {
            val body = call.receive<IdListRequest>()
            repository.deleteWishlists(body.ids)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/wishlistItems/add") {
            val body = call.receive<AddWishlistItemsRequest>()
            repository.addWishlistItems(
                body.wishlistId,
                body.cards.map { PortfolioRepository.WishlistCardToAdd(it.cardId, it.name, it.imageUrl) }
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/api/wishlistItems/delete") {
            val body = call.receive<IdListRequest>()
            repository.deleteWishlistItems(body.ids)
            call.respond(HttpStatusCode.OK)
        }
        // Sealed-Wantslisten (28.08., Parität zur App vom 25.08.) - gleiche
        // Struktur wie die Karten-Wantslisten-Routen oben, nur auf den
        // Sealed-Katalog bezogen; der Preis-Alarm läuft über die App-seitig
        // eingeführte setSealedWishlistAlarm-Semantik (null = entfernen)
        get("/api/sealedWishlists") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val results = repository.getSealedWishlists(game, resolveAccountId(call)).map { w ->
                SealedWishlistResponse(id = w.id, name = w.name, game = w.game, itemCount = w.itemCount)
            }
            call.respond(results)
        }
        get("/api/sealedWishlistItems") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val results = repository.getSealedWishlist(game, resolveAccountId(call)).map {
                SealedWishlistItemResponse(
                    id = it.id,
                    wishlistId = it.wishlistId,
                    catalogId = it.catalogId,
                    name = it.name,
                    category = it.category,
                    imageUrl = it.imageUrl,
                    marketPriceEur = it.marketPriceEur,
                    priceAlarmEur = it.priceAlarmEur
                )
            }
            call.respond(results)
        }
        post("/api/sealedWishlists") {
            val body = call.receive<CreateSealedWishlistRequest>()
            val id = repository.addSealedWishlist(body.name, body.game, resolveAccountId(call))
            call.respond(SealedWishlistResponse(id = id, name = body.name, game = body.game, itemCount = 0))
        }
        post("/api/sealedWishlists/delete") {
            val body = call.receive<IdRequest>()
            repository.removeSealedWishlist(body.id)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/sealedWishlistItems/add") {
            val body = call.receive<AddSealedWishlistItemRequest>()
            repository.addSealedWishlistItem(body.game, body.catalogId, resolveAccountId(call), body.wishlistId)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/sealedWishlistItems/delete") {
            val body = call.receive<IdRequest>()
            repository.removeSealedWishlistItem(body.id)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/sealedWishlistItems/alarm") {
            val body = call.receive<SealedWishlistAlarmRequest>()
            repository.setSealedWishlistAlarm(body.id, body.priceEur)
            call.respond(HttpStatusCode.OK)
        }
        // Ausgelöste Preis-Alarme (Schwelle erreicht/unterschritten) - die
        // Weboberfläche zeigt sie nach dem Laden als Hinweis an, analog zum
        // Alarm-Dialog beim App-Start
        get("/api/sealedWishlistAlarms") {
            val results = repository.getTriggeredSealedAlarms(resolveAccountId(call)).map {
                TriggeredSealedAlarmResponse(
                    game = it.game,
                    name = it.name,
                    priceAlarmEur = it.priceAlarmEur,
                    marketPriceEur = it.marketPriceEur
                )
            }
            call.respond(results)
        }
        // Binder (31.07.) - siehe Kommentar bei BinderResponse oben
        get("/api/binders") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val accountId = resolveAccountId(call)
            val items = repository.getBinderItemsForGame(accountId, game)
            val results = repository.getBindersForGame(accountId, game).map { b ->
                BinderResponse(
                    id = b.id,
                    name = b.name,
                    game = b.game,
                    itemCount = items.count { it.binderId == b.id }.toLong(),
                    pageSize = b.pageSize,
                    color = b.color,
                    coverImageUrl = b.coverImageUrl?.takeIf { it.startsWith("/images/custom/") }
                )
            }
            call.respond(results)
        }
        get("/api/binderItems") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val results = repository.getBinderItemsForGame(resolveAccountId(call), game).map {
                BinderItemResponse(
                    id = it.id,
                    binderId = it.binderId,
                    cardId = it.cardId,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    setId = it.setId,
                    marketPriceUsd = it.marketPriceUsd,
                    marketPriceEur = it.marketPriceEur,
                    rarity = it.rarity,
                    position = it.position
                )
            }
            call.respond(results)
        }
        post("/api/binders") {
            val body = call.receive<CreateBinderRequest>()
            val id = repository.addBinder(resolveAccountId(call), body.name, body.game, body.pageSize)
            call.respond(BinderResponse(id = id, name = body.name, game = body.game, itemCount = 0, pageSize = body.pageSize))
        }
        post("/api/binders/delete") {
            val body = call.receive<IdListRequest>()
            repository.deleteBinders(body.ids)
            call.respond(HttpStatusCode.OK)
        }
        // Binder umbenennen (03.08., Nutzer-Vorgabe) - Web-Pendant zu
        // renameBinder() in der App/PortfolioRepository.kt
        // ---------- Foto-Sync (25.08.) ----------
        // Eigene Fotos (Karten/Vault/Binder-Deckel) zwischen App und Server
        // abgleichen - Gegenstück zu syncCustomPhotos() in App.kt. Dateien
        // liegen im bestehenden customPhotosDir und werden über die schon
        // vorhandene /images/custom-Static-Route auch der Weboberfläche
        // serviert. Schlüssel siehe SyncClient.kt-Kommentar.
        fun photoSyncFileName(kind: String, key: String): String {
            val digest = java.security.MessageDigest.getInstance("MD5")
                .digest("$kind|$key".toByteArray())
            return "sync-$kind-" + digest.joinToString("") { b ->
                (b.toInt() and 0xff).toString(16).padStart(2, '0')
            } + ".jpg"
        }
        // Halb übertragene Fotos (28.08., Nutzer-Fund "Binder-Foto nur halb
        // zu sehen"): ein abgerissener Upload darf nie als fertige Datei
        // enden. JPEG beginnt mit FFD8 und endet mit FFD9 - fehlt der
        // Endmarker, ist die Datei unvollständig.
        fun isCompleteJpeg(bytes: ByteArray): Boolean {
            if (bytes.size < 4) return false
            if (bytes[0] != 0xFF.toByte() || bytes[1] != 0xD8.toByte()) return false
            // EOI in den letzten Bytes suchen (manche Encoder hängen wenige
            // Füllbytes an, deshalb nicht stur nur die letzten zwei prüfen)
            for (i in bytes.size - 2 downTo maxOf(0, bytes.size - 32)) {
                if (bytes[i] == 0xFF.toByte() && bytes[i + 1] == 0xD9.toByte()) return true
            }
            return false
        }
        fun isCompleteJpegFile(file: File): Boolean {
            if (!file.exists() || file.length() < 4) return false
            return java.io.RandomAccessFile(file, "r").use { raf ->
                val head = ByteArray(2)
                raf.readFully(head)
                if (head[0] != 0xFF.toByte() || head[1] != 0xD8.toByte()) return@use false
                val tailLen = minOf(32L, raf.length()).toInt()
                raf.seek(raf.length() - tailLen)
                val tail = ByteArray(tailLen)
                raf.readFully(tail)
                for (i in tailLen - 2 downTo 0) {
                    if (tail[i] == 0xFF.toByte() && tail[i + 1] == 0xD9.toByte()) return@use true
                }
                false
            }
        }
        fun resolvePhotoUrl(kind: String, key: String): String? = when (kind) {
            "card" -> repository.getCustomCardPhotoUrl(key)
            "sealed" -> {
                val accountUid = key.substringBefore("|", "")
                val itemKey = key.substringAfter("|", "")
                repository.getAccounts().firstOrNull { it.uid == accountUid }?.let { acc ->
                    repository.getAllSealedProductsRaw(acc.id).firstOrNull { p ->
                        repository.sealedSyncKey(p.catalogId, p.isSealed, p.name, p.category, p.game) == itemKey
                    }?.imageUrl
                }
            }
            "binder" -> repository.getAccounts().firstNotNullOfOrNull { acc ->
                repository.getAllBindersRaw(acc.id).firstOrNull { it.uid == key }?.coverImageUrl
            }
            else -> null
        }
        // Selbstheilung (28.08.): Einträge, deren Datei fehlt oder
        // unvollständig ist (z.B. ein früher halb angekommener Upload),
        // tauchen im Inventar NICHT auf - die App sieht dann "Server hat
        // kein Foto" und lädt es beim nächsten Sync von selbst neu hoch.
        fun photoFileHealthy(url: String?): Boolean {
            val f = url?.takeIf { it.startsWith("/images/custom/") }
                ?.let { File(customPhotosDir, it.removePrefix("/images/custom/")) }
            return f != null && isCompleteJpegFile(f)
        }
        get("/api/photoSync/list") {
            val entries = mutableListOf<PhotoSyncEntryResponse>()
            repository.getAllCustomCardPhotos()
                .filter { it.imageUrl.startsWith("/images/custom/") && photoFileHealthy(it.imageUrl) }
                .forEach { entries += PhotoSyncEntryResponse("card", it.cardId, it.updatedAt) }
            repository.getAccounts().forEach { acc ->
                repository.getAllSealedProductsRaw(acc.id).forEach { p ->
                    if (p.imageUrl?.startsWith("/images/custom/") == true && photoFileHealthy(p.imageUrl)) {
                        val key = acc.uid + "|" + repository.sealedSyncKey(p.catalogId, p.isSealed, p.name, p.category, p.game)
                        entries += PhotoSyncEntryResponse("sealed", key, p.updatedAt)
                    }
                }
                repository.getAllBindersRaw(acc.id).forEach { b ->
                    if (b.coverImageUrl?.startsWith("/images/custom/") == true && b.uid.isNotEmpty() && photoFileHealthy(b.coverImageUrl)) {
                        entries += PhotoSyncEntryResponse("binder", b.uid, b.coverUpdatedAt)
                    }
                }
            }
            call.respond(entries)
        }
        get("/api/photoSync/file") {
            val kind = call.request.queryParameters["kind"]
            val key = call.request.queryParameters["key"]
            if (kind == null || key == null) {
                call.respond(HttpStatusCode.BadRequest, "kind/key fehlt")
                return@get
            }
            val url = resolvePhotoUrl(kind, key)
            val file = url?.takeIf { it.startsWith("/images/custom/") }
                ?.let { File(customPhotosDir, it.removePrefix("/images/custom/")) }
            if (file == null || !file.exists()) {
                call.respond(HttpStatusCode.NotFound, "kein Foto")
                return@get
            }
            call.respondBytes(file.readBytes(), ContentType.Image.JPEG)
        }
        post("/api/photoSync/upload") {
            val kind = call.request.queryParameters["kind"]
            val key = call.request.queryParameters["key"]
            val updatedAt = call.request.queryParameters["updatedAt"]?.toLongOrNull()
            if (kind == null || key == null || updatedAt == null) {
                call.respond(HttpStatusCode.BadRequest, "kind/key/updatedAt fehlt")
                return@post
            }
            val bytes = call.receive<ByteArray>()
            // Abgerissene Übertragung abweisen (28.08.): Länge muss zum
            // Content-Length passen UND das JPEG muss vollständig sein -
            // sonst 400, die App versucht es beim nächsten Sync erneut.
            val expected = call.request.headers[HttpHeaders.ContentLength]?.toLongOrNull()
            if ((expected != null && bytes.size.toLong() != expected) || !isCompleteJpeg(bytes)) {
                call.respond(HttpStatusCode.BadRequest, "Foto unvollständig übertragen")
                return@post
            }
            val fileName = photoSyncFileName(kind, key)
            // Atomar schreiben: erst Temp-Datei, dann Move - die Web-
            // oberfläche sieht so nie eine halb geschriebene Datei
            val target = File(customPhotosDir, fileName)
            val tmp = File(customPhotosDir, "$fileName.tmp")
            tmp.writeBytes(bytes)
            java.nio.file.Files.move(
                tmp.toPath(), target.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                java.nio.file.StandardCopyOption.ATOMIC_MOVE
            )
            val url = "/images/custom/$fileName"
            val applied = when (kind) {
                "card" -> {
                    repository.setCustomCardPhotoSynced(key, url, updatedAt)
                    true
                }
                "sealed" -> {
                    val accountUid = key.substringBefore("|", "")
                    val itemKey = key.substringAfter("|", "")
                    repository.getAccounts().firstOrNull { it.uid == accountUid }?.let { acc ->
                        repository.getAllSealedProductsRaw(acc.id).firstOrNull { p ->
                            repository.sealedSyncKey(p.catalogId, p.isSealed, p.name, p.category, p.game) == itemKey
                        }?.also { repository.setSealedProductImageSynced(it.id, url, updatedAt) }
                    } != null
                }
                "binder" -> {
                    repository.getAccounts().firstNotNullOfOrNull { acc ->
                        repository.getAllBindersRaw(acc.id).firstOrNull { it.uid == key }
                    }?.also { repository.setBinderCoverImageSynced(it.id, url, updatedAt) } != null
                }
                else -> false
            }
            if (applied) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound, "Ziel nicht gefunden")
        }

        // Binder-Farbe (25.08., Nutzer-Vorgabe "echte Binder") - server-eigene
        // Deko, siehe BinderResponse.color
        // "Ein Fach nach vorn schieben" (28.08., Parität zum Doppel-Tipp in
        // BinderScreen.kt vom 09.08.) - schiebt die Karte an atPosition und
        // alle folgenden um ein Fach weiter, es entsteht eine Lücke
        post("/api/binderItems/shiftForward") {
            val body = call.receive<ShiftBinderItemsRequest>()
            repository.shiftBinderItemsForward(body.binderId, body.atPosition)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/binders/color") {
            val body = call.receive<BinderColorRequest>()
            repository.setBinderColor(body.id, body.color)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/binders/rename") {
            val body = call.receive<RenameBinderRequest>()
            repository.renameBinder(body.id, body.name)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/binderItems/add") {
            val body = call.receive<AddBinderItemsRequest>()
            repository.addBinderItems(
                body.binderId,
                body.cards.map { PortfolioRepository.BinderCardToAdd(it.cardId, it.name, it.imageUrl) }
            )
            call.respond(HttpStatusCode.OK)
        }
        post("/api/binderItems/delete") {
            val body = call.receive<IdListRequest>()
            repository.deleteBinderItems(body.ids)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/binderItems/swap") {
            val body = call.receive<SwapBinderItemsRequest>()
            repository.swapBinderItemPositions(body.itemId1, body.itemId2)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/binderItems/move") {
            val body = call.receive<MoveBinderItemRequest>()
            repository.moveBinderItemToPosition(body.itemId, body.newPosition)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/binderItems/insertPage") {
            val body = call.receive<InsertBinderPageRequest>()
            repository.insertBinderPage(body.binderId, body.atPage, body.pageSize)
            call.respond(HttpStatusCode.OK)
        }
        // Vollständiges JSON-Backup (10.08.) - siehe Kommentar bei
        // BackupImportResponse oben. Export liefert das rohe JSON von
        // exportData() direkt zurück (kein weiteres Verpacken/Serialisieren,
        // sonst müsste der Client es erst wieder entpacken); Import erwartet
        // umgekehrt den rohen Dateiinhalt als Body (receiveText(), keine
        // typisierte Request-Klasse - der Body IST bereits das Backup-JSON).
        get("/api/backup/export") {
            val accountId = resolveAccountId(call)
            // Backup v3 (28.08.): eigene Fotos aus customPhotosDir einbetten
            call.respondText(
                repository.exportData(accountId) { _, _, imageUrl ->
                    imageUrl.takeIf { it.startsWith("/images/custom/") }
                        ?.let { File(customPhotosDir, it.removePrefix("/images/custom/")) }
                        ?.takeIf { it.exists() }?.readBytes()
                },
                ContentType.Application.Json
            )
        }
        post("/api/backup/import") {
            val accountId = resolveAccountId(call)
            val json = call.receiveText()
            val summary = try {
                // photoSaver (Backup v3): gleiche Dateinamen wie der Foto-
                // Sync-Kanal (photoSyncFileName; sealed dort mit Account-
                // Präfix), atomar geschrieben; vorhandene eigene Fotos werden
                // nicht überschrieben (add-only), kaputte JPEGs abgewiesen
                repository.importData(accountId, json) { kind, key, bytes, currentUrl ->
                    if (currentUrl != null && currentUrl.startsWith("/images/custom/")) null
                    else if (!isCompleteJpeg(bytes)) null
                    else {
                        val accountUid = repository.getAccounts().firstOrNull { it.id == accountId }?.uid.orEmpty()
                        val fileName = photoSyncFileName(kind, if (kind == "sealed") "$accountUid|$key" else key)
                        val tmp = File(customPhotosDir, "$fileName.tmp")
                        tmp.writeBytes(bytes)
                        java.nio.file.Files.move(
                            tmp.toPath(), File(customPhotosDir, fileName).toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                            java.nio.file.StandardCopyOption.ATOMIC_MOVE
                        )
                        "/images/custom/$fileName"
                    }
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Datei konnte nicht gelesen werden")
                return@post
            }
            call.respond(BackupImportResponse(
                summary.cardsAdded, summary.sealedAdded,
                summary.bindersAdded, summary.binderItemsAdded,
                summary.wishlistsAdded, summary.wishlistItemsAdded,
                summary.decksAdded, summary.deckCardsAdded,
                summary.photosRestored
            ))
        }
        // Eigenes Foto hochladen (10.08., Feature-Parität App <-> Web) - die
        // App nimmt das Foto per Kamera auf und speichert es lokal auf dem
        // Gerät (siehe PlainPhotoCaptureOverlay/setCustomCardPhoto in App.kt),
        // die Weboberfläche schickt stattdessen eine Datei per Multipart-
        // Upload. cardId kommt als eigenes Formularfeld mit (nicht als
        // Query-Parameter, damit ein einziger Multipart-Request reicht).
        // Räumt eine vorher gesetzte eigene Datei mit auf, wenn ersetzt wird
        // (nur, wenn sie tatsächlich von UNS gehostet wurde - ein Katalogbild
        // o.ä. würde nie hier landen, siehe COALESCE in Portfolio.sq).
        post("/api/customPhoto/upload") {
            var cardId: String? = null
            var savedUrl: String? = null
            val multipart = call.receiveMultipart()
            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name == "cardId") cardId = part.value
                    }
                    is PartData.FileItem -> {
                        val id = cardId
                        if (id != null && savedUrl == null) {
                            val ext = part.originalFileName
                                ?.substringAfterLast('.', "jpg")
                                ?.lowercase()
                                ?.filter { it.isLetterOrDigit() }
                                ?.ifBlank { "jpg" } ?: "jpg"
                            val safeId = id.filter { it.isLetterOrDigit() || it == '-' || it == '_' }.take(80)
                            val filename = "$safeId-${System.currentTimeMillis()}.$ext"
                            val file = File(customPhotosDir, filename)
                            file.writeBytes(part.provider().readBytes())
                            savedUrl = "/images/custom/$filename"
                        }
                    }
                    else -> {}
                }
                part.dispose()
            }
            val id = cardId
            val url = savedUrl
            if (id == null || url == null) {
                call.respond(HttpStatusCode.BadRequest, "cardId oder Datei fehlt")
                return@post
            }
            val old = repository.getCustomCardPhotoUrl(id)
            repository.setCustomCardPhoto(id, url)
            if (old != null && old.startsWith("/images/custom/")) {
                File(customPhotosDir, old.removePrefix("/images/custom/")).let { if (it.exists()) it.delete() }
            }
            call.respond(CustomPhotoUploadResponse(url))
        }
        post("/api/customPhoto/clear") {
            val body = call.receive<ClearCustomPhotoRequest>()
            val old = repository.getCustomCardPhotoUrl(body.cardId)
            repository.clearCustomCardPhoto(body.cardId)
            if (old != null && old.startsWith("/images/custom/")) {
                File(customPhotosDir, old.removePrefix("/images/custom/")).let { if (it.exists()) it.delete() }
            }
            call.respond(HttpStatusCode.OK)
        }
        // Eigenes Foto für Sealed-/Vault-Produkte (12.08.) - Pendant zu
        // /api/customPhoto/upload, nur mit sealedId statt cardId als
        // Formularfeld (SealedProductEntity.id ist ein Long, kein String).
        post("/api/sealedPhoto/upload") {
            var sealedId: Long? = null
            var savedUrl: String? = null
            val multipart = call.receiveMultipart()
            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name == "sealedId") sealedId = part.value.toLongOrNull()
                    }
                    is PartData.FileItem -> {
                        val id = sealedId
                        if (id != null && savedUrl == null) {
                            val ext = part.originalFileName
                                ?.substringAfterLast('.', "jpg")
                                ?.lowercase()
                                ?.filter { it.isLetterOrDigit() }
                                ?.ifBlank { "jpg" } ?: "jpg"
                            val filename = "sealed-$id-${System.currentTimeMillis()}.$ext"
                            val file = File(customPhotosDir, filename)
                            file.writeBytes(part.provider().readBytes())
                            savedUrl = "/images/custom/$filename"
                        }
                    }
                    else -> {}
                }
                part.dispose()
            }
            val id = sealedId
            val url = savedUrl
            if (id == null || url == null) {
                call.respond(HttpStatusCode.BadRequest, "sealedId oder Datei fehlt")
                return@post
            }
            val old = repository.getSealedProductById(id)?.imageUrl
            repository.setSealedProductImageUrl(id, url)
            if (old != null && old.startsWith("/images/custom/")) {
                File(customPhotosDir, old.removePrefix("/images/custom/")).let { if (it.exists()) it.delete() }
            }
            call.respond(SealedPhotoUploadResponse(url))
        }
        // "Zurücksetzen" (12.08.) - anders als bei Karten (dort einfach die
        // CustomCardPhotoEntity-Zeile löschen, COALESCE übernimmt dann wieder
        // das Katalogbild) gibt es bei Sealed-Produkten kein automatisches
        // Zurückfallen auf SealedCatalogEntity.imageUrl (siehe
        // selectSealedProductsWithPrice in Portfolio.sq), das muss hier
        // explizit nachgebaut werden.
        post("/api/sealedPhoto/clear") {
            val body = call.receive<ClearSealedPhotoRequest>()
            val product = repository.getSealedProductById(body.sealedId)
            val old = product?.imageUrl
            val catalogImageUrl = product?.catalogId?.let { repository.getSealedCatalogEntry(it)?.imageUrl }
            repository.setSealedProductImageUrl(body.sealedId, catalogImageUrl)
            if (old != null && old.startsWith("/images/custom/")) {
                File(customPhotosDir, old.removePrefix("/images/custom/")).let { if (it.exists()) it.delete() }
            }
            call.respond(SealedPhotoUploadResponse(catalogImageUrl ?: ""))
        }
        // Decks (02.08., Phase 3 Deckbuilding) - siehe Kommentar bei DeckResponse
        get("/api/decks") {
            val game = call.request.queryParameters["game"]
            if (game == null) {
                call.respond(HttpStatusCode.BadRequest, "game fehlt")
                return@get
            }
            val accountId = resolveAccountId(call)
            val results = repository.getDecksForGame(accountId, game).map { d ->
                val cardCount = repository.getDeckCards(d.id).sumOf { it.quantity }
                DeckResponse(id = d.id, name = d.name, game = d.game, cardCount = cardCount)
            }
            call.respond(results)
        }
        get("/api/deckCards") {
            val deckId = call.request.queryParameters["deckId"]?.toLongOrNull()
            if (deckId == null) {
                call.respond(HttpStatusCode.BadRequest, "deckId fehlt")
                return@get
            }
            val results = repository.getDeckCards(deckId).map {
                DeckCardResponse(
                    id = it.id,
                    deckId = it.deckId,
                    cardId = it.cardId,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    setId = it.setId,
                    number = it.number,
                    rarity = it.rarity,
                    ruleSupertype = it.ruleSupertype,
                    ruleSubtypes = repository.decodeRuleSubtypes(it.ruleSubtypes),
                    quantity = it.quantity
                )
            }
            call.respond(results)
        }
        post("/api/decks") {
            val body = call.receive<CreateDeckRequest>()
            val id = repository.addDeck(resolveAccountId(call), body.name, body.game)
            call.respond(DeckResponse(id = id, name = body.name, game = body.game, cardCount = 0))
        }
        post("/api/decks/delete") {
            val body = call.receive<IdListRequest>()
            repository.deleteDecks(body.ids)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/deckCards/add") {
            val body = call.receive<AddDeckCardRequest>()
            repository.addCardToDeck(body.deckId, body.cardId, body.quantity)
            call.respond(HttpStatusCode.OK)
        }
        post("/api/deckCards/setQuantity") {
            val body = call.receive<SetDeckCardQuantityRequest>()
            repository.setDeckCardQuantity(body.deckCardId, body.quantity)
            call.respond(HttpStatusCode.OK)
        }
        // Regelprüfung (02.08.) - aktuell nur Pokémon, siehe
        // PortfolioRepository.validateDeck()/DeckValidationResponse-Kommentar
        get("/api/decks/validate") {
            val deckId = call.request.queryParameters["deckId"]?.toLongOrNull()
            val game = call.request.queryParameters["game"]
            if (deckId == null || game == null) {
                call.respond(HttpStatusCode.BadRequest, "deckId/game fehlt")
                return@get
            }
            val result = repository.validateDeck(deckId, game)
            if (result == null) {
                call.respond(DeckValidationResponse(available = false))
            } else {
                call.respond(
                    DeckValidationResponse(
                        available = true,
                        violations = result.violations,
                        cardsWithoutRuleData = result.cardsWithoutRuleData,
                        totalDistinctCards = result.totalDistinctCards
                    )
                )
            }
        }
        // Umrechnungsfaktor für den Werte-Tab (26.07.) - eigene, unabhängige
        // Kalibrierung des Servers, siehe Kommentar bei MarketFactorResponse.
        // Default 0.8f identisch zum App-Default (App.kt), aber komplett
        // getrennt gespeichert.
        get("/api/marketFactor") {
            val factor = repository.getSetting("marketFactor")?.toFloatOrNull() ?: 0.8f
            call.respond(MarketFactorResponse(factor))
        }
        post("/api/marketFactor") {
            val body = call.receive<MarketFactorResponse>()
            val clamped = body.factor.coerceIn(0.4f, 1.0f)
            repository.setSetting("marketFactor", clamped.toString())
            // Zeitstempel fürs Sync-Protokoll (26.07., Nutzer-Vorgabe: App und
            // Server sollen denselben Faktor zeigen) - ohne den würde die App
            // beim nächsten Sync nicht wissen, dass DIESE Änderung die jüngere
            // ist, siehe PortfolioRepository.importSyncData()
            repository.setSetting("marketFactorUpdatedAt", System.currentTimeMillis().toString())
            call.respond(MarketFactorResponse(clamped))
        }
        // TCGs pro Account ausblenden (17.08., Nutzer-Vorgabe) - gespeichert
        // als kommaseparierte Codes-Liste im bestehenden Settings-Store, per
        // Account-Id im SCHLÜSSEL namespaced ("hiddenGames.<id>") statt einer
        // neuen Tabelle/Spalte - kein Schema, keine Migration, und der
        // geforderte Default ("standardmäßig alles eingeblendet") ergibt sich
        // von selbst: fehlender Eintrag = leere Liste = nichts ausgeblendet.
        // Bewusst NUR eine Server-/Web-Einstellung, kein Sync-Feld - die App
        // hat ihre eigene TCG-Auswahl-Logik (Primär-TCG/Premium-Sperre) und
        // war vom Nutzer hier nicht gemeint.
        get("/api/hiddenGames") {
            val accountId = resolveAccountId(call)
            val stored = repository.getSetting("hiddenGames.$accountId") ?: ""
            call.respond(HiddenGamesResponse(stored.split(",").filter { it.isNotBlank() }))
        }
        post("/api/hiddenGames") {
            val accountId = resolveAccountId(call)
            val body = call.receive<HiddenGamesResponse>()
            repository.setSetting("hiddenGames.$accountId", body.hidden.joinToString(","))
            call.respond(HiddenGamesResponse(body.hidden))
        }
        // Echte Zwei-Wege-Sync (2026-07-23): App schickt ihr Backup-Export-JSON
        // (dasselbe Format wie beim manuellen Backup), Server merged es per
        // importData() (insert-if-missing, siehe PortfolioRepository) in seine
        // eigene Datenbank und schickt im Gegenzug sein eigenes aktuelles
        // exportData() zurück - die App merged das wiederum lokal auf dieselbe
        // Weise. Beide Seiten landen dadurch im selben "Vereinigungs"-Zustand.
        webSocket("/sync") {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val receivedJson = frame.readText()
                    try {
                        val summary = repository.importSyncData(receivedJson)
                        logger.info(
                            "Sync: +${summary.cardsAdded} Karten, +${summary.sealedAdded} Sealed-Produkte, " +
                                "~${summary.cardsUpdated} Karten, ~${summary.sealedUpdated} Sealed-Produkte, " +
                                "-${summary.cardsDeleted} Karten, -${summary.sealedDeleted} Sealed-Produkte, " +
                                "+${summary.wishlistsAdded} Wunschlisten, +${summary.wishlistItemsAdded} Wunschlisten-Karten, " +
                                "-${summary.wishlistsDeleted} Wunschlisten, -${summary.wishlistItemsDeleted} Wunschlisten-Karten, " +
                                "+${summary.bindersAdded} Binder, +${summary.binderItemsAdded} Binder-Karten, " +
                                "-${summary.bindersDeleted} Binder, -${summary.binderItemsDeleted} Binder-Karten, " +
                                "~${summary.binderItemsUpdated} Binder-Kartenplätze (vom Client)"
                        )
                    } catch (e: Exception) {
                        logger.warn("Sync-Import fehlgeschlagen: ${e.message}")
                    }
                    // Frisch gesyncte Karten gleich in den Bild-Cache sichern
                    // (17.08.) - fire-and-forget, überlappende Läufe werden
                    // über imagePrefetchRunning übersprungen.
                    call.application.launch { prefetchOwnedImagesOnce() }
                    send(Frame.Text(repository.exportSyncData()))
                }
            }
        }
    }
}
