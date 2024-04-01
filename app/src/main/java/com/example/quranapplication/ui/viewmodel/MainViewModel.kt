package com.example.quranapplication.ui.viewmodel

import android.text.SpannableString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quranapplication.other.Constant

class MainViewModel:ViewModel() {

    companion object {

        val sura: MutableLiveData<SpannableString> by lazy {
            MutableLiveData<SpannableString>(SpannableString(Constant.alfateha))
        }

        val visibilityState: MutableLiveData<Int> by lazy {
            MutableLiveData<Int>()
        }

        val textSize:MutableLiveData<Float> by lazy {
            MutableLiveData<Float>(70f)
        }
    }
}