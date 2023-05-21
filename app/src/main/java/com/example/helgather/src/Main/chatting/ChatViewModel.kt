package com.example.helgather.src.Main.chatting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helgather.src.Main.chatting.models.ChatMessageResponseItem

class ChatViewModel : ViewModel() {
    val messageList = MutableLiveData<List<ChatMessageResponseItem>>()

    fun addMessage(message: ChatMessageResponseItem) {
        val updatedMessages = messageList.value?.toMutableList() ?: mutableListOf()
        updatedMessages.add(0, message)
        messageList.value = updatedMessages
    }
}