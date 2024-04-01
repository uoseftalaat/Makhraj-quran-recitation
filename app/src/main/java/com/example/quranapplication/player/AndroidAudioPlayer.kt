package com.example.quranproject.player

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.example.quranapplication.player.AudioPlayer
import java.io.File

class AndroidAudioPlayer(
    private val context:Context
) : AudioPlayer {

    private var length = 0

    private var mediaPlayer:MediaPlayer? = null

    override fun playFile(file: File) {
        MediaPlayer.create(context,file.toUri()).apply {
            mediaPlayer = this
            start()
        }

    }

    override fun stopPlay() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}