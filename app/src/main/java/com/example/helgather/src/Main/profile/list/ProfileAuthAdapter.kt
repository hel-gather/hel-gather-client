package com.example.helgather.src.Main.profile.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.example.helgather.databinding.ProfileAuthListBinding
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResult

class ProfileAuthAdapter(var getTodayExerciseResult: List<GetTodayExerciseResult>) : RecyclerView.Adapter<ProfileAuthAdapter.TodayExerciseViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayExerciseViewholder {
        val binding = ProfileAuthListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return TodayExerciseViewholder(binding)
    }

    override fun onBindViewHolder(holder: TodayExerciseViewholder, position: Int) {
        holder.bind(getTodayExerciseResult[position])
    }

    override fun getItemCount(): Int {
        return getTodayExerciseResult.size
    }

    inner class TodayExerciseViewholder(val binding : ProfileAuthListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(getTodayExerciseResult: GetTodayExerciseResult){

            Glide.with(itemView)
                .load(getTodayExerciseResult.imageUrl)
                .centerCrop().into(binding.ivProfileAuthPicture)
        }
    }
}