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
        mStompClient.topic("/sub/chatroom/$chatId").subscribe { topicMessage ->
            // 메시지를 받았을 때의 동작을 여기에 작성합니다.
            viewModel.addMessageFromJson(topicMessage.payload)
        }

        sendMessage()
    }

    override fun onResume() {
        super.onResume()
        //바텀네비게이션 바 숨김
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)
    }

    @SuppressLint("SimpleDateFormat")
    private fun sendMessage(){
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
            viewModel.addMessage(ChatMessageResult(first = false, message,formattedTime,1,0))
            binding.edtChatMessage.text.clear()
            stompManager.sendMessage(1, userId = 2 ,message,formattedTime,0,false)
        }

    }


    override fun onGetChatRoomSuccess(response: ChatRoomResponse) {}

    override fun onGetChatRoomFailure(message: String) {}

    override fun onGetChatMessageSuccess(response: ChatMessageResponse) {
        val chatAdapter = ChattingChatAdapter(response.ChatMessageResult)
        binding.rvChattingChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)

        }
        viewModel.messageList.observe(viewLifecycleOwner, Observer { messages->
            messages.forEach {chatAdapter.addMessage(it)}
            binding.rvChattingChat.scrollToPosition(0)
        })

        //테스트 데이터 추가
        response.ChatMessageResult.forEach { viewModel.addMessage(it) }
    }

    override fun onGetChatMessageFailure(message: String) {
        showToastMessage(message)
    }
}