package com.example.helgather.src.Main.chatting.models

data class ChatMessageResult(
    val first: Boolean,
    val message: String,
    val time: String,
    val userId: Int,
    val userProfile: Int
)