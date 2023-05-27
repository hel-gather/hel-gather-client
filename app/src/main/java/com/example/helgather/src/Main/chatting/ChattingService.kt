package com.example.helgather.src.Main.chatting

import android.util.Log
import com.example.helgather.config.ApplicationClass
import com.example.helgather.src.Main.chatting.models.ChatMessageResponse
import com.example.helgather.src.Main.chatting.models.ChatRoomResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChattingService(val chattingFragmentInterface: ChattingFragmentInterface) {

    fun tryGetChattingRoom(memberId : Int){
        val chattingRetrofitInterface = ApplicationClass.sRetrofit.create(ChattingRetrofitInterface::class.java)
        chattingRetrofitInterface.getChatRoom(id = memberId).enqueue(object : Callback<ChatRoomResponse>{
            override fun onResponse(
                call: Call<ChatRoomResponse>,
                response: Response<ChatRoomResponse>
            ) {
                Log.d("chattingd","${response.errorBody().toString()} ")
                Log.d("chattinge","${response.body().toString()} ")
                if (response.isSuccessful) {
                    chattingFragmentInterface.onGetChatRoomSuccess(response.body() as ChatRoomResponse)
                } else {
                    chattingFragmentInterface.onGetChatRoomSuccess(ChatRoomResponse(emptyList()))
                }
            }

            override fun onFailure(call: Call<ChatRoomResponse>, t: Throwable) {
                chattingFragmentInterface.onGetChatRoomFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetChattingMessage(chatId : Int,memberId : Int){
        val chattingRetrofitInterface = ApplicationClass.sRetrofit.create(ChattingRetrofitInterface::class.java)
        chattingRetrofitInterface.getChatMessage(id = chatId, memberId = memberId).enqueue(object  : Callback<ChatMessageResponse>{
            override fun onResponse(
                call: Call<ChatMessageResponse>,
                response: Response<ChatMessageResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("testtest1",response.body().toString())
                    chattingFragmentInterface.onGetChatMessageSuccess(response.body() as ChatMessageResponse)
                } else {
                    Log.d("testtest2",response.errorBody().toString())
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr,ChatMessageResponse::class.java)
                    val getMessageResult = errorResponse.chatMessageResult
                    val getMessageResponse = ChatMessageResponse(false,errorResponse.code,errorResponse.message,getMessageResult)
                    chattingFragmentInterface.onGetChatMessageSuccess(getMessageResponse)
                }
            }

            override fun onFailure(call: Call<ChatMessageResponse>, t: Throwable) {
                chattingFragmentInterface.onGetChatMessageFailure(t.message ?: "통신 오류")
            }
        })
    }
}