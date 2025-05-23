package com.example.appmeteo.utils

import java.text.SimpleDateFormat
import java.util.*

//convertir un timestamp unix en heure lisible
fun formatUnixHour(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val sdf = SimpleDateFormat("H 'h'", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}
