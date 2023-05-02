package com.example.helgather.src.First

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helgather.databinding.StartDeadliftListBinding

class StartDeadliftApdater(var deadliftList : List<Int>) : RecyclerView.Adapter<StartDeadliftApdater.DeadliftViewHolder>() {

    private lateinit var binding : StartDeadliftListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeadliftViewHolder {
        binding = StartDeadliftListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return DeadliftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeadliftViewHolder, position: Int) {
        holder.bind(deadliftList[position])
    }

    override fun getItemCount(): Int {
        return deadliftList.size
    }

    inner class DeadliftViewHolder(val binding : StartDeadliftListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(deadliftList: Int){
            Glide.with(itemView).load(deadliftList).into(binding.ivStartDeadlift)
        }
    }
}