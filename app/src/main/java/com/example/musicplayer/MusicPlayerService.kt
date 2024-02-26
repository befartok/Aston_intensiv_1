package com.example.musicplayer

import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.IBinder

class MusicPlayerService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private var isStopped = true

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val trName = intent.getStringExtra("trName").toString()

        val res: Resources = applicationContext.resources

        val soundId = res.getIdentifier(trName, "raw", applicationContext.packageName)

        if (isStopped) {
            mediaPlayer = MediaPlayer.create(applicationContext, soundId)
        }

        val buttonsType = intent.getStringExtra("buttonsType").toString()

        when (buttonsType) {
            "Play" -> mediaPlayer.start()
            "Pause" -> mediaPlayer.pause()
        }

        isStopped = false

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        isStopped = true
    }


}