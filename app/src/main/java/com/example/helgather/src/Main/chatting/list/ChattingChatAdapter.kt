package com.example.helgather.src.Main.chatting.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.databinding.ChattingMineListBinding
import com.example.helgather.databinding.ChattingOtherListBinding
import com.example.helgather.src.Main.chatting.ChatViewModel
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.example.helgather.util.TimeConversion

class ChattingChatAdapter(var messages: MutableList<ChatMessageResult>,private val memberId: Int)
    : RecyclerView.Adapter<ChattingChatAdapter.MessageViewHolder>() {


    companion object {
        private const val VIEW_TYPE_MINE = 0
        private const val VIEW_TYPE_OTHER = 1
    }



    override fun getItemViewType(position: Int): Int = when (messages[position].userId) {
        memberId -> VIEW_TYPE_MINE
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
                    val wrappedText = filterAndWrapText(chatMessage.message,25)
                    binding.tvChattingMine.text = wrappedText
                    binding.tvChattingMineWhen.text = TimeConversion.datetoTime(chatMessage.time)
                }
                is ChattingOtherListBinding -> {
                    val wrappedText = filterAndWrapText(chatMessage.message,25)
                    binding.tvChattingOtherMessage.text = wrappedText
                    binding.tvChattingOtherWhen.text = TimeConversion.datetoTime(chatMessage.time)
                    Glide.with(itemView).load(chatMessage.userProfile)
                        .placeholder(R.drawable.ic_btm_profile) // 로드되기 전에 표시할 이미지
                        .error(R.drawable.ic_btm_profile)
                        .into(binding.ivChattingOtherProfile)
//                    if (chatMessage.first) {
//                        Log.d("testtestcat","----")
//                        Glide.with(itemView).load(chatMessage.userProfile)
//                            .placeholder(R.drawable.ic_btm_profile) // 로드되기 전에 표시할 이미지
//                            .error(R.drawable.ic_btm_profile)
//                            .into(binding.ivChattingOtherProfile)
//                    } else {
//                        binding.ivChattingOtherProfile.visibility = View.GONE
//                    }
                }
            }
        }
    }
    private fun filterAndWrapText(text: String, maxLineLength: Int): String {
        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var currentLine = ""

        for (word in words) {
            if ((currentLine + word).length <= maxLineLength) {
                currentLine += "$word "
            } else {
                lines.add(currentLine.trim())
                currentLine = "$word "
            }
        }

        if (currentLine.isNotEmpty()) {
            lines.add(currentLine.trim())
        }

        return lines.joinToString("\n")
    }

}


