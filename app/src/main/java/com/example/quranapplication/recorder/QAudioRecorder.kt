package com.example.quranproject.recorder

import java.io.File

interface QAudioRecorder {
    fun startRecording(outputFile:File)
    fun stopRecording()
}