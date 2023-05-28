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
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompHeader

class StompManager {

    private lateinit var mStompClient: StompClient

    fun connectStomp(chatId: Int) : StompClient {
        val okhttpClient = OkHttpClient.Builder().build()
//        //집에서 작업할때
//        val webSocketUrl = "ws://13.124.19.96:8080/ws"

        //다른 공간 ip주소 임시
        val webSocketUrl = "ws://121.135.181.61:8080/ws"
        // WebSocket 연결을 설정합니다.
        val webSocket = okhttpClient.newWebSocket(Request.Builder().url(webSocketUrl).build(), object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocket", "onOpen")
                // WebSocket 연결이 성공하면 StompClient를 생성하고 연결
                mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, webSocketUrl)

            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.d("WebSocket", "onFailure: ${t.message}")
                // WebSocket 연결 실패 처리를 수행합니다.
            }
        })
        // 위의 WebSocket 연결 코드와 별개로 mStompClient를 초기화
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, webSocketUrl)

        return mStompClient
    }


    fun disconnectStomp() {
        if(::mStompClient.isInitialized && mStompClient.isConnected) {
            Log.d("WebSocket", "onClosed")
            mStompClient.disconnect()
        }
    }

    fun sendMessage(roomId: Int, chatMessageResult: ChatMessageResult) {
        // JSON 형식의 메시지를 생성
        val jsonMessage = """
            {
                "userId": ${chatMessageResult.userId},
                "message": "${chatMessageResult.message}",
                "time": "${chatMessageResult.time}",
                "userProfile": "${chatMessageResult.userProfile}",
                "first": ${chatMessageResult.first}
            }
        """.trimIndent()

        val headers = mutableMapOf<String, String>()
        val authorization = sSharedPreferences.getString("Authorization","")
        headers["Authorization"] = "Bearer $authorization"

        val stompHeaders = headers.map { (key, value) ->
            StompHeader(key, value)
        }

        mStompClient.connect(stompHeaders)

        // 메시지를 보냅니다.
        mStompClient.send("/pub/chats/$roomId", jsonMessage).subscribe()
    }
}

//                val headers = mutableMapOf<String, String>()
//                val authorization = sSharedPreferences.getString("Authorization","")
//                headers["Authorization"] = "Bearer $authorization"
//
//                val stompHeaders = headers.map { (key, value) ->
//                    StompHeader(key, value)
//                }
//
//                mStompClient.connect(stompHeaders)
