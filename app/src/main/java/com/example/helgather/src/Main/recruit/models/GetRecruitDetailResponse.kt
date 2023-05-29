package com.example.helgather.src.Main.recruit.models

import com.google.gson.annotations.SerializedName

data class GetRecruitDetailResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    @SerializedName("result")val getRecruitDetailResult: GetRecruitDetailResult
)