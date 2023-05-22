package com.example.helgather.src.Main.chatting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helgather.src.Main.chatting.models.ChatMessageResult

class ChatViewModel : ViewModel() {
    val messageList = MutableLiveData<List<ChatMessageResult>>()

    fun addMessage(message: ChatMessageResult) {
        val updatedMessages = messageList.value?.toMutableList() ?: mutableListOf()
        updatedMessages.add(0, message)
        messageList.value = updatedMessages
    }
}