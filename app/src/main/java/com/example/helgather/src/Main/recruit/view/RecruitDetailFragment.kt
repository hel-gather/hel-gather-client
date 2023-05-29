package com.example.helgather.src.Main.recruit.view

import android.os.Bundle
import android.view.View
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentRecruitDetailBinding
import com.example.helgather.src.Main.recruit.RecruitFragmentInterface
import com.example.helgather.src.Main.recruit.models.GetRecruitDetailResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitChatResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailResponse

class RecruitDetailFragment:BaseFragment<FragmentRecruitDetailBinding>
    (FragmentRecruitDetailBinding::bind, R.layout.fragment_recruit_detail),RecruitFragmentInterface {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onGetRecruitAllSuccess(response: GetRecruitLocationResponse) {}

    override fun onGetRecruitAllFailure(message: String) {}

    override fun onGetRecruitLocationSuccess(response: GetRecruitLocationResponse) {}

    override fun onGetRecruitLocationFailure(message: String) {}

    override fun onGetRecruitDetailSuccess(response: GetRecruitDetailResponse) {}

    override fun onGetRecruitDetailFailure(message: String) {}

    override fun onPostRecruitDetailSuccess(response: PostRecruitDetailResponse) {}

    override fun onPostRecruitDetailFailure(message: String) {}

    override fun onPostRecruitChatSuccess(response: PostRecruitChatResponse) {}

    override fun onPostRecruitChatFailure(message: String) {}
}