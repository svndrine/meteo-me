package com.example.appmeteo.utils

import com.example.appmeteo.R

object WeatherIconMapper {
    fun getIconResForWeather(meteo: String): Int {
        return when (meteo.lowercase()) {
            "clear" -> R.drawable.clear
            "clouds" -> R.drawable.clouds
            "rain" -> R.drawable.rain
            "snow" -> R.drawable.snow
            "drizzle" -> R.drawable.drizzle
            "thunderstorm" -> R.drawable.thunderstorm
            "mist", "fog" -> R.drawable.mist
            else -> R.drawable.clouds // fallback
        }
    }

}
