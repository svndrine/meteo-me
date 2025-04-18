package com.example.appmeteo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmeteo.R
import com.example.appmeteo.models.Ville
import com.example.appmeteo.utils.WeatherIconMapper

class VilleAdapter(
    private val villesOriginal: List<Ville>,
    private val onItemClick: (Ville) -> Unit
) : RecyclerView.Adapter<VilleAdapter.VilleViewHolder>(), Filterable {

    private var villesFiltrees: MutableList<Ville> = villesOriginal.toMutableList()

    class VilleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNom: TextView = itemView.findViewById(R.id.villeName)
        val textHeure: TextView = itemView.findViewById(R.id.heure)
        val textTemp: TextView = itemView.findViewById(R.id.temperature)
        val iconMeteo: ImageView = itemView.findViewById(R.id.imageMeteo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VilleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ville, parent, false)
        return VilleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VilleViewHolder, position: Int) {
        val ville = villesFiltrees[position]
        holder.textNom.text = ville.nom
        holder.textHeure.text = ville.heure
        holder.textTemp.text = ville.temperature
        holder.iconMeteo.setImageResource(
            WeatherIconMapper.getIconResForWeather(ville.meteo)
        )

        holder.itemView.setOnClickListener {
            onItemClick(ville)
        }
    }

    override fun getItemCount(): Int = villesFiltrees.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()?.trim()
                val filtered = if (query.isNullOrEmpty()) {
                    villesOriginal
                } else {
                    villesOriginal.filter {
                        it.nom.lowercase().contains(query)
                    }
                }
                return FilterResults().apply { values = filtered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                villesFiltrees = (results?.values as? List<Ville>)?.toMutableList() ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }
}
