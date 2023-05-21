package com.example.helgather.src.Main.chatting

import com.example.helgather.src.Main.chatting.models.ChatRoomResponse

interface ChattingFragmentInterface {
    fun onGetChatRoomSuccess(response : ChatRoomResponse)

    fun onGetChatRoomFailure(message : String)
}