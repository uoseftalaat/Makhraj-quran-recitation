package com.example.quranapplication.ui.fragment

import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.quranapplication.R
import com.example.quranapplication.databinding.FragmentSuraViewerBinding
import com.example.quranapplication.other.Constant
import com.example.quranapplication.ui.viewmodel.MainViewModel
import com.example.quranapplication.ui.recyclerview.ViewAdapter
import kotlin.math.log


class SuraViewerFragment : Fragment() {

    private lateinit var binding:FragmentSuraViewerBinding
    private val viewModel:MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuraViewerBinding.inflate(inflater,container,false)
        setTextSource()
        setReciteMode()
        return binding.root
    }

    private fun setTextSource(){
        val pages = mutableListOf(
            Constant._367, Constant._368, Constant._369,
            Constant._370, Constant._371, Constant._372, Constant._373,
            Constant._374, Constant._375, Constant._376, Constant._446,
            Constant._447, Constant._448, Constant._449, Constant._450,
            Constant._451, Constant._452, Constant._520, Constant._521,
            Constant._522, Constant._523, Constant._524, Constant._525,
            Constant._526, Constant._527, Constant._528, Constant._529,
            Constant._530, Constant._531, Constant._532, Constant._533,
            Constant._534, Constant._535, Constant._536, Constant._537
            )
        val adap= ViewAdapter(pages)
        binding.pageViewer.apply {
            adapter = adap
        }
        binding.pageViewer.currentItem = viewModel.chosenPage

    }
    private fun setReciteMode(){
        binding.recitemodebt.setOnClickListener{
            viewModel.chosenPage = binding.pageViewer.currentItem
            MainViewModel.sura.value = SpannableString("")
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,RecordingHandlerFragment())?.commit()
        }
    }

}