package com.example.helgather.src.Main.chatting.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helgather.databinding.ChattingRoomsListBinding
import com.example.helgather.src.Main.chatting.ChattingListResult
import com.example.helgather.src.Main.chatting.models.ChatRoomResponseItem
import com.example.helgather.util.TimeConversion

class ChattingRoomAdapter(var chatList : List<ChatRoomResponseItem>) : RecyclerView.Adapter<ChattingRoomAdapter.ChatViewHolder>() {

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
        fun bind(chattingRoom : ChatRoomResponseItem){
            Glide.with(itemView).load(chattingRoom.profile).circleCrop().into(binding.ivChattingProfile)
            binding.tvChattingId.text = chattingRoom.id
            binding.tvChattingMessage.text = chattingRoom.preview
            binding.tvChattingWhen.text = TimeConversion.intervalBetweenDateText(chattingRoom.time)
        }
    }
}