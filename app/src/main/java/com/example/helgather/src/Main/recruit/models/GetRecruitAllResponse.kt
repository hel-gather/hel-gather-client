package com.example.helgather.src.Main.recruit.models

import com.google.gson.annotations.SerializedName

data class GetRecruitAllResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    @SerializedName("result") val getRecruitAllResult: List<GetRecruitAllResult>
)