package com.example.quranapplication.ui.fragment

import android.os.Bundle
import android.text.SpannableString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quranapplication.R
import com.example.quranapplication.data.Source
import com.example.quranapplication.data.Sura
import com.example.quranapplication.databinding.FragmentIndexBinding
import com.example.quranapplication.other.Constant
import com.example.quranapplication.ui.recyclerview.IndexAdapter
import com.example.quranapplication.ui.viewmodel.MainViewModel

class IndexFragment : Fragment() {
    private lateinit var binding:FragmentIndexBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndexBinding.inflate(inflater,container,false)
        val suraToView = mutableListOf(
            Sura(resources.getString(R.string.alsho3raa),26,Source.Makki,0),
            Sura(resources.getString(R.string.alsaafat),37,Source.Makki,10),
            Sura(resources.getString(R.string.alzariat),51,Source.Makki,17),
            Sura(resources.getString(R.string.altor),52,Source.Makki,20),
            Sura(resources.getString(R.string.alnagm),53,Source.Makki,23),
            Sura(resources.getString(R.string.alkamer),54,Source.Makki,25),
            Sura(resources.getString(R.string.alrahman),55,Source.Madani,28),
            Sura(resources.getString(R.string.alwaqiha),56,Source.Makki,31)
        )
        val adap= IndexAdapter(suraToView) { sura ->
            MainViewModel.chosenPage = when (sura.number) {
                26 -> {
                    MainViewModel.chosenSura = Constant.alsho3raa
                    suraToView[0].startingPage
                }
                37 -> {
                    MainViewModel.chosenSura = Constant.alsafat
                    suraToView[1].startingPage
                }
                51 -> {
                    MainViewModel.chosenSura = Constant.alzariat
                    suraToView[2].startingPage
                }
                52 -> {
                    MainViewModel.chosenSura = Constant.altor
                    suraToView[3].startingPage
                }
                53 -> {
                    MainViewModel.chosenSura = Constant.alnagm
                    suraToView[4].startingPage
                }
                54 -> {
                    MainViewModel.chosenSura = Constant.alkamer
                    suraToView[5].startingPage
                }
                55 -> {
                    MainViewModel.chosenSura = Constant.alrahman
                    suraToView[6].startingPage
                }
                56 -> {
                    MainViewModel.chosenSura = Constant.alwaq3a
                    suraToView[7].startingPage
                }
                else -> {
                    suraToView[8].startingPage
                }
            }
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,SuraViewerFragment())?.commit()
        }
        binding.indexrecyclerView.apply {
            adapter = adap
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }
}