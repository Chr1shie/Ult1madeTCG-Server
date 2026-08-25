package com.tcgportfolio.companion

import app.cash.sqldelight.db.SqlDriver // <- Geändert

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}