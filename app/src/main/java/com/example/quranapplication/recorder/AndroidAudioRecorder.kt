package com.example.quranapplication.recorder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioFormat.CHANNEL_CONFIGURATION_MONO
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import com.example.quranapplication.network.SocketManager
import com.example.quranproject.recorder.QAudioRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.File
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket


class AndroidAudioRecorder(
    private val context:Context
): QAudioRecorder {
    private val sampleRate = 16000
    private var isRecording = true
    private val channelConfig = CHANNEL_CONFIGURATION_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private var minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat) * 10
    private var audioRecord:AudioRecord? = null
    val socketManager = SocketManager()

     fun buildRecorder(){
         if (ActivityCompat.checkSelfPermission(
                 context,
                 Manifest.permission.RECORD_AUDIO
             ) == PackageManager.PERMISSION_GRANTED
         ) {
             audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,minBufferSize)
         }
        socketManager.buildSocket()
    }

    override fun startRecording(outputFile: File) {
        audioRecord!!.startRecording()
        isRecording = true
        val buffer = ByteArray(minBufferSize)
        while (isRecording) {
            val bytesRead = audioRecord!!.read(buffer, 0, buffer.size)
            socketManager.sendData(buffer,bytesRead)
            socketManager.receiveDataFromSocket()
        }
        socketManager.close()
    }

    override fun stopRecording() {
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
    }

}