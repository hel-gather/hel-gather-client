package com.example.helgather.src.Main.profile.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helgather.databinding.ProfileSbdListBinding
import com.example.helgather.src.Main.profile.model.GetSBDResult
import com.example.helgather.src.Main.profile.model.ProfileSBDTest

@Suppress("DEPRECATION")
class ProfileSBDAdapter(var getSBDResult: List<GetSBDResult>,private val clickListener : ProfileSBDClickListener<GetSBDResult>)
    : RecyclerView.Adapter<ProfileSBDAdapter.SBDViewHolder>() {

    interface ProfileSBDClickListener<T>{
        fun onUploadClick(view : ProfileSbdListBinding, pos : Int)
        fun onVideoClick(view : ProfileSbdListBinding,pos : Int)
    }

    private lateinit var binding : ProfileSbdListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SBDViewHolder {
        binding = ProfileSbdListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SBDViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SBDViewHolder, position: Int) {
        holder.bind(getSBDResult[position])

        holder.binding.btnSbdUpload.setOnClickListener {
            clickListener.onUploadClick(holder.binding,position)
        }
        holder.binding.ivSbdPreview.setOnClickListener {
            clickListener.onVideoClick(holder.binding,position)
        }
    }

    override fun getItemCount(): Int {
        return getSBDResult.size
    }

    inner class SBDViewHolder(val binding : ProfileSbdListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(getSBDResult: GetSBDResult){
            binding.tvSbdTitle.text = getSBDResult.category
            Glide.with(itemView).load(getSBDResult.thumbNailUrl).into(binding.ivSbdPreview)
        }
    }
}