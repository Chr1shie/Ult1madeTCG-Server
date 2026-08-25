import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// Reine Datenschicht (SQLDelight-Schema, PortfolioRepository, Katalog-Seed-
// Daten), aus dem UI-lastigen `shared`-Modul extrahiert (2026-07-23), damit
// sowohl die App (`shared`, Android/iOS) als auch der Server (`:server`, JVM)
// dieselben Datenmodelle/dieselbe Repository-Logik nutzen können - siehe
// CONCEPT.md, Phase 1e. Bewusst ohne jegliche Compose-/UI-Abhängigkeit.
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    // Ohne explizite Toolchain kompiliert Gradles jvm()-Target mit der
    // Gradle-eigenen JVM (hier JDK 21) - das :server-Modul läuft aber mit
    // jvmToolchain(11) (siehe server/build.gradle.kts), was zur Laufzeit mit
    // "class file version 65.0... only recognizes up to 55.0" fehlschlägt.
    // Beide Module müssen dasselbe Ziel haben.
    jvmToolchain(11)
    jvm()

    iosArm64()
    iosSimulatorArm64()

    android {
        namespace = "com.tcgportfolio.companion.data"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    sourceSets {
        // Ktor-Client (28.07., Cardmarket-Preisabgleich) - bewusst hier in
        // :data statt in :shared/:server einzeln, damit sowohl App als auch
        // Server dieselbe Fetch-/Match-Logik über die gemeinsame Repository-
        // Schicht nutzen (siehe CardmarketPriceClient.kt), statt sie zu
        // duplizieren. Plattform-Engines analog zu denen, die :shared für
        // den bestehenden Sync-Client bereits nutzt (okhttp/darwin) bzw.
        // eine passende JVM-Engine für den Server (cio - reines Kotlin,
        // keine zusätzlichen nativen Abhängigkeiten).
        androidMain.dependencies {
            implementation("app.cash.sqldelight:android-driver:2.0.1")
            implementation("io.ktor:ktor-client-okhttp:2.3.11")
        }
        commonMain.dependencies {
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
            implementation("io.ktor:ktor-client-core:2.3.11")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.11")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
        }
        iosMain.dependencies {
            implementation("app.cash.sqldelight:native-driver:2.0.1")
            implementation("io.ktor:ktor-client-darwin:2.3.11")
        }
        val jvmMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
                implementation("io.ktor:ktor-client-cio:2.3.11")
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// DatabaseDriverFactory (expect/actual class, kein Interface) löst sonst bei
// jedem Build die Warnung "'expect'/'actual' classes ... are in Beta" aus
// (04.08., Nutzer-Fund "Apple schaut wohl auf solche Dinge") - das Feature
// selbst ist stabil genug im Einsatz, die Warnung wird bewusst unterdrückt
// statt die Klasse in ein Interface + Factory-Funktion umzubauen.
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
}

apply(plugin = "app.cash.sqldelight")

sqldelight {
    databases {
        create("PortfolioDatabase") {
            packageName.set("com.tcgportfolio.companion.db")
        }
    }
}
