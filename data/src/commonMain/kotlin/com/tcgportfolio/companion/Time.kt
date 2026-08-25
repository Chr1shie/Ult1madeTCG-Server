package com.tcgportfolio.companion

// Aktuelle Zeit in Millisekunden seit Epoch - nur für den Export-Zeitstempel
// und den Backup-Dateinamen gebraucht, keine echte Zeitzonen-/Datumslogik
// nötig, deshalb kein kotlinx-datetime als Extra-Abhängigkeit.
expect fun currentTimeMillis(): Long
