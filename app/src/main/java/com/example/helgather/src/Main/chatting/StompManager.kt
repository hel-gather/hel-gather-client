package com.example.helgather.src.Main.chatting

import android.annotation.SuppressLint
import android.util.Log
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompHeader

class StompManager {

    private lateinit var mStompClient: StompClient

    fun connectStomp(chatId: Int) : StompClient {
        val okhttpClient = OkHttpClient.Builder().build()
        val webSocketUrl = "ws://13.124.19.96:8080/ws"

        mStompClient =  Stomp.over(Stomp.ConnectionProvider.OKHTTP, webSocketUrl)
        mStompClient.connect()
//
//        // WebSocket 연결을 설정합니다.
//        val webSocket = okhttpClient.newWebSocket(Request.Builder().url(webSocketUrl).build(), object : WebSocketListener() {
//            override fun onOpen(webSocket: WebSocket, response: Response) {
//                Log.d("WebSocket", "onOpen")
//                // WebSocket 연결이 성공하면 StompClient를 생성하고 연결
//                mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, webSocketUrl)
//
//            }
//
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//                Log.d("WebSocket", "onFailure: ${t.message}")
//                // WebSocket 연결 실패 처리를 수행합니다.
//            }
//        })
//        // 위의 WebSocket 연결 코드와 별개로 mStompClient를 초기화
//        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, webSocketUrl)

        return mStompClient
    }


    fun disconnectStomp() {
        if(::mStompClient.isInitialized && mStompClient.isConnected) {
            Log.d("WebSocket", "onClosed")
            mStompClient.disconnect()
        }
    }

    fun sendMessage(roomId: Int, chatMessageResult: ChatMessageResult) {
        Log.d("asdfasdfasdf",roomId.toString())
        // JSON 형식의 메시지를 생성
        val jsonObject = JSONObject().apply {
            put("userId", chatMessageResult.userId)
            put("message", chatMessageResult.message)
            put("time", chatMessageResult.time)
            put("first", chatMessageResult.first)
            put("userProfile", chatMessageResult.userProfile)
        }

        val jsonMessage = jsonObject.toString()

        // 메시지를 보냅니다.
        mStompClient.send("/pub/chatroom/$roomId", jsonMessage).subscribe({}, { throwable ->
            Log.e("StompManager", "Error occurred", throwable)
        })
    }

//    fun sendMessage(roomId: Int, chatMessageResult: ChatMessageResult) {
//        Log.d("asdfasdfasdf",roomId.toString())
//        // JSON 형식의 메시지를 생성
//        val jsonObject = JSONObject().apply {
//            put("userId", chatMessageResult.userId)
//            put("message", chatMessageResult.message)
//            put("time", chatMessageResult.time)
//            put("first", chatMessageResult.first)
//            put("userProfile", chatMessageResult.userProfile)
//        }
//
//        val jsonMessage = jsonObject.toString()
//
//        val headers = mutableMapOf<String, String>()
//        headers["asdf"] = "asdf "
//
//        val stompHeaders = headers.map { (key, value) ->
//            StompHeader(key, value)
//        }
//
//        mStompClient.connect(stompHeaders)
//
//        // 메시지를 보냅니다.
//        mStompClient.send("/pub/chatroom/$roomId", jsonMessage).subscribe()
//    }
}