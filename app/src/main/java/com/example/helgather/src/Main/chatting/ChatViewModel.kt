package com.example.helgather.src.Main.chatting

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.Socket

class ChatViewModel : ViewModel() {

    val _messageList = MutableLiveData<List<ChatMessageResult>>()
    val messageList : LiveData<List<ChatMessageResult>> = _messageList

//    fun addMessage(message: ChatMessageResult) {
//        val updatedMessages = _messageList.value?.toMutableList() ?: mutableListOf()
//        updatedMessages.add(0, message)
//        _messageList.value = updatedMessages
//    }

    fun addMessage(message: ChatMessageResult) {
        val updatedMessages = _messageList.value?.toMutableList() ?: mutableListOf()
        updatedMessages.add(0, message)
        _messageList.postValue(updatedMessages)
    }


    // ChatViewModel 클래스에 추가
    fun addMessageFromJson(messageJson: String) {
        val message = Gson().fromJson(messageJson, ChatMessageResult::class.java)
        addMessage(message)
    }

}