package com.example.appmeteo.network

import okhttp3.*
import java.io.IOException

object WeatherService {
    private val client = OkHttpClient()

    fun fetchWeather(city: String, apiKey: String, callback: (String) -> Unit) {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric&lang=fr"

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { callback(it) }
            }
        })
    }
}
