package com.example.examenreproductor.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.examenreproductor.R

class MusicPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val playPauseButton: ImageButton
    private val stopButton: ImageButton

    init {
        LayoutInflater.from(context).inflate(R.layout.music_player_view, this, true)
        playPauseButton = findViewById(R.id.play_pause_button)
        stopButton = findViewById(R.id.stop_button)
    }

    fun setOnPlayPauseClickListener(listener: () -> Unit) {
        playPauseButton.setOnClickListener { listener() }
    }

    fun setOnStopClickListener(listener: () -> Unit) {
        stopButton.setOnClickListener { listener() }
    }

    fun setPlaying(isPlaying: Boolean) {
        val drawableRes = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        playPauseButton.setImageResource(drawableRes)
    }
}