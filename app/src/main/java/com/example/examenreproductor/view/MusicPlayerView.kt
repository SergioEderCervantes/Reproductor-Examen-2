package com.example.examenreproductor.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.examenreproductor.R
import com.example.examenreproductor.databinding.MusicPlayerViewBinding

class MusicPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var b: MusicPlayerViewBinding

    init {
        b = MusicPlayerViewBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    fun setOnPlayPauseClickListener(listener: () -> Unit) {
        b.playPauseButton.setOnClickListener { listener() }
    }

    fun setOnStopClickListener(listener: () -> Unit) {
        b.stopButton.setOnClickListener { listener() }
    }

    fun setPlaying(isPlaying: Boolean) {
        val drawableRes = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        b.playPauseButton.setImageResource(drawableRes)
    }
}