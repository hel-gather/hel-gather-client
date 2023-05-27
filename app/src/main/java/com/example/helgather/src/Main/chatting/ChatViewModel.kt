package com.example.helgather.src.Main.chatting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.google.gson.Gson

class ChatViewModel : ViewModel() {
    private val _messageList = MutableLiveData<MutableList<ChatMessageResult>>()
    val messageList: LiveData<MutableList<ChatMessageResult>> = _messageList

    init {
        _messageList.value = mutableListOf()
    }

    fun addMessage(message: ChatMessageResult) {
        _messageList.value?.add(message)
        _messageList.value = _messageList.value
    }

    fun addMessageList(messages: List<ChatMessageResult>) {
        val currentMessages = _messageList.value ?: mutableListOf()
        currentMessages.addAll(messages)
        _messageList.value = currentMessages
    }

    fun addMessageFromJson(jsonString: String) {
        val gson = Gson()
        val message = gson.fromJson(jsonString, ChatMessageResult::class.java)
        addMessage(message)
    }
}
