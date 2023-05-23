package com.example.helgather.src.Main.chatting

import android.annotation.SuppressLint
import android.util.Log
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class StompManager {

    private lateinit var mStompClient: StompClient

    fun connectStomp(chatId: Int) : StompClient {
        val okhttpClient = OkHttpClient.Builder().build()
        val webSocketListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocket", "onOpen")
            }
        }
        // WebSocket 연결을 설정합니다.
        val webSocket = okhttpClient.newWebSocket(Request.Builder().url("ws://13.124.19.96:8080/ws").build(), webSocketListener)

        // StompClient를 생성하고 설정합니다.
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://13.124.19.96:8080/ws")
        mStompClient.connect()

        return mStompClient
    }


    fun disconnectStomp() {
        mStompClient.disconnect()
    }

    fun sendMessage(chatId: Int, userId: Int, message: String, time: String, userProfile: Int, first: Boolean) {
        // JSON 형식의 메시지를 생성합니다.
        val jsonMessage = """
            {
                "userId": $userId,
                "message": "$message",
                "time": "$time",
                "userProfile": $userProfile,
                "first": $first
            }
        """.trimIndent()

        // 메시지를 보냅니다.
        mStompClient.send("/pub/chatroom/$chatId", jsonMessage).subscribe()
    }
}