package com.example.examenreproductor

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.examenreproductor.databinding.ActivityReproductorBinding

class ReproductorActivity : AppCompatActivity() {

    private lateinit var b: ActivityReproductorBinding
    private var musicPlayerService: MusicPlayerService? = null
    private var isBound = false
    private var songName: String? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicPlayerService.LocalBinder
            musicPlayerService = binder.getService()
            isBound = true

            // Player is initialized and started from onStart
            // Set listener to update local UI
            musicPlayerService?.setOnStateChangedListener { isPlaying ->
                b.musicPlayerView.setPlaying(isPlaying)
            }
            // Sync UI with service state
            b.musicPlayerView.setPlaying(musicPlayerService?.isPlaying() ?: false)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityReproductorBinding.inflate(layoutInflater)
        setContentView(b.root)

        songName = intent.getStringExtra("SONG_NAME")
        val songImageResId = intent.getIntExtra("SONG_IMAGE_RES_ID", R.drawable.ic_launcher_background)
        b.songNameTextview.text = songName
        b.songImageView.setImageResource(songImageResId)

        b.musicPlayerView.setOnPlayPauseClickListener {
            musicPlayerService?.let { service ->
                if (service.isPlaying()) {
                    service.pause()
                } else {
                    service.play()
                }
            }
        }

        b.musicPlayerView.setOnStopClickListener {
            musicPlayerService?.stop()
            finish()
        }

        b.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val serviceIntent = Intent(this, MusicPlayerService::class.java).apply {
            putExtra("SONG_NAME", songName)
        }
        startService(serviceIntent) // Start the service to keep it running
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE) // Bind to interact with it
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}