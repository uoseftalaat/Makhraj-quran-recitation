package com.example.quranapplication.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quranapplication.R
import com.example.quranapplication.data.Action
import com.example.quranapplication.data.Setting
import com.example.quranapplication.databinding.EditTextSizeBinding
import com.example.quranapplication.databinding.FragmentSettingBinding
import com.example.quranapplication.ui.recyclerview.SettingAdapter
import com.example.quranapplication.ui.viewmodel.MainViewModel
import com.yariksoffice.lingver.Lingver


class SettingFragment : Fragment() {

    private lateinit var binding:FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater,container,false)
        val settings = setSettings()
        val adap = SettingAdapter(settings){setting ->
            when(setting.action){
                Action.EDIT_SIZE -> {
                    showEditSizeDialog(container)
                }
                Action.LANGUAGE_SWITCH ->{
                    if(Lingver.getInstance().getLanguage() == "en") {
                        Lingver.getInstance().setLocale(requireContext(), "ar")
                        requireActivity().recreate()
                    }
                    else{
                        Lingver.getInstance().setLocale(requireContext(), "en")
                        requireActivity().recreate()
                    }
                }
            }
        }

        binding.settingrecyclerView.apply {
            adapter = adap
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    private fun setSettings():List<Setting>{
        return mutableListOf(
            Setting(R.string.font_size_setting,R.drawable.scale,Action.EDIT_SIZE),
            Setting(R.string.Language_switcher,R.drawable.translator,Action.LANGUAGE_SWITCH)
        )
    }

    private fun showEditSizeDialog(container: ViewGroup?){
        val binding:EditTextSizeBinding = EditTextSizeBinding.inflate(layoutInflater,container,false)
        val builder = AlertDialog.Builder(requireContext())

        with(builder){
            setTitle(resources.getString(R.string.edit_text_size_title))
            setView(binding.root)
            setPositiveButton(resources.getString(R.string.button_apply)){ _,_ ->
                //set only to close the window without crashing
            }
            show()
        }
        binding.textsizeSmall.setOnClickListener{
            MainViewModel.textSize.value = 50f
        }
        binding.textsizeNormal.setOnClickListener{
            MainViewModel.textSize.value= 90f
        }
        binding.textsizeLarge.setOnClickListener{
            MainViewModel.textSize.value = 140f
        }
    }

}