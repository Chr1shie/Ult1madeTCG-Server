plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    application
}

kotlin {
    // JVM 11, konsistent mit dem shared-Modul (JvmTarget.JVM_11) - auf dieser
    // Maschine ist ohnehin nur ein JDK 11 installiert, kein Toolchain-Download
    // konfiguriert
    jvmToolchain(11)
}

application {
    mainClass.set("com.tcgportfolio.server.MainKt")
}

dependencies {
    // Datenschicht (Repository, Katalog-Daten, SQLDelight) - dasselbe Modul,
    // das auch die App (:shared) nutzt, siehe data/build.gradle.kts
    implementation(project(":data"))

    // mDNS/Bonjour-Ankündigung, damit die App den Server per Server-Erkennung
    // findet statt die IP von Hand einzutippen (Phase 1e)
    implementation("org.jmdns:jmdns:3.5.9")

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.serialization.kotlinxJson)
    implementation(libs.logback.classic)

    testImplementation(libs.kotlin.test)
}
