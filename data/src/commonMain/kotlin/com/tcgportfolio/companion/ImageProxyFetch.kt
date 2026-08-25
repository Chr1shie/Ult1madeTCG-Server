package com.tcgportfolio.companion

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess

// Bild-Abruf für den Weboberflächen-Bild-Proxy (17.08., Nutzer-Fund
// "Gundam-/One-Piece-Bilder laden im Browser nicht, in der App schon") -
// Safari verliert beim HTTP/2-Multiplexing vieler paralleler Bild-Streams
// gegen Apaches mod_http2 (www.gundam-gcg.com, en.onepiece-cardgame.com)
// Streams ohne HTTP-Status; Einzelabrufe und andere HTTP-Clients (dieser
// hier, curl, Chrome) sind nicht betroffen. Der Server holt das Bild
// deshalb als Rückfalloption selbst und liefert es same-origin aus - siehe
// /images/proxy in server/Main.kt und den globalen error-Handler in app.js.
//
// Lebt im :data-Modul statt in :server, weil ktor-client dort nur als
// implementation-Abhängigkeit existiert und dem Server-Modul deshalb nicht
// im Compile-Classpath zur Verfügung steht - dasselbe Muster wie
// fetchCardmarketPriceGuide()/searchCardmarketProducts().
private val imageProxyClient by lazy { HttpClient() }

suspend fun fetchImageForProxy(url: String): Pair<ByteArray, String?>? {
    return try {
        val response: HttpResponse = imageProxyClient.get(url)
        if (!response.status.isSuccess()) return null
        response.body<ByteArray>() to response.headers[HttpHeaders.ContentType]
    } catch (e: Exception) {
        null
    }
}
