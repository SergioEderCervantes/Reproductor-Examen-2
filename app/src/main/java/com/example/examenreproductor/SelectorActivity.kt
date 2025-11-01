package com.example.examenreproductor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenreproductor.databinding.ActivitySelectorBinding

class SelectorActivity : AppCompatActivity() {
    private lateinit var b: ActivitySelectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySelectorBinding.inflate(layoutInflater)
        setContentView(b.root)

        val songNames = resources.getStringArray(R.array.nombres_canciones)
        val songDurations = resources.getStringArray(R.array.duracion_canciones)

        val songs = songNames.zip(songDurations).map { (name, duration) ->
            Song(name, duration, R.drawable.ic_launcher_background) // Placeholder image
        }

        val adapter = SongAdapter(songs) { song ->
            val intent = Intent(this, ReproductorActivity::class.java)
            intent.putExtra("SONG_NAME", song.name)
            intent.putExtra("SONG_IMAGE_RES_ID", song.imageResId)
            startActivity(intent)
        }

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        b.button.setOnClickListener { finish() }
    }
}
