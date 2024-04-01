package com.example.quranapplication

import android.app.Application
import com.yariksoffice.lingver.Lingver

class QuranApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this)
    }
}