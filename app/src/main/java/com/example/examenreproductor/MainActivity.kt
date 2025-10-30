package com.example.examenreproductor

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MainActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player = ExoPlayer.Builder(this).build()

        val playButton: Button = findViewById(R.id.playButton)

        playButton.setOnClickListener {
            val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://$packageName/${R.raw.development}"))
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.play()
        }
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}
