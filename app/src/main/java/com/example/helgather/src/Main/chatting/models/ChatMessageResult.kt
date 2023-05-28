package com.example.helgather.src.Main.chatting.models

data class ChatMessageResult(
    val userId: Int,
    val message: String,
    val time: String,
    val userProfile: String,
    val first: Boolean
)