package com.example.quranapplication.ui

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.quranapplication.R
import com.example.quranapplication.databinding.ActivityMainBinding
import com.example.quranapplication.ui.fragment.IndexFragment
import com.example.quranapplication.ui.fragment.SettingFragment
import com.example.quranapplication.ui.fragment.SuraViewerFragment
import com.example.quranapplication.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        request_permissions(this)
        MainViewModel.visibilityState.value = View.VISIBLE
        MainViewModel.visibilityState.observe(this, Observer{
            binding.bottomNavigationView.visibility = it
        })
        navigation()
        binding.bottomNavigationView.isActivated = false
    }

    private fun request_permissions(context: Context){
        if (ContextCompat.checkSelfPermission(
                context,android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                0
            )
        }
    }

    private fun makeCurrentFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }
    }

    private fun navigation(){
        val homeFragment = SuraViewerFragment()
        val settingFragment = SettingFragment()
        val indexFragment = IndexFragment()
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homebt -> makeCurrentFragment(homeFragment)
                R.id.indexbt -> makeCurrentFragment(indexFragment)
                R.id.settingbt -> makeCurrentFragment(settingFragment)
            }
            true
        }
    }

}