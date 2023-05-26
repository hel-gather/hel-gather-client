package com.example.helgather.src.Main.chatting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.google.gson.Gson

class ChatViewModel : ViewModel() {

    val _messageList = MutableLiveData<List<ChatMessageResult>>()
    val messageList : LiveData<List<ChatMessageResult>> = _messageList

    fun addMessage(message: ChatMessageResult) {
        val updatedMessages = _messageList.value?.toMutableList() ?: mutableListOf()
        // 중복된 메시지인지 체크 후 추가
        if (!updatedMessages.contains(message)) {
            updatedMessages.add(0, message)
        }
        _messageList.postValue(updatedMessages)
    }


    // ChatViewModel 클래스에 추가
    fun addMessageFromJson(messageJson: String) {
        val message = Gson().fromJson(messageJson, ChatMessageResult::class.java)
        addMessage(message)
    }

    fun isMessageExists(message: ChatMessageResult): Boolean {
        val existingMessages = messageList.value ?: emptyList()
        return existingMessages.any { it.userId == message.userId } // 여기에서 id가 메시지의 고유 식별자라고 가정
    }

}