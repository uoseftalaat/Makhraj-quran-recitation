package com.example.quranapplication.ui.recyclerview

import android.graphics.text.LineBreaker
import android.os.Build
import android.text.Layout
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quranapplication.databinding.SuraViewBinding

class ViewAdapter(private val suraToView:List<String>): RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SuraViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SuraViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = suraToView.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            suracontenttv.movementMethod = ScrollingMovementMethod()
            suracontenttv.setText(suraToView[position],true)
        }
    }

}