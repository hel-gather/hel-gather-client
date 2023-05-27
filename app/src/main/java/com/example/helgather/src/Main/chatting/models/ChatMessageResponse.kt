package com.example.helgather.src.Main.chatting.models

import com.example.helgather.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ChatMessageResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val chatMessageResult : MutableList<ChatMessageResult>
)