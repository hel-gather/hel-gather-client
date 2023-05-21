package com.example.helgather.src.Main.chatting

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentChattingBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Main.chatting.list.ChattingRoomAdapter
import com.example.helgather.src.Main.chatting.models.ChatRoomResponse
import com.example.helgather.src.Main.chatting.models.ChatRoomResponseItem

class ChattingFragment : BaseFragment<FragmentChattingBinding>(FragmentChattingBinding::bind, R.layout.fragment_chatting)
    ,ChattingFragmentInterface{

//    var chatList = listOf<ChattingListResult>(ChattingListResult("아이디1","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디2","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
//    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.tvChattingTitle.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.frm_main,ChattingChatFragment()).commit()
        }

        ChattingService(this@ChattingFragment).tryGetChattingRoom(1) //우선 임시적으로 1로 설정
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)
    }

    override fun onGetChatRoomSuccess(response: ChatRoomResponse) {
        binding.rvChattingList.apply {
            adapter = ChattingRoomAdapter(response)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onGetChatRoomFailure(message: String) {
        showToastMessage(message)
    }
}