package com.example.helgather.src.Main.chatting.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helgather.R
import com.example.helgather.databinding.ChattingRoomsListBinding
import com.example.helgather.src.Main.chatting.models.ChatRoomResult
import com.example.helgather.util.TimeConversion

class ChattingRoomAdapter(var chatList : List<ChatRoomResult>, private val clickListener: chatRoomClickListener<ChatRoomResult>)
    : RecyclerView.Adapter<ChattingRoomAdapter.ChatViewHolder>() {

    private lateinit var binding : ChattingRoomsListBinding

    interface chatRoomClickListener<T>{
        fun onRoomClick(view : ChattingRoomsListBinding, pos : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        binding = ChattingRoomsListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])

        holder.binding.root.setOnClickListener {
            clickListener.onRoomClick(holder.binding,position)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    inner class ChatViewHolder(val binding : ChattingRoomsListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chattingRoom : ChatRoomResult){
            Glide.with(itemView).load(chattingRoom.image)
                .placeholder(R.drawable.ic_blank_profile) // 로딩 중에 표시할 이미지
                .error(R.drawable.ic_blank_profile) // 로딩 실패 시 표시할 이미지
                .circleCrop().into(binding.ivChattingProfile)
            binding.tvChattingId.text = chattingRoom.title
            binding.tvChattingMessage.text = chattingRoom.preview
            binding.tvChattingWhen.text = TimeConversion.intervalBetweenDateText(chattingRoom.time)
        }
    }
}