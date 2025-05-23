package com.example.appmeteo.network

//objet principale json complet
data class ForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val dt: Long,
    val main: ForecastMain,
    val weather: List<ForecastWeather>
)

data class ForecastMain(
    val temp: Double
)

data class ForecastWeather(
    val main: String
)
