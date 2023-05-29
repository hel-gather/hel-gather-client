package com.example.helgather.src.Main.recruit.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helgather.databinding.RecruitWriteListBinding
import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResult
import com.example.helgather.util.TimeConversion

class RecruitListAdapter(var postListResult: List<GetRecruitLocationResult>,private val clickListener : RecruitListItemClickListener)
    : RecyclerView.Adapter<RecruitListAdapter.ChatViewHolder>() {

    interface RecruitListItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var binding : RecruitWriteListBinding



    interface onV

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        binding = RecruitWriteListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(postListResult[position])

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return postListResult.size
    }

    inner class ChatViewHolder(binding : RecruitWriteListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(postListResult: GetRecruitLocationResult){
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