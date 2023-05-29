package com.example.helgather.src.Main.recruit

import com.example.helgather.src.Main.recruit.models.GetRecruitAllResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitDetailResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitChatResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailResponse

interface RecruitFragmentInterface {

    fun onGetRecruitAllSuccess(response: GetRecruitAllResponse)
    fun onGetRecruitAllFailure(message: String)

    fun onGetRecruitLocationSuccess(response: GetRecruitLocationResponse)
    fun onGetRecruitLocationFailure(message: String)

    fun onGetRecruitDetailSuccess(response: GetRecruitDetailResponse)
    fun onGetRecruitDetailFailure(message: String)

    fun onPostRecruitDetailSuccess(response: PostRecruitDetailResponse)
    fun onPostRecruitDetailFailure(message: String)

    fun onPostRecruitChatSuccess(response: PostRecruitChatResponse)
    fun onPostRecruitChatFailure(message: String)


}