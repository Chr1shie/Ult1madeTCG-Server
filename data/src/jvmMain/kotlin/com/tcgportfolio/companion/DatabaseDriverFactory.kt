package com.tcgportfolio.companion

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.tcgportfolio.companion.db.PortfolioDatabase
import java.io.File

// Für den Server (Phase 1e) - eigene, lokale SQLite-Datei statt Android/iOS'
// eingebetteten Treibern (die Schema-Erstellung/-Migration automatisch beim
// Öffnen erledigen). Pfad über Umgebungsvariable konfigurierbar (fürs
// Docker-Volume), Default für lokale Entwicklung im Arbeitsverzeichnis.
//
// Echte Versions-Migration über PRAGMA user_version (Bugfix 27.07., beim
// Bau der Wunschlisten-Tabellen entdeckt): vorher lief Schema.create() NUR
// bei einer brandneuen Datei - eine bereits bestehende Server-DB (wie die
// des Nutzers im echten Betrieb, längst über mehrere *.sqm-Migrationen
// gewachsen) bekam neue Tabellen/Spalten aus SPÄTEREN Migrationen nie
// angewendet. Der alte Kommentar hier ("es gibt noch keine ausgelieferte
// Server-Instanz mit alter Version") stimmte beim Schreiben, aber der
// Server läuft inzwischen produktiv beim Nutzer.
//
// Eine bestehende Datei kann dabei in zwei ganz unterschiedlichen Zuständen
// ankommen:
// 1. user_version > 0: sauber verfolgt (ab diesem Fix). Ganz normaler
//    Schema.migrate(current, target) - läuft nur die WIRKLICH neuen
//    *.sqm-Dateien seit dem letzten Start.
// 2. user_version == 0, aber die Datei enthält bereits Daten: eine "alte"
//    Datei von VOR diesem Fix. Ihr echter Schema-Stand ist unbekannt (sie
//    kann irgendwo zwischen Migration 1 und der letzten vor diesem Fix
//    stehen, je nachdem, wann sie einmal per Schema.create() angelegt
//    wurde - Schema.create() bäckt dabei IMMER den zum Erstellungszeitpunkt
//    aktuellen, vollständigen Stand ein, nie eine historische Zwischenstufe).
//    Schema.migrate(driver, 0, target) würde hier versuchen, ALLE
//    *.sqm-Dateien 1..target von vorne abzuspielen, obwohl die älteren
//    Migrationen (z.B. "CREATE TABLE DeletionLog") längst erledigt sind -
//    das crasht mit "table/column already exists". Deshalb wird für diesen
//    Fall NICHT die generische Migrationskette gespielt, sondern gezielt
//    (idempotent, IF NOT EXISTS) nur das nachgezogen, was seit Einführung
//    dieser Versionsverfolgung neu hinzugekommen ist - aktuell die beiden
//    Wunschlisten-Tabellen aus 11.sqm. Künftige Migrationen, die BESTEHENDE
//    Tabellen/Spalten anfassen (nicht nur rein additiv neue Tabellen
//    anlegen), müssten diesen Bootstrap-Zweig entsprechend erweitern.
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val path = System.getenv("DB_PATH") ?: "portfolio.db"
        val isNewFile = !File(path).exists()
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$path")
        val targetVersion = PortfolioDatabase.Schema.version

        if (isNewFile) {
            PortfolioDatabase.Schema.create(driver)
            setUserVersion(driver, targetVersion)
            return driver
        }

        val currentVersion = getUserVersion(driver)
        when {
            currentVersion >= targetVersion -> Unit
            currentVersion > 0L -> {
                PortfolioDatabase.Schema.migrate(driver, currentVersion, targetVersion)
                setUserVersion(driver, targetVersion)
            }
            tableExists(driver, "PortfolioItemEntity") -> {
                bootstrapTablesAddedAfterVersionTracking(driver)
                setUserVersion(driver, targetVersion)
            }
            else -> {
                // Datei existiert (z.B. leer vormontiertes Docker-Volume), aber
                // ohne jedes Kern-Table - wie eine neue Datenbank behandeln
                PortfolioDatabase.Schema.create(driver)
                setUserVersion(driver, targetVersion)
            }
        }
        return driver
    }

    private fun tableExists(driver: SqlDriver, name: String): Boolean {
        return driver.executeQuery(
            identifier = null,
            sql = "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
            mapper = { cursor -> QueryResult.Value(cursor.next().value) },
            parameters = 1,
            binders = { bindString(0, name) }
        ).value
    }

    // Siehe Klassen-Kommentar oben - nur additive, garantiert neue Tabellen,
    // idempotent per IF NOT EXISTS statt der generischen *.sqm-Kette. WICHTIG:
    // muss bei jeder additiven Schema-Änderung seit Einführung dieser
    // Versionsverfolgung auf dem LETZTEN Stand gehalten werden (siehe
    // Klassen-Kommentar) - hier direkt inklusive der uid-Spalte aus 12.sqm,
    // sonst würde eine Datenbank, die diesen Zweig zum ALLERERSTEN Mal
    // durchläuft, sofort auf die neueste Version gestempelt, OHNE die
    // uid-Spalte je bekommen zu haben (die generische Migrationskette wird
    // hier ja bewusst übersprungen). Die ALTER-TABLE-Zeile darunter fängt
    // zusätzlich den Fall ab, dass WishlistEntity aus einem FRÜHEREN
    // Durchlauf dieses Zweigs (vor Einführung von uid) schon ohne die Spalte
    // existiert - "duplicate column"-Fehler wird dann bewusst verschluckt.
    private fun bootstrapTablesAddedAfterVersionTracking(driver: SqlDriver) {
        driver.execute(
            identifier = null,
            sql = """
                CREATE TABLE IF NOT EXISTS WishlistEntity (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    game TEXT NOT NULL,
                    createdAt INTEGER NOT NULL,
                    uid TEXT NOT NULL DEFAULT ''
                )
            """.trimIndent(),
            parameters = 0
        )
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE WishlistEntity ADD COLUMN uid TEXT NOT NULL DEFAULT ''",
                parameters = 0
            )
        }
        driver.execute(
            identifier = null,
            sql = """
                CREATE TABLE IF NOT EXISTS WishlistItemEntity (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    wishlistId INTEGER NOT NULL REFERENCES WishlistEntity(id),
                    cardId TEXT REFERENCES CardCatalogEntity(id),
                    name TEXT NOT NULL,
                    imageUrl TEXT,
                    addedAt INTEGER NOT NULL
                )
            """.trimIndent(),
            parameters = 0
        )
        // marketPriceEur aus 13.sqm (28.07., Cardmarket-Preisabgleich) - anders
        // als die beiden Tabellen oben eine Spalte auf einer Tabelle
        // (CardCatalogEntity), die es schon seit der allerersten Schema-
        // Version gibt, deshalb hier nur die defensive ALTER-TABLE-Zeile
        // nötig (kein CREATE TABLE IF NOT EXISTS möglich/nötig)
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE CardCatalogEntity ADD COLUMN marketPriceEur REAL",
                parameters = 0
            )
        }
        // Mehrere Cardmarket-Preisoptionen + Nutzer-Auswahl aus 14.sqm (28.07.)
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE CardCatalogEntity ADD COLUMN marketPriceEurOptions TEXT",
                parameters = 0
            )
        }
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE CardCatalogEntity ADD COLUMN marketPriceEurSelectedIndex INTEGER",
                parameters = 0
            )
        }
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE CardCatalogEntity ADD COLUMN marketPriceEurSelectedAt INTEGER",
                parameters = 0
            )
        }
        // Binder aus 15.sqm (31.07.) - siehe Kommentar bei BinderEntity in
        // Portfolio.sq. uid ist hier von Anfang an Teil der Tabelle (anders als
        // bei WishlistEntity oben). pageSize/position/positionUpdatedAt kamen
        // mit 16.sqm dazu (Binder-Seiten, ebenfalls 31.07.) - hier direkt
        // inklusive, plus defensive ALTER-TABLE-Zeilen für den Fall, dass
        // dieser Zweig schon einmal VOR 16.sqm durchlaufen wurde.
        driver.execute(
            identifier = null,
            sql = """
                CREATE TABLE IF NOT EXISTS BinderEntity (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    game TEXT NOT NULL,
                    createdAt INTEGER NOT NULL,
                    uid TEXT NOT NULL DEFAULT '',
                    pageSize INTEGER NOT NULL DEFAULT 9
                )
            """.trimIndent(),
            parameters = 0
        )
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE BinderEntity ADD COLUMN pageSize INTEGER NOT NULL DEFAULT 9",
                parameters = 0
            )
        }
        driver.execute(
            identifier = null,
            sql = """
                CREATE TABLE IF NOT EXISTS BinderItemEntity (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    binderId INTEGER NOT NULL REFERENCES BinderEntity(id),
                    cardId TEXT REFERENCES CardCatalogEntity(id),
                    name TEXT NOT NULL,
                    imageUrl TEXT,
                    addedAt INTEGER NOT NULL,
                    position INTEGER NOT NULL DEFAULT 0,
                    positionUpdatedAt INTEGER NOT NULL DEFAULT 0
                )
            """.trimIndent(),
            parameters = 0
        )
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE BinderItemEntity ADD COLUMN position INTEGER NOT NULL DEFAULT 0",
                parameters = 0
            )
        }
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE BinderItemEntity ADD COLUMN positionUpdatedAt INTEGER NOT NULL DEFAULT 0",
                parameters = 0
            )
        }
        // Bereits vorhandene Binder-Karten aus einem früheren Durchlauf dieses
        // Zweigs (vor 16.sqm) bekommen dieselbe sinnvolle Startanordnung wie
        // im normalen Migrationspfad (16.sqm) - siehe Kommentar dort
        driver.execute(
            identifier = null,
            sql = """
                UPDATE BinderItemEntity
                SET position = (
                    SELECT ranked.rn - 1
                    FROM (
                        SELECT id, ROW_NUMBER() OVER (PARTITION BY binderId ORDER BY addedAt ASC, id ASC) AS rn
                        FROM BinderItemEntity
                    ) AS ranked
                    WHERE ranked.id = BinderItemEntity.id
                )
            """.trimIndent(),
            parameters = 0
        )
        // Mandantenfähigkeit aus 17.sqm (02.08.) - siehe Kommentar bei
        // AccountEntity in Portfolio.sq/CONCEPT.md. Der WHERE-NOT-EXISTS-Guard
        // beim INSERT ersetzt den "garantiert allererste Zeile"-Trick aus
        // 17.sqm (dort reicht ein einfacher INSERT, weil die Tabelle dort
        // gerade erst per CREATE TABLE entstanden ist) - hier defensiv nötig,
        // falls dieser Bootstrap-Zweig doch einmal mehrfach liefe.
        driver.execute(
            identifier = null,
            sql = """
                CREATE TABLE IF NOT EXISTS AccountEntity (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    uid TEXT NOT NULL DEFAULT '',
                    name TEXT NOT NULL,
                    pinHash TEXT,
                    pinSalt TEXT,
                    createdAt INTEGER NOT NULL DEFAULT 0,
                    updatedAt INTEGER NOT NULL DEFAULT 0
                )
            """.trimIndent(),
            parameters = 0
        )
        driver.execute(
            identifier = null,
            sql = """
                INSERT INTO AccountEntity (uid, name, pinHash, pinSalt, createdAt, updatedAt)
                SELECT 'acc-bootstrap-default', 'Standard', NULL, NULL, 0, 0
                WHERE NOT EXISTS (SELECT 1 FROM AccountEntity)
            """.trimIndent(),
            parameters = 0
        )
        listOf("PortfolioItemEntity", "SealedProductEntity", "WishlistEntity", "BinderEntity", "DeletionLog").forEach { table ->
            runCatching {
                driver.execute(
                    identifier = null,
                    sql = "ALTER TABLE $table ADD COLUMN accountId INTEGER NOT NULL DEFAULT 1 REFERENCES AccountEntity(id)",
                    parameters = 0
                )
            }
        }
        // Deckbuilding aus 18.sqm (02.08.) - siehe Kommentar bei DeckEntity/
        // CardCatalogEntity in Portfolio.sq
        listOf("ruleSupertype TEXT", "ruleSubtypes TEXT", "ruleLegalStandard INTEGER", "ruleLegalExpanded INTEGER").forEach { columnDef ->
            runCatching {
                driver.execute(
                    identifier = null,
                    sql = "ALTER TABLE CardCatalogEntity ADD COLUMN $columnDef",
                    parameters = 0
                )
            }
        }
        driver.execute(
            identifier = null,
            sql = """
                CREATE TABLE IF NOT EXISTS DeckEntity (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    game TEXT NOT NULL,
                    createdAt INTEGER NOT NULL,
                    uid TEXT NOT NULL DEFAULT '',
                    accountId INTEGER NOT NULL DEFAULT 1 REFERENCES AccountEntity(id)
                )
            """.trimIndent(),
            parameters = 0
        )
        driver.execute(
            identifier = null,
            sql = """
                CREATE TABLE IF NOT EXISTS DeckCardEntity (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    deckId INTEGER NOT NULL REFERENCES DeckEntity(id),
                    cardId TEXT NOT NULL REFERENCES CardCatalogEntity(id),
                    quantity INTEGER NOT NULL DEFAULT 1,
                    addedAt INTEGER NOT NULL
                )
            """.trimIndent(),
            parameters = 0
        )
        // Cardmarket-EUR-Preise für Sealed-Produkte aus 19.sqm (03.08.)
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE SealedCatalogEntity ADD COLUMN marketPriceEur REAL",
                parameters = 0
            )
        }
        // Binder umbenennen aus 20.sqm (03.08.)
        runCatching {
            driver.execute(
                identifier = null,
                sql = "ALTER TABLE BinderEntity ADD COLUMN nameUpdatedAt INTEGER NOT NULL DEFAULT 0",
                parameters = 0
            )
        }
    }

    private fun getUserVersion(driver: SqlDriver): Long {
        return driver.executeQuery(
            identifier = null,
            sql = "PRAGMA user_version",
            mapper = { cursor -> QueryResult.Value(if (cursor.next().value) (cursor.getLong(0) ?: 0L) else 0L) },
            parameters = 0
        ).value
    }

    private fun setUserVersion(driver: SqlDriver, version: Long) {
        driver.execute(identifier = null, sql = "PRAGMA user_version = $version", parameters = 0)
    }
}
