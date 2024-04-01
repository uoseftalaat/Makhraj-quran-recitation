package com.example.quranapplication.recorder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioFormat.CHANNEL_CONFIGURATION_MONO
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
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
    private var minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat) * 4
    private var audioRecord:AudioRecord? = null
    var recordingThread:Thread? = null
    var outputStream:DataOutputStream? = null
    val socket:Socket? = null

     fun buildRecorder(){
         if (ActivityCompat.checkSelfPermission(
                 context,
                 Manifest.permission.RECORD_AUDIO
             ) == PackageManager.PERMISSION_GRANTED
         ) {
             audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,minBufferSize)
         }

    }

    override fun startRecording(outputFile: File) {
        try {
            audioRecord!!.startRecording()
            isRecording = true

            // Establish socket connection asynchronously
            GlobalScope.launch(Dispatchers.IO) {
                val socket = Socket("16.170.236.127", 8080)
                val outputStream = DataOutputStream(socket.getOutputStream())

                // Start recording and streaming audio data
                val buffer = ByteArray(minBufferSize)
                while (isRecording) {
                    val bytesRead = audioRecord!!.read(buffer, 0, buffer.size)
                    outputStream.write(buffer, 0, bytesRead)
                }

                // Close output stream and socket when recording stops
                outputStream.close()
                socket.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun stopRecording() {
        try {
            isRecording = false
            recordingThread?.join()
            outputStream?.close()
            socket?.close()
            audioRecord?.stop()
            audioRecord?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}