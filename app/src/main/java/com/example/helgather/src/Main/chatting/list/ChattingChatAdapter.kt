package com.example.helgather.src.Main.chatting.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.helgather.databinding.ChattingMineListBinding
import com.example.helgather.databinding.ChattingOtherListBinding
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.example.helgather.util.TimeConversion

class ChattingChatAdapter(var chattingMessageResult: List<ChatMessageResult>)
    : RecyclerView.Adapter<ChattingChatAdapter.MessageViewHolder>() {

    private val messages = mutableListOf<ChatMessageResult>()

    companion object {
        private const val VIEW_TYPE_MINE = 0
        private const val VIEW_TYPE_OTHER = 1
    }

    fun addMessage(message : ChatMessageResult){
        messages.add(0,message)
        notifyItemInserted(0)
    }

    override fun getItemViewType(position: Int): Int = when (messages[position].userId) {
        0 -> VIEW_TYPE_MINE
        else -> VIEW_TYPE_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType){
            VIEW_TYPE_MINE -> ChattingMineListBinding.inflate(inflater,parent,false)
            else -> ChattingOtherListBinding.inflate(inflater,parent,false)
        }
        return MessageViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class MessageViewHolder(private val binding : ViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chatMessage: ChatMessageResult){
            when(binding){
                is ChattingMineListBinding ->{
                    binding.tvChattingMine.text = chatMessage.message
                    binding.tvChattingMineWhen.text = TimeConversion.datetoTime(chatMessage.time)
                }
                is ChattingOtherListBinding -> {
                    binding.tvChattingOtherMessage.text = chatMessage.message
                    binding.tvChattingOtherWhen.text = TimeConversion.datetoTime(chatMessage.time)
                    if(chatMessage.first){
                        Glide.with(itemView).load(chatMessage.userProfile)
                            .circleCrop().into(binding.ivChattingOtherProfile)
                    }else{
                        binding.ivChattingOtherProfile.visibility = View.GONE
                    }
                }
            }
        }
    }

}