package com.example.helgather.src.Main.chatting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.example.helgather.src.Main.chatting.models.WSMessageResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.Socket

class ChatViewModel : ViewModel() {
    val _messageList = MutableLiveData<List<ChatMessageResult>>()
    val messageList : LiveData<List<ChatMessageResult>> = _messageList

    fun addMessage(message: ChatMessageResult) {
        val updatedMessages = _messageList.value?.toMutableList() ?: mutableListOf()
        updatedMessages.add(0, message)
        _messageList.value = updatedMessages
    }

    private var socket: Socket? = null
    private var writer: BufferedWriter? = null

    init {
        connectToServer()
    }

    fun connectToServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                socket = Socket("ws://13.124.19.96:8080/ws/sub/chatroom/", 1)
                writer = BufferedWriter(OutputStreamWriter(socket?.getOutputStream()))

                handleConnection(socket)
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error connecting to server", e)
            }
        }
    }

    private suspend fun handleConnection(socket: Socket?) {
        val reader = socket?.getInputStream()?.bufferedReader()
        val gson = Gson()
        withContext(Dispatchers.IO) {
            while (socket?.isConnected == true) {
                val receivedMessage = reader?.readLine()
                if (receivedMessage != null) {
                    withContext(Dispatchers.Main) {
                        val chatMessageResult = gson.fromJson(receivedMessage, ChatMessageResult::class.java)
                        addMessage(chatMessageResult)
                    }
                } else {
                    break
                }
            }
            closeConnection()
        }
    }

    private fun closeConnection() {
        socket?.close()
        writer?.close()
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                writer?.apply {
                    write(message)
                    newLine()
                    flush()
                }
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error sending message", e)
            }
        }
    }

}