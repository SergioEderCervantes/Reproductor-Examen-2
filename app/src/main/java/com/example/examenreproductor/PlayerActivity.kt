package com.example.examenreproductor

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenreproductor.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var b: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(b.root)

        val songNames = resources.getStringArray(R.array.nombres_canciones)
        val songDurations = resources.getStringArray(R.array.duracion_canciones)

        val songs = songNames.zip(songDurations).map { (name, duration) ->
            Song(name, duration)
        }

        val adapter = SongAdapter(songs) { song ->
            Toast.makeText(this, "Se ha seleccionado: ${song.name}", Toast.LENGTH_SHORT).show()
        }

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        b.button.setOnClickListener { finish() }
    }
}
