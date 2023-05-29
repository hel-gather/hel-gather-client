package com.example.helgather.src.Main.chatting

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentChattingChatBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Main.chatting.list.ChattingChatAdapter
import com.example.helgather.src.Main.chatting.models.ChatMessageResponse
import com.example.helgather.src.Main.chatting.models.ChatMessageResult
import com.example.helgather.src.Main.chatting.models.ChatRoomResponse
import okhttp3.OkHttpClient
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompHeader
import java.text.SimpleDateFormat
import java.util.Date

    class ChattingMessageFragment : BaseFragment<FragmentChattingChatBinding>
        (FragmentChattingChatBinding::bind, R.layout.fragment_chatting_chat), ChattingFragmentInterface {


        private val stompManager: StompManager = StompManager()
        private lateinit var mStompClient: StompClient
        val chatId = ApplicationClass.sSharedPreferences.getInt("chatId",1)
        val memberId = ApplicationClass.sSharedPreferences.getInt("memberId",2)
        val chatName = ApplicationClass.sSharedPreferences.getString("chatName","채팅방")
        private val viewModel: ChatViewModel by viewModels()
        private lateinit var chatAdapter: ChattingChatAdapter

        @SuppressLint("CheckResult")
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.tvChattingChatId.text = chatName

            ChattingService(this@ChattingMessageFragment).tryGetChattingMessage(chatId = chatId, memberId = memberId)

            // mStompClient 초기화
            mStompClient = stompManager.connectStomp(chatId)



            viewModel.messageList.observe(viewLifecycleOwner) { messages ->
                chatAdapter = ChattingChatAdapter(messages, memberId)
                binding.rvChattingChat.apply {
                    adapter = chatAdapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    post {
                        scrollToPosition(messages.size - 1)
                    }
                }
            }

            val topic = "/sub/chatroom/$chatId"

            if(mStompClient.isConnected){
                Log.d("---aaaa","---")
            }else{ Log.d("----aaa", "----")

            }
            // 메시지를 받을 때의 Listener를 설정
            val subscribe = mStompClient.topic(topic).subscribe { topicMessage ->
                Log.d("webSocketID",chatId.toString())
                val receivedMessage = topicMessage.payload
                // 메시지를 받았을 때의 동작을 여기에 작성
                viewModel.addMessageFromJson(receivedMessage)
            }

            // 구독 상태 확인
            if (!subscribe.isDisposed) {
                Log.d("----test","1")
            } else {
                Log.d("----test","2")
            }

            messageControl()
        }
        override fun onResume() {
            super.onResume()
            //바텀네비게이션 바 숨김
            val mainAct = activity as MainActivity
            mainAct.hideBottomNavi(true)
        }

        override fun onDestroyView() {
            super.onDestroyView()
            stompManager.disconnectStomp()
        }
        override fun onBackPressed() {
            parentFragmentManager.popBackStack()
        }
        @SuppressLint("SimpleDateFormat")
        private fun messageControl(){
            binding.edtChatMessage.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = s.toString()
                    binding.ivChatSend.isEnabled = text.isNotEmpty()

                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
            binding.ivChatSend.setOnClickListener {
                val message = binding.edtChatMessage.text.toString()
                val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
                val currentTime = Date()
                val formattedTime = outputFormat.format(currentTime)
                Log.d("testtesttest","$message, $formattedTime, $memberId")
                val chatMessage = ChatMessageResult(memberId,message, time = formattedTime, userProfile ="",false)
//                viewModel.addMessage(chatMessage)
                binding.edtChatMessage.text.clear()
                stompManager.sendMessage(chatId, chatMessage)
            }

        }


    override fun onGetChatRoomSuccess(response: ChatRoomResponse) {}

    override fun onGetChatRoomFailure(message: String) {}
    override fun onGetChatMessageSuccess(response: ChatMessageResponse) {
        viewModel.addMessageList(response.chatMessageResult)

        binding.rvChattingChat.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val messages = viewModel.messageList.value
            chatAdapter = ChattingChatAdapter(messages ?: mutableListOf(), memberId)
            adapter = chatAdapter
        }

        binding.rvChattingChat.scrollToPosition(0)
    }

    override fun onGetChatMessageFailure(message: String) {
        showToastMessage(message)
    }
}