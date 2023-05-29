package com.example.helgather.src.Main.chatting

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.ChattingRoomsListBinding
import com.example.helgather.databinding.FragmentChattingBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Main.chatting.list.ChattingRoomAdapter
import com.example.helgather.src.Main.chatting.models.ChatMessageResponse
import com.example.helgather.src.Main.chatting.models.ChatRoomResponse
import com.example.helgather.src.Main.chatting.models.ChatRoomResult

class ChattingFragment : BaseFragment<FragmentChattingBinding>(FragmentChattingBinding::bind, R.layout.fragment_chatting)
    ,ChattingFragmentInterface{

    private val memberId = sSharedPreferences.getInt("memberId",0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //memberId임시설정
        ChattingService(this@ChattingFragment).tryGetChattingRoom(memberId = memberId) //우선 임시적으로 1로 설정
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)
    }

    override fun onGetChatRoomSuccess(response: ChatRoomResponse) {
        binding.rvChattingList.apply {
            adapter = ChattingRoomAdapter(response.ChatRoomResult,object  : ChattingRoomAdapter.chatRoomClickListener<ChatRoomResult>{
                override fun onRoomClick(view: ChattingRoomsListBinding, pos: Int) {
                    view.root.setOnClickListener {
                        val sSharedPref = sSharedPreferences.edit()
                        sSharedPref.putInt("chatId",response.ChatRoomResult[pos].chatId) // 채팅방 아이디
                        sSharedPref.putString("chatName",response.ChatRoomResult[pos].id)
                        sSharedPref.apply()
                        parentFragmentManager.beginTransaction().replace(R.id.frm_main,ChattingMessageFragment()).addToBackStack("Chatting").commit()
                    }
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onGetChatRoomFailure(message: String) {
        showToastMessage(message)
    }

    override fun onGetChatMessageSuccess(response: ChatMessageResponse) {}
    override fun onGetChatMessageFailure(message: String) {}
}