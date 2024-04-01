package com.example.quranapplication.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quranapplication.R
import com.example.quranapplication.data.Source
import com.example.quranapplication.data.Sura
import com.example.quranapplication.databinding.IndexViewBinding
import com.example.quranapplication.ui.fragment.SuraViewerFragment


class IndexAdapter(val sura:List<Sura>,val onItemClicked: (Sura) -> Unit):RecyclerView.Adapter<IndexAdapter.IndexHolder>() {

    inner class IndexHolder(val binding: IndexViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndexHolder {
        val binding = IndexViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndexHolder(binding)
    }

    override fun getItemCount(): Int = sura.size

    override fun onBindViewHolder(holder: IndexHolder, position: Int) {
        holder.binding.apply {
            suranametv.text = sura[position].name
            suranumtv.text = sura[position].number.toString()
            if(sura[position].source == Source.Madani){
                makkiMadani.setImageResource(R.drawable.mosque)
            }
        }
        holder.itemView.setOnClickListener{
            onItemClicked(sura[position])
        }
    }

}