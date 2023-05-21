package com.example.helgather.src.Main.chatting

import com.example.helgather.src.Main.chatting.models.ChatMessageResponse
import com.example.helgather.src.Main.chatting.models.ChatRoomResponse

interface ChattingFragmentInterface {
    fun onGetChatRoomSuccess(response : ChatRoomResponse)
    fun onGetChatRoomFailure(message : String)
    fun onGetChatMessageSuccess(response : ChatMessageResponse)
    fun onGetChatMessageFailure(message: String)
}