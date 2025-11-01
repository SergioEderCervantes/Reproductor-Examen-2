package com.example.examenreproductor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.examenreproductor.databinding.ActivityReproductorBinding

class ReproductorActivity : AppCompatActivity() {
    private lateinit var b: ActivityReproductorBinding
    private lateinit var musicPlayer: MusicPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityReproductorBinding.inflate(layoutInflater)
        setContentView(b.root)

        val songName = intent.getStringExtra("SONG_NAME")
        val songImageResId = intent.getIntExtra("SONG_IMAGE_RES_ID", R.drawable.ic_launcher_background)
        b.songNameTextview.text = songName
        b.songImageView.setImageResource(songImageResId)

        musicPlayer = MusicPlayer(this)
        musicPlayer.initializePlayer(songName!!.toLowerCase().replace(" ", "_"))
        musicPlayer.play()
        b.musicPlayerView.setPlaying(true)

        b.musicPlayerView.setOnPlayPauseClickListener {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause()
            } else {
                musicPlayer.play()
            }
        }

        b.musicPlayerView.setOnStopClickListener {
            musicPlayer.stop()
            finish()
        }

        musicPlayer.setOnStateChangedListener { isPlaying ->
            b.musicPlayerView.setPlaying(isPlaying)
        }

        b.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        musicPlayer.stop()
    }
}