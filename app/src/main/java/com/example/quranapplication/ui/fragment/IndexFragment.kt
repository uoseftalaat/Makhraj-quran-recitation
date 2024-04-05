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
        val suraToview = mutableListOf(
            Sura(resources.getString(R.string.alsho3raa),26,Source.Makki),
            Sura(resources.getString(R.string.alsaafat),37,Source.Makki),
            Sura(resources.getString(R.string.alzariat),51,Source.Makki),
            Sura(resources.getString(R.string.alnagm),53,Source.Makki),
            Sura(resources.getString(R.string.alkamer),54,Source.Makki),
            Sura(resources.getString(R.string.alrahman),55,Source.Madani),
            Sura(resources.getString(R.string.alwaqiha),56,Source.Makki)
        )
        val adap= IndexAdapter(suraToview) { sura ->
            MainViewModel.sura.value = when (sura.number) {
                26 -> SpannableString( Constant.alsho3raa)
                37 -> SpannableString( Constant.alsafat)
                51 -> SpannableString( Constant.alzariat)
                53 -> SpannableString( Constant.alnagm)
                54 -> SpannableString( Constant.alkamer)
                55 -> SpannableString( Constant.alrahman)
                56 -> SpannableString( Constant.alwaq3a)
                else -> SpannableString( Constant.alfateha)
            }
        }
        binding.indexrecyclerView.apply {
            adapter = adap
            layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }
}