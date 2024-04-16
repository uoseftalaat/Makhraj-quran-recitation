package com.example.quranapplication.network

import android.util.Log
import com.example.quranapplication.other.Constant.HOST
import com.example.quranapplication.other.Constant.PORT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.Socket

class SocketManager {

    private var socket :Socket?= null
    private var outputStream :DataOutputStream? = null
    private var inputStream:InputStream? = null

    fun buildSocket() = Thread{
            socket = Socket(HOST, PORT)
    }.start()

    fun startSocket(buffer: ByteArray, bytesRead: Int,callback: (String?) -> Unit) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                outputStream = DataOutputStream(socket?.getOutputStream())
                outputStream?.write(buffer, 0, bytesRead)
                // Check if there is data available in the input stream
                inputStream = socket?.getInputStream()
                if (inputStream?.available()!! > 0) {
                    val br = BufferedReader(InputStreamReader(inputStream))
                    val res = br.readLine()
                    callback(res)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (ex: NullPointerException){
            Log.i("stream_process","socket is not initialized")
        }
    }



    fun close(){
        socket?.close()
        outputStream?.close()
    }
}