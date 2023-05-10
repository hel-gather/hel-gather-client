package com.example.helgather.src.Main.chatting

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentChattingBinding
import com.example.helgather.src.Main.MainActivity

class ChattingFragment : BaseFragment<FragmentChattingBinding>(FragmentChattingBinding::bind, R.layout.fragment_chatting) {

    var chatList = listOf<ChattingListResult>(ChattingListResult("아이디1","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디2","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
        ChattingListResult("아이디3","방금전",R.drawable.ic_btm_profile,"메세지가 왔습니다",1),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        chatListRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)
    }

    //나중에 서버와의 db + 서버 통신 예정
    private fun chatListRecyclerView(){
        binding.rvChattingList.run {
            adapter = ChattingListAdapter(chatList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }
}