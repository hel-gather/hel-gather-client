package com.example.helgather.src.Main.chatting.models

import com.google.gson.annotations.SerializedName

data class ChatMessageResponse(
    @SerializedName("result") val ChatMessageResponseItem : List<ChatMessageResponseItem>
)