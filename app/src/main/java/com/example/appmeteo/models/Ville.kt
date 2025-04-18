package com.example.appmeteo.models


data class Ville(
    val nom: String,
    val heure: String,
    val temperature: String,
    val meteo: String // ex: "Clear", "Rain"
)
