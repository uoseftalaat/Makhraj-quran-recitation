package com.example.quranapplication.ui.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.quranapplication.R
import com.example.quranapplication.databinding.FragmentRecordingHandlerBinding
import com.example.quranapplication.other.Constant
import com.example.quranapplication.recorder.AndroidAudioRecorder
import com.example.quranapplication.ui.viewmodel.MainViewModel
import java.io.File


class RecordingHandlerFragment : Fragment() {
    private lateinit var binding:FragmentRecordingHandlerBinding
    private var isRecording = false
    private val viewModel: MainViewModel by viewModels()
    private lateinit var audioRecorder: AndroidAudioRecorder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordingHandlerBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        startRecord()
        setTextSource()
        setReciteMode()
        return binding.root
    }

    private fun setTextSource(){
        MainViewModel.sura.observe(viewLifecycleOwner, Observer {
            binding.tvrecording.text = it
        })
    }

    private fun checkRecordingPermission():Boolean = ContextCompat.checkSelfPermission(
        requireContext(),android.Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    private fun startRecord(){
        audioRecorder = AndroidAudioRecorder(requireContext())
        val file = File(requireContext().cacheDir,"audio.mp3")
        binding.record.setOnClickListener{
            if(isRecording){
                audioRecorder.stopRecording()
                Toast.makeText(requireContext(),resources.getString(R.string.recording_end), Toast.LENGTH_SHORT).show()
                file.delete()
                isRecording = false
                Toast.makeText(requireContext(),"stoping", Toast.LENGTH_SHORT).show()
            }
            else{
                if(checkRecordingPermission()) {
                    isRecording = true
                    file.apply {
                        audioRecorder.buildRecorder(MainViewModel.chosenSura)
                        audioRecorder.startRecording(this)
                    }
                }
                else{
                    Toast.makeText(requireContext(),resources.getString(R.string.request_permission),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        audioRecorder.stopRecording()
    }

    private fun setReciteMode(){
        binding.returnmodebt.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,SuraViewerFragment())?.commit()
        }
    }
}