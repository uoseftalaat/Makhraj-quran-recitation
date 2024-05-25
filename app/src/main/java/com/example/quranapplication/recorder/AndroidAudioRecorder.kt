package com.example.quranapplication.recorder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.AudioFormat
import android.media.AudioFormat.CHANNEL_CONFIGURATION_MONO
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.quranapplication.matchingalgo.MatchingAlgorithm
import com.example.quranapplication.network.SocketManager
import com.example.quranapplication.other.Constant
import com.example.quranapplication.ui.viewmodel.MainViewModel
import com.example.quranproject.recorder.QAudioRecorder
import java.io.File


class AndroidAudioRecorder(
    private val context:Context
): QAudioRecorder {
    private val sampleRate = 16000
    private var isRecording = true
    private val channelConfig = CHANNEL_CONFIGURATION_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private var minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat) * 10
    private var audioRecord:AudioRecord? = null
    private val socketManager = SocketManager()
    var matching:MatchingAlgorithm? = null

     fun buildRecorder(Sura:String){
         if (ActivityCompat.checkSelfPermission(
                 context,
                 Manifest.permission.RECORD_AUDIO
             ) == PackageManager.PERMISSION_GRANTED
         ) {
             audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,minBufferSize)
            matching = MatchingAlgorithm(Sura,context)
         }
        socketManager.buildSocket()
    }

    override fun startRecording(outputFile: File) {
        audioRecord!!.startRecording()
        isRecording = true
        val buffer = ByteArray(minBufferSize)
        Thread {
            while (isRecording) {
                val bytesRead = audioRecord!!.read(buffer, 0, buffer.size)
                socketManager.startSocket(buffer, bytesRead){
                    if(it == null){}
                    else{
                        var resultant = matching?.match(it)
                        var res : CharSequence = ""
                        if (resultant != null) {
                            res = SpannableString(MainViewModel.sura.value.toString())
                            for(i in resultant){
                                res = if(i.b){
                                    Log.i("resultant_string", "$it True")
                                    var spanned = SpannableString(i.s)
                                    spanned.setSpan(ForegroundColorSpan(Color.GREEN),0,i.s.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                    TextUtils.concat(res , " " , spanned)
                                } else{
                                    Log.i("resultant_string", "$it False")
                                    var spanned = SpannableString(i.s)
                                    spanned.setSpan(ForegroundColorSpan(Color.RED),0,i.s.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                    TextUtils.concat(res , " " , spanned)
                                }
                            }
                        }
                        MainViewModel.sura.postValue(SpannableString(res))
                    }
                }
            }
        }.start()
    }

    override fun stopRecording() {
        Toast.makeText(context,"stoping recorder",Toast.LENGTH_SHORT).show()
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        socketManager.close()
    }



}