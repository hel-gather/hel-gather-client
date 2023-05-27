package com.example.helgather.src.Main.chatting.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.databinding.ChattingMineListBinding
import com.example.helgather.databinding.ChattingOtherListBinding
import com.example.helgather.src.Main.chatting.ChatViewModel
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.example.helgather.util.TimeConversion

class ChattingChatAdapter(var messages: MutableList<ChatMessageResult>,private val memberId: Int)
    : RecyclerView.Adapter<ChattingChatAdapter.MessageViewHolder>() {

//    private val messages = mutableListOf<ChatMessageResult>()


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

//    fun addMessage(message: ChatMessageResult) {
//        val newList = messages.toMutableList()
//        newList.add(0, message)
//        messages = newList
//        notifyItemInserted(0)
//    }

    fun addMessage(message: ChatMessageResult) {
        messages.add(messages.size-1, message)
        notifyItemInserted(messages.size - 1)
    }

    fun setChatMessages(newMessages: List<ChatMessageResult>) {
        messages = newMessages.toMutableList()
        notifyDataSetChanged()
    }

    fun submitList(newList: List<ChatMessageResult>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(messages, newList))
        diffResult.dispatchUpdatesTo(this)
        messages = newList.toMutableList() // 기존 리스트 업데이트
    }

    class DiffCallback(private val oldList: List<ChatMessageResult>, private val newList: List<ChatMessageResult>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.time == newItem.time // 시간을 기준으로 아이템이 동일한지 비교
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem // 아이템의 내용이 동일한지 비교 (equals 메서드 호출)
        }
    }

}


