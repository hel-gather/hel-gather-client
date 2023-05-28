package com.example.helgather.src.Main.post

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentPostBinding
import com.example.helgather.src.Main.MainActivity

class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::bind , R.layout.fragment_post) {

    var postTestList = listOf<PostListResult>(
        PostListResult(1,"노균욱","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"김지홍","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"진주성","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"노균욱","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"김지홍","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"진주성","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"노균욱","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"김지홍","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"진주성","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"노균욱","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"김지홍","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"진주성","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"노균욱","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"김지홍","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"진주성","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"노균욱","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"김지홍","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
        PostListResult(1,"진주성","이것은 제목 테스트 입니다.",12,"2023-05-21T04:20:19.883098"),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postRecyclerView()

        binding.fabPostWriting.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.frm_main,PostWriteFragment()).commit()
        }

        //스와이프시 게시물 갱신
        binding.srlPost.setOnRefreshListener {
            //여기서 서버랑 통신
        }
        
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(false)
    }


    private fun postRecyclerView(){
        binding.rvPost.apply {
            adapter = PostListAdapter(postTestList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}