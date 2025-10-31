package com.example.examenreproductor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.examenreproductor.databinding.ActivityReproductorBinding

class ReproductorActivity : AppCompatActivity() {
    private lateinit var b: ActivityReproductorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityReproductorBinding.inflate(layoutInflater)
        setContentView(b.root)

        val songName = intent.getStringExtra("SONG_NAME")
        b.songNameTextview.text = songName

        b.backButton.setOnClickListener {
            finish()
        }
    }
}