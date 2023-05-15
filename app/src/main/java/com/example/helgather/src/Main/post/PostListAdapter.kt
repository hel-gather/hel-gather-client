package com.example.helgather.src.Main.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helgather.databinding.PostWritingListBinding
import com.example.helgather.util.TimeConversion

class PostListAdapter(var postListResult: List<PostListResult>) : RecyclerView.Adapter<PostListAdapter.ChatViewHolder>() {

    private lateinit var binding : PostWritingListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        binding = PostWritingListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        holder.bind(postListResult[position])
    }

    override fun getItemCount(): Int {
        return postListResult.size
    }

    inner class ChatViewHolder(binding : PostWritingListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(postListResult: PostListResult){
            binding.tvWritingId.text = postListResult.name
            binding.tvWritingTitle.text = postListResult.title
            binding.tvWritingWhen.text = TimeConversion.intervalBetweenDateText(postListResult.time)
            binding.tvWritingComment.text = "[" + postListResult.commentCount + "]"
        }
    }
}