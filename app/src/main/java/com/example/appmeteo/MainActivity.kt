package com.example.appmeteo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmeteo.adapters.VilleAdapter
import com.example.appmeteo.models.Ville

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewVilles)
        val searchInput = findViewById<EditText>(R.id.searchInput)

        // Liste simulée
        val villes = listOf(
            Ville("Paris", "14:00", "23°", "Clear"),
            Ville("Moscou", "11:00", "16°", "Rain"),
            Ville("New York", "16:00", "11°", "Thunderstorm"),
            Ville("Tokyo", "21:00", "19°", "Clouds"),
            Ville("Rio", "09:00", "28°", "Drizzle")
        )

        val adapter = VilleAdapter(villes) { ville ->
            val intent = Intent(this, MeteoActivity::class.java).apply {
                putExtra("ville_nom", ville.nom)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Recherche dynamique
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
