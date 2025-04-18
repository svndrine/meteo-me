package com.example.appmeteo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmeteo.network.ForecastItem
import com.example.appmeteo.utils.WeatherIconMapper
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter(private val items: List<ForecastItem>) :
    RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val jourView: TextView = itemView.findViewById(R.id.textJour)
        private val tempView: TextView = itemView.findViewById(R.id.textTempJour)
        private val iconView: ImageView = itemView.findViewById(R.id.imageJour)

        fun bind(item: ForecastItem) {
            val sdf = SimpleDateFormat("EEE", Locale.FRENCH)
            val date = Date(item.dt * 1000)
            jourView.text = sdf.format(date).replaceFirstChar { it.uppercase() }

            tempView.text = "${item.main.temp.toInt()}°"
            val icon = WeatherIconMapper.getIconResForWeather(item.weather[0].main)
            iconView.setImageResource(icon)
        }
    }
}
