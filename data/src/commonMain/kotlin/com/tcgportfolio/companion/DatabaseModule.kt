package com.tcgportfolio.companion

import com.tcgportfolio.companion.db.PortfolioDatabase

// Diese Klasse nutzen wir, um das Repository an einer zentralen Stelle zu erstellen
class DatabaseModule(driverFactory: DatabaseDriverFactory) {

    // Wir erzeugen den Driver aus der Factory
    private val driver = driverFactory.createDriver()

    // Wir initialisieren die Datenbank mit dem Driver
    private val database = PortfolioDatabase(driver)

    // Wir erstellen unser Repository
    val repository = PortfolioRepository(database)
}

