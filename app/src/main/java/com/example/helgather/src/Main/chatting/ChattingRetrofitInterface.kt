package com.example.helgather.src.Main.chatting

import com.example.helgather.src.Main.chatting.models.ChatMessageResponse
import com.example.helgather.src.Main.chatting.models.ChatRoomResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChattingRetrofitInterface {

    @GET("/chats/")
    fun getChatRoom(@Query("member")id : Int) : Call<ChatRoomResponse>

    @GET("/chats/")
    fun getChatMessage(@Query("id")id : Int
                       ,@Query("member")memberId : Int)
    : Call<ChatMessageResponse>

}