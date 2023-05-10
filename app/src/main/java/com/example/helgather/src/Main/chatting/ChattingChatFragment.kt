package com.example.helgather.src.Main.chatting

import android.os.Bundle
import android.view.View
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentChattingChatBinding
import com.example.helgather.src.Main.MainActivity

class ChattingChatFragment : BaseFragment<FragmentChattingChatBinding>
    (FragmentChattingChatBinding::bind, R.layout.fragment_chatting_chat) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }



    override fun onResume() {
        super.onResume()
        //바텀네비게이션 바 숨김
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)
    }


}