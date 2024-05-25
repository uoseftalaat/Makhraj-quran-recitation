package com.example.quranapplication.network

import android.util.Log
import com.example.quranapplication.other.Constant.HOST
import com.example.quranapplication.other.Constant.PORT
import kotlinx.coroutines.CoroutineScope
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
    private var isClosed = false

    fun buildSocket() = Thread{
        socket = Socket(HOST, PORT)
    }.start()


    fun startSocket(buffer: ByteArray, bytesRead: Int,callback: (String?) -> Unit) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                if(!isClosed) {
                    outputStream = DataOutputStream(socket?.getOutputStream())
                    outputStream?.write(buffer, 0, bytesRead)
                    inputStream = socket?.getInputStream()
                    if (inputStream?.available()!! > 0) {
                        val br = BufferedReader(InputStreamReader(inputStream))
                        val res = br.readLine()
                        if (res == "?????"){
                            inputStream?.close()
                            socket?.close()
                            isClosed = true
                            outputStream?.close()
                        }
                        callback(res)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (ex: NullPointerException){
            Log.i("stream_process","socket is not initialized")
        }
    }



    fun close(){
        inputStream?.close()
        outputStream?.close()
        socket?.close()
        isClosed = true
        
    }
}