package com.example.helgather.src.Main.post

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentPostBinding

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
        
    }


    private fun postRecyclerView(){
        binding.rvPost.apply {
            adapter = PostListAdapter(postTestList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}