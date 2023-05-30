package com.example.helgather.src.Main.recruit.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentRecruitDetailBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Main.recruit.RecruitFragmentInterface
import com.example.helgather.src.Main.recruit.RecruitService
import com.example.helgather.src.Main.recruit.models.GetRecruitDetailResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitChatResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailResponse

class RecruitDetailFragment:BaseFragment<FragmentRecruitDetailBinding>
    (FragmentRecruitDetailBinding::bind, R.layout.fragment_recruit_detail),RecruitFragmentInterface {


    private var recruitmentId  = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recruitmentId = arguments?.getInt("recruitmentId", 0)!!


        RecruitService(this@RecruitDetailFragment).tryGetRecruitDetail(recruitmentId)

        binding.btnPostDetailMatch.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        parentFragmentManager.popBackStack()
    }


    override fun onGetRecruitDetailSuccess(response: GetRecruitDetailResponse) {
        if(response.code == 200){
            binding.tvPostDetailTitle.text = response.getRecruitDetailResult.title.toString()
            binding.tvPostDetailWriterName.text = response.getRecruitDetailResult.nickname
            binding.tvPostDetailContent.text = response.getRecruitDetailResult.description
        }
    }

    override fun onGetRecruitDetailFailure(message: String) {
        showToastMessage("오류 : $message")
    }


    override fun onPostRecruitDetailSuccess(response: PostRecruitDetailResponse) {
    }

    override fun onPostRecruitDetailFailure(message: String) {}

    override fun onPostRecruitChatSuccess(response: PostRecruitChatResponse) {}

    override fun onPostRecruitChatFailure(message: String) {}

    override fun onGetRecruitAllSuccess(response: GetRecruitLocationResponse) {}

    override fun onGetRecruitAllFailure(message: String) {}

    override fun onGetRecruitLocationSuccess(response: GetRecruitLocationResponse) {}

    override fun onGetRecruitLocationFailure(message: String) {}




}