package com.example.helgather.src.Main.chatting.models

import com.example.helgather.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ChatMessageResponse(
    @SerializedName("result") val ChatMessageResult : List<ChatMessageResult>
) : BaseResponse()