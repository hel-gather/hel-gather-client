package com.example.helgather.src.Main.chatting.models

import com.example.helgather.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ChatRoomResponse(
    @SerializedName("result")val ChatRoomResult :  List<ChatRoomResult>
) : BaseResponse()