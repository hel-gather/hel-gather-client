package com.example.helgather.src.Main.chatting

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.text.SimpleDateFormat
import java.util.Date

class ChattingMessageFragment : BaseFragment<FragmentChattingChatBinding>
    (FragmentChattingChatBinding::bind, R.layout.fragment_chatting_chat), ChattingFragmentInterface {


    private val stompManager: StompManager = StompManager()
    private lateinit var mStompClient: StompClient
    val chatId = ApplicationClass.sSharedPreferences.getInt("chatId",1) // 0임시임
    val userId = ApplicationClass.sSharedPreferences.getInt("userId",2)
    private val viewModel: ChatViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ChattingService(this@ChattingMessageFragment).tryGetChattingMessage(chatId = 1, userId = 2)

        // mStompClient 초기화
        mStompClient = stompManager.connectStomp(chatId)

        // 메시지를 받을 때의 Listener를 설정
        val subscribe = mStompClient.topic("/sub/chatroom/$chatId").subscribe { topicMessage ->
            // 메시지를 받았을 때의 동작을 여기에 작성합니다.
            viewModel.addMessageFromJson(topicMessage.payload)
        }
        viewModel.messageList.observe(viewLifecycleOwner) { messages ->
            val mutableMessages = messages.toMutableList() // MutableList로 변환
            val chatAdapter = ChattingChatAdapter(mutableMessages)
            chatAdapter.submitList(messages)
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
            val chatMessage = ChatMessageResult(first = false, message, formattedTime, 1, 0)
            viewModel.addMessage(chatMessage)
            binding.edtChatMessage.text.clear()
            stompManager.sendMessage(1, userId = 2 ,message,formattedTime,0,true)
        }

    }


    override fun onGetChatRoomSuccess(response: ChatRoomResponse) {}

    override fun onGetChatRoomFailure(message: String) {}

    override fun onGetChatMessageSuccess(response: ChatMessageResponse) {
        val chatAdapter = ChattingChatAdapter(response.ChatMessageResult.toMutableList())
        binding.rvChattingChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        }
        response.ChatMessageResult.forEach { message ->
            if (!viewModel.isMessageExists(message)) {
                viewModel.addMessage(message)
                chatAdapter.addMessage(message)
            }
        }

        binding.rvChattingChat.scrollToPosition(0)
    }

    override fun onGetChatMessageFailure(message: String) {
        showToastMessage(message)
    }
}