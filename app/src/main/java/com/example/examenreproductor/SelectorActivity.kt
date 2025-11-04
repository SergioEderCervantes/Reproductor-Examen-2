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
            val imageName = name.lowercase().replace(" ", "_")
            val imageResId = resources.getIdentifier(imageName, "drawable", packageName)
            Song(name, duration, imageResId)
        }

        val adapter = SongAdapter(songs) { song ->
            val intent = Intent(this, ReproductorActivity::class.java)
            intent.putExtra("SONG_NAME", song.name)
            intent.putExtra("SONG_IMAGE_RES_ID", song.imageResId)
            startActivity(intent)
        }

        b.recyclerView.layoutManager = LinearLayoutManager(this)
        b.recyclerView.adapter = adapter

        b.button.setOnClickListener { finish() }
    }
}
