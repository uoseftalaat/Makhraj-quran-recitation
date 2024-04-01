package com.example.quranapplication.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quranapplication.data.Setting
import com.example.quranapplication.databinding.SettingViewBinding


class SettingAdapter(val setting:List<Setting>, val onItemClicked: (Setting) -> Unit):RecyclerView.Adapter<SettingAdapter.SettingHolder>() {

    inner class SettingHolder(val binding: SettingViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingHolder {
        val binding = SettingViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingHolder(binding)
    }

    override fun getItemCount(): Int = setting.size

    override fun onBindViewHolder(holder: SettingHolder, position: Int) {
        holder.binding.inputSize.text = holder.binding.root.context.resources.getString(setting[position].settingName)
        holder.binding.settingIcon.setImageResource(setting[position].imageSource)
        holder.itemView.setOnClickListener {
            onItemClicked(setting[position])
        }
    }


}