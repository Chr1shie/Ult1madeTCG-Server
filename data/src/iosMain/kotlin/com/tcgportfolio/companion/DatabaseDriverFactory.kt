package com.tcgportfolio.companion

import app.cash.sqldelight.db.SqlDriver // <- Geändert
import app.cash.sqldelight.driver.native.NativeSqliteDriver // <- Geändert
import com.tcgportfolio.companion.db.PortfolioDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(PortfolioDatabase.Schema, "portfolio.db")
    }
}