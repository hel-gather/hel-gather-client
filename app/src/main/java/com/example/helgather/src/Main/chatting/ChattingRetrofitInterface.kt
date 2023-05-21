package com.example.helgather.src.Main.chatting

import com.example.helgather.src.Main.chatting.models.ChatRoomResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChattingRetrofitInterface {

    @GET("/members/{id}/chatrooms")
    fun getChatRoom(@Path("id")id : Int) : Call<ChatRoomResponse>

}