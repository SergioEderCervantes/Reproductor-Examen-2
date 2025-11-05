package com.example.examenreproductor

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MusicPlayerService : Service() {

    private val binder = LocalBinder()
    private var musicPlayer: MusicPlayer? = null
    private var currentSongName: String? = null

    companion object {
        const val CHANNEL_ID = "MusicPlayerServiceChannel"
        const val NOTIFICATION_ID = 1
    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songNameToPlay = intent?.getStringExtra("SONG_NAME")

        if (songNameToPlay != null && songNameToPlay != currentSongName) {
            initializePlayer(songNameToPlay)
            play()
        }

        when (intent?.action) {
            MusicWidgetProvider.ACTION_PLAY_PAUSE -> {
                if (isPlaying()) pause() else play()
                updateWidget(isPlaying())
            }
            MusicWidgetProvider.ACTION_STOP -> {
                stop()
            }
        }

        return START_NOT_STICKY
    }

    fun initializePlayer(songName: String) {
        this.currentSongName = songName
        musicPlayer = MusicPlayer(this).apply {
            initializePlayer(songName.lowercase().replace(" ", "_"))
            setOnStateChangedListener { isPlaying ->
                updateWidget(isPlaying)
                if (isPlaying) {
                    startForeground(NOTIFICATION_ID, createNotification(songName, true))
                } else {
                    stopForeground(false)
                    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(NOTIFICATION_ID, createNotification(songName, false))
                }
            }
        }
    }

    fun play() {
        musicPlayer?.play()
    }

    fun pause() {
        musicPlayer?.pause()
    }

    fun stop() {
        musicPlayer?.stop()
        stopForeground(true)
        stopSelf()
        updateWidget(false)
    }

    fun isPlaying(): Boolean = musicPlayer?.isPlaying() ?: false

    fun setOnStateChangedListener(listener: (Boolean) -> Unit) {
        musicPlayer?.setOnStateChangedListener(listener)
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Music Player Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    private fun createNotification(songTitle: String, isPlaying: Boolean): Notification {
        val playPauseIcon = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Reproductor de Música")
            .setContentText(songTitle)
            .setSmallIcon(R.drawable.ic_play)
            // Add actions in a real app
            .build()
    }

    private fun updateWidget(isPlaying: Boolean) {
        val intent = Intent(this, MusicWidgetProvider::class.java).apply {
            action = MusicWidgetProvider.ACTION_UPDATE_WIDGET
            putExtra(MusicWidgetProvider.EXTRA_SONG_TITLE, currentSongName)
            putExtra(MusicWidgetProvider.EXTRA_IS_PLAYING, isPlaying)
        }
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        musicPlayer?.stop()
        super.onDestroy()
    }
}