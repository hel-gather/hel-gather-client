package com.example.helgather.src.Main.chatting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentChattingChatBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Main.chatting.list.ChattingChatAdapter

class ChattingChatFragment : BaseFragment<FragmentChattingChatBinding>
    (FragmentChattingChatBinding::bind, R.layout.fragment_chatting_chat) {

    private val viewModel: ChatViewModel by viewModels()



    val chatList = listOf(ChattingMessageResult(0,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",true),
        ChattingMessageResult(0,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",false),
        ChattingMessageResult(0,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",false),
        ChattingMessageResult(1,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",true),
        ChattingMessageResult(1,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",false),
        ChattingMessageResult(1,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",false),
        ChattingMessageResult(0,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",true),
        ChattingMessageResult(0,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",true),
        ChattingMessageResult(1,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",true),
        ChattingMessageResult(1,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",false),
        ChattingMessageResult(1,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",false),
        ChattingMessageResult(0,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",true),
        ChattingMessageResult(0,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",false),
        ChattingMessageResult(1,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",true),
        ChattingMessageResult(1,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",false),
        ChattingMessageResult(0,R.drawable.ic_profile_me,"이것은 메세지 입니다~~~~~","시간테스트",true))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatAdapter = ChattingChatAdapter(chatList)
        binding.rvChattingChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)

        }
        viewModel.messageList.observe(viewLifecycleOwner, Observer { messages->
            messages.forEach {chatAdapter.addMessage(it)}
            binding.rvChattingChat.scrollToPosition(0)
        })

        //테스트 데이터 추가
        chatList.forEach { viewModel.addMessage(it) }



    }



    override fun onResume() {
        super.onResume()
        //바텀네비게이션 바 숨김
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)
    }


}