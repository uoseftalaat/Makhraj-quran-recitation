package com.example.quranapplication.ui.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.quranapplication.R
import com.example.quranapplication.databinding.FragmentSuraViewerBinding
import com.example.quranapplication.network.SocketManager
import com.example.quranapplication.ui.viewmodel.MainViewModel
import com.example.quranapplication.recorder.AndroidAudioRecorder
import java.io.File


class SuraViewerFragment : Fragment() {

    private lateinit var binding:FragmentSuraViewerBinding
    private val viewModel: MainViewModel by viewModels()
    lateinit var audioRecorder: AndroidAudioRecorder
    private var isRecording = false
    val socketManager = SocketManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuraViewerBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setTextSource()
        startRecord()
        setReciteMode()
        return binding.root
    }

    private fun setTextSource(){
        binding.ayat.movementMethod = ScrollingMovementMethod()
        MainViewModel.sura.observe(viewLifecycleOwner, Observer {
            binding.ayat.setText(it, TextView.BufferType.SPANNABLE)
        })
    }
    private fun setReciteMode(){
        binding.recitemodebt.setOnClickListener{
            if(binding.record.visibility == View.VISIBLE){
                binding.recitemodebt.setImageResource(R.drawable.hide)
                binding.record.visibility = View.INVISIBLE
                binding.ayat.text = MainViewModel.sura.value
            }
            else{
                binding.recitemodebt.setImageResource(R.drawable.view)
                binding.record.visibility = View.VISIBLE
                binding.ayat.text = ""
            }
        }
    }

    private fun startRecord(){
        audioRecorder = AndroidAudioRecorder(requireContext())
        val file = File(requireContext().cacheDir,"audio.mp3")
        binding.record.setOnClickListener{
            if(isRecording){
                audioRecorder.stopRecording()
                Toast.makeText(requireContext(),resources.getString(R.string.recording_end),Toast.LENGTH_SHORT).show()
                file.delete()
                isRecording = false
            }
            else{
            if(checkRecordingPermission()) {
                isRecording = true
                file.apply {
                    audioRecorder.buildRecorder()
                    audioRecorder.startRecording(this)
                }
            }
            else{
                Toast.makeText(requireContext(),resources.getString(R.string.request_permission),Toast.LENGTH_SHORT).show()
            }
        }
        }
    }

    private fun checkRecordingPermission():Boolean = ContextCompat.checkSelfPermission(
            requireContext(),android.Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED



    //not used
    private fun setToolsVisibility(){
        binding.ayat.setOnClickListener{
            if(MainViewModel.visibilityState.value == View.VISIBLE) {
                MainViewModel.visibilityState.value = View.INVISIBLE
            }
            else
                MainViewModel.visibilityState.value = View.VISIBLE
        }
    }
}