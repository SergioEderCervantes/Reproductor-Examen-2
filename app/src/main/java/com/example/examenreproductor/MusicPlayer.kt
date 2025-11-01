package com.example.examenreproductor

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MusicPlayer(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null
    private var onStateChanged: ((Boolean) -> Unit)? = null

    fun setOnStateChangedListener(listener: (Boolean) -> Unit) {
        onStateChanged = listener
    }

    fun initializePlayer(songName: String) {
        exoPlayer = ExoPlayer.Builder(context).build()
        val resourceId = context.resources.getIdentifier(songName, "raw", context.packageName)
        val uri = Uri.parse("android.resource://${context.packageName}/$resourceId")
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
    }

    fun play() {
        exoPlayer?.play()
        onStateChanged?.invoke(true)
    }

    fun pause() {
        exoPlayer?.pause()
        onStateChanged?.invoke(false)
    }

    fun stop() {
        exoPlayer?.stop()
        exoPlayer?.release()
        exoPlayer = null
        onStateChanged?.invoke(false)
    }

    fun isPlaying(): Boolean {
        return exoPlayer?.isPlaying ?: false
    }
}