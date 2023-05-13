package com.example.helgather.src.Main.chatting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {
    val messageList = MutableLiveData<List<ChattingMessageResult>>()

    fun addMessage(message: ChattingMessageResult) {
        val updatedMessages = messageList.value?.toMutableList() ?: mutableListOf()
        updatedMessages.add(0, message)
        messageList.value = updatedMessages
    }
}