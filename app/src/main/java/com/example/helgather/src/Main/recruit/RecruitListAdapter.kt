package com.example.helgather.src.Main.recruit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helgather.databinding.RecruitWriteListBinding
import com.example.helgather.src.Main.recruit.models.GetRecruitAllResult
import com.example.helgather.util.TimeConversion

class RecruitListAdapter(var postListResult: List<GetRecruitAllResult>) : RecyclerView.Adapter<RecruitListAdapter.ChatViewHolder>() {

    private lateinit var binding : RecruitWriteListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        binding = RecruitWriteListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        holder.bind(postListResult[position])
    }

    override fun getItemCount(): Int {
        return postListResult.size
    }

    inner class ChatViewHolder(binding : RecruitWriteListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(postListResult: GetRecruitAllResult){
            binding.tvWritingId.text = postListResult.nickname
            binding.tvWritingTitle.text = postListResult.title
            if(postListResult.createdAt.isNullOrEmpty()){
                binding.tvWritingWhen.text =""
            }else{
                binding.tvWritingWhen.text = TimeConversion.intervalBetweenDateText(postListResult.createdAt.toString())
            }
        }
    }
}