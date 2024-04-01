package com.example.quranapplication.player

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stopPlay()
}