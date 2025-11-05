package com.example.examenreproductor

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class MusicWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_PLAY_PAUSE = "com.example.examenreproductor.ACTION_PLAY_PAUSE"
        const val ACTION_STOP = "com.example.examenreproductor.ACTION_STOP"
        const val ACTION_UPDATE_WIDGET = "com.example.examenreproductor.ACTION_UPDATE_WIDGET"
        const val EXTRA_SONG_TITLE = "com.example.examenreproductor.EXTRA_SONG_TITLE"
        const val EXTRA_IS_PLAYING = "com.example.examenreproductor.EXTRA_IS_PLAYING"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == ACTION_UPDATE_WIDGET) {
            val songTitle = intent.getStringExtra(EXTRA_SONG_TITLE)
            val isPlaying = intent.getBooleanExtra(EXTRA_IS_PLAYING, false)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, MusicWidgetProvider::class.java))

            for (appWidgetId in appWidgetIds) {
                val views = RemoteViews(context.packageName, R.layout.music_widget_layout)
                views.setTextViewText(R.id.widget_song_title, songTitle ?: "Unknown Song")
                views.setImageViewResource(R.id.widget_play_pause_button, if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.music_widget_layout)

        // Create PendingIntent for Play/Pause button
        val playPauseIntent = Intent(context, MusicPlayerService::class.java).apply {
            action = ACTION_PLAY_PAUSE
        }
        val playPausePendingIntent = PendingIntent.getService(context, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        views.setOnClickPendingIntent(R.id.widget_play_pause_button, playPausePendingIntent)

        // Create PendingIntent for Stop button
        val stopIntent = Intent(context, MusicPlayerService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(context, 1, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        views.setOnClickPendingIntent(R.id.widget_stop_button, stopPendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}