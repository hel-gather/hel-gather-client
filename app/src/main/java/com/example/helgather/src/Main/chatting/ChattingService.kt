package com.example.helgather.src.Main.chatting

import com.example.helgather.config.ApplicationClass
import com.example.helgather.src.Main.chatting.models.ChatMessageResponse
import com.example.helgather.src.Main.chatting.models.ChatRoomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class ChattingService(val chattingFragmentInterface: ChattingFragmentInterface) {

    fun tryGetChattingRoom(user_id : Int){
        val chattingRetrofitInterface = ApplicationClass.sRetrofit.create(ChattingRetrofitInterface::class.java)
        chattingRetrofitInterface.getChatRoom(id = user_id).enqueue(object : Callback<ChatRoomResponse>{
            override fun onResponse(
                call: Call<ChatRoomResponse>,
                response: Response<ChatRoomResponse>
            ) {
                chattingFragmentInterface.onGetChatRoomSuccess(response.body() as ChatRoomResponse)
            }

            override fun onFailure(call: Call<ChatRoomResponse>, t: Throwable) {
                chattingFragmentInterface.onGetChatRoomFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetChattingMessage(id : Int){
        val chattingRetrofitInterface = ApplicationClass.sRetrofit.create(ChattingRetrofitInterface::class.java)
        chattingRetrofitInterface.getChatMessage(id = id).enqueue(object  : Callback<ChatMessageResponse>{
            override fun onResponse(
                call: Call<ChatMessageResponse>,
                response: Response<ChatMessageResponse>
            ) {
                chattingFragmentInterface.onGetChatMessageSuccess(response.body() as ChatMessageResponse)
            }

            override fun onFailure(call: Call<ChatMessageResponse>, t: Throwable) {
                chattingFragmentInterface.onGetChatMessageFailure(t.message ?: "통신 오류")
            }
        })
    }
}