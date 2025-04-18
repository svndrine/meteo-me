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

class HourlyAdapter(private val items: List<ForecastItem>) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hour, parent, false)
        return HourlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val heureView: TextView = itemView.findViewById(R.id.textHeure)
        private val tempView: TextView = itemView.findViewById(R.id.textTempHeure)
        private val iconView: ImageView = itemView.findViewById(R.id.imageHeure)

        fun bind(item: ForecastItem) {
            val date = Date(item.dt * 1000)
            val sdf = SimpleDateFormat("HH:mm", Locale.FRENCH)
            heureView.text = sdf.format(date)
            tempView.text = "${item.main.temp.toInt()}°"
            iconView.setImageResource(WeatherIconMapper.getIconResForWeather(item.weather[0].main))
        }
    }
}
