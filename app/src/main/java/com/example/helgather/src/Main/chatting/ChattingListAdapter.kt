package com.example.helgather.src.Main.chatting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helgather.databinding.ChattingRoomsListBinding

class ChattingListAdapter(var chatList : List<ChattingListResult>) : RecyclerView.Adapter<ChattingListAdapter.ChatViewHolder>() {

    private lateinit var binding : ChattingRoomsListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        binding = ChattingRoomsListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    inner class ChatViewHolder(val binding : ChattingRoomsListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chatList : ChattingListResult){
            Glide.with(itemView).load(chatList.Profile).circleCrop().into(binding.ivChattingProfile)
            binding.tvChattingId.text = chatList.id
            binding.tvChattingMessage.text = chatList.preview
            binding.tvChattingWhen.text = chatList.time
        }
    }
}