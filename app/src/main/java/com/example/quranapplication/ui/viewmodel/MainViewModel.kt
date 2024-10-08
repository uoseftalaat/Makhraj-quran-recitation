package com.example.quranapplication.ui.viewmodel

import android.text.SpannableString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapplication.other.Constant
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {


    companion object {

        val sura: MutableLiveData<SpannableString> by lazy {
            MutableLiveData<SpannableString>(SpannableString(""))
        }

        val textSize:MutableLiveData<Float> by lazy {
            MutableLiveData<Float>(80f)
        }

    }

    val visibilityState: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    var chosenSura:String = Constant.alrahman

    var chosenPage:Int = 0
}