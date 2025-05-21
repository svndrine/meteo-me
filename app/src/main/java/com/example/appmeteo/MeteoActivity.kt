package com.example.appmeteo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmeteo.network.ForecastItem
import com.example.appmeteo.network.ForecastResponse
import com.example.appmeteo.network.WeatherResponse
import com.example.appmeteo.network.WeatherService
import com.example.appmeteo.utils.WeatherIconMapper
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MeteoActivity : AppCompatActivity() {

    private lateinit var textVille: TextView
    private lateinit var textDate: TextView
    private lateinit var textTemp: TextView
    private lateinit var textDescription: TextView
    private lateinit var textHumidity: TextView
    private lateinit var textFeelsLike: TextView
    private lateinit var textWind: TextView
    private lateinit var textSunset: TextView
    private lateinit var imageMeteo: ImageView
    private lateinit var recyclerHourly: RecyclerView

    private val apiKey = "1fcbae2b519aef96d0ac6343459a0eff" 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meteo)

        // Bind vues
        textVille = findViewById(R.id.textVille)
        textDate = findViewById(R.id.textDate)
        textTemp = findViewById(R.id.textTemp)
        textDescription = findViewById(R.id.textDescription)
        imageMeteo = findViewById(R.id.imageMeteo)
        textHumidity = findViewById(R.id.textHumidity)
        textFeelsLike = findViewById(R.id.textFeelsLike)
        textWind = findViewById(R.id.textWind)
        textSunset = findViewById(R.id.textSunset)

        recyclerHourly = findViewById(R.id.recyclerHourly)
        recyclerHourly.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val city = intent.getStringExtra("ville_nom") ?: return

        fetchCurrentWeather(city)
        fetchHourlyForecast(city)

        val dateFormat = SimpleDateFormat("EEEE d MMMM", Locale.FRENCH)
        textDate.text = dateFormat.format(Date()).replaceFirstChar { it.uppercase() }
    }

    private fun fetchCurrentWeather(city: String) {
        WeatherService.fetchWeather(city, apiKey) { responseStr ->
            val weatherData = Gson().fromJson(responseStr, WeatherResponse::class.java)

            runOnUiThread {
                textVille.text = weatherData.name
                textTemp.text = "${weatherData.main.temp.toInt()}°"
                textDescription.text = weatherData.weather[0].description.replaceFirstChar { it.uppercase() }

                textHumidity.text = "${weatherData.main.humidity}%"
                textFeelsLike.text = "${weatherData.main.feels_like.toInt()}°"
                textWind.text = "${weatherData.wind.speed} km/h"
                textSunset.text = formatUnixTime(weatherData.sys.sunset)

                val icon = WeatherIconMapper.getIconResForWeather(weatherData.weather[0].main)
                imageMeteo.setImageResource(icon)
            }
        }
    }

    private fun fetchHourlyForecast(city: String) {
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=$apiKey&units=metric&lang=fr"

        val request = Request.Builder().url(url).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val forecast = Gson().fromJson(body, ForecastResponse::class.java)

                // Prévisions horaires (prochaines heures)
                val hourly: List<ForecastItem> = forecast?.list?.take(5) ?: emptyList()

                // Prévisions journalières (1 par jour sur 5 jours)
                val daily: List<ForecastItem> = forecast?.list
                    ?.groupBy { SimpleDateFormat("yyyyMMdd", Locale.FRENCH).format(Date(it.dt * 1000)) }
                    ?.map { it.value[0] }
                    ?.take(5) ?: emptyList()

                runOnUiThread {
                    // Bloc des heures
                    if (recyclerHourly.layoutManager == null) {
                        recyclerHourly.layoutManager = LinearLayoutManager(this@MeteoActivity, LinearLayoutManager.HORIZONTAL, false)
                    }
                    recyclerHourly.adapter = HourlyAdapter(hourly)

                    // Bloc des jours
                    val recyclerDaily = findViewById<RecyclerView>(R.id.recyclerDaily)
                    recyclerDaily.layoutManager = LinearLayoutManager(this@MeteoActivity, LinearLayoutManager.HORIZONTAL, false)
                    recyclerDaily.adapter = DailyAdapter(daily)
                }
            }
        })
    }


    private fun formatUnixTime(unix: Long): String {
        val date = Date(unix * 1000)
        val sdf = SimpleDateFormat("HH:mm", Locale.FRENCH)
        return sdf.format(date)
    }
}
