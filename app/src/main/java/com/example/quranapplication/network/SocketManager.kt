package com.example.quranapplication.network

import android.util.Log
import com.example.quranapplication.other.Constant.HOST
import com.example.quranapplication.other.Constant.PORT
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket

class SocketManager {

    private var socket :Socket?= null
    private var outputStream :DataOutputStream? = null
    fun buildSocket(){
        GlobalScope.launch {
            socket = Socket(HOST, PORT)
        }
    }

    fun sendData(buffer:ByteArray , bytesRead:Int){
        try {
            GlobalScope.launch {
                outputStream = DataOutputStream(socket?.getOutputStream())
                outputStream?.write(buffer, 0, bytesRead)
            }
        } catch (e: Exception) {
        e.printStackTrace()
    }
    }

    fun socketState() = socket?.isClosed

    fun receiveDataFromSocket() {
        GlobalScope.launch {
            val br_input = BufferedReader(InputStreamReader(socket?.getInputStream()))
            var res = br_input.readLine()
            Log.i("stream_process", res)
        }
    }

    fun close(){
        socket?.close()
        outputStream?.close()
    }
}