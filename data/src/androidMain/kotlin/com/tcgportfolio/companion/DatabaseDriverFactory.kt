package com.tcgportfolio.companion

import android.content.Context
import app.cash.sqldelight.db.SqlDriver // <- Geändert
import app.cash.sqldelight.driver.android.AndroidSqliteDriver // <- Geändert
import com.tcgportfolio.companion.db.PortfolioDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(PortfolioDatabase.Schema, context, "portfolio.db")
    }
}