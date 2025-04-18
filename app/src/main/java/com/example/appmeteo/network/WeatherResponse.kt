package com.example.appmeteo.network

data class WeatherResponse(
    val name: String,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val sys: Sys
)

data class Weather(
    val main: String,
    val description: String
)

data class Main(
    val temp: Float,
    val feels_like: Float,
    val humidity: Int
)

data class Wind(
    val speed: Float
)

data class Sys(
    val sunset: Long
)
