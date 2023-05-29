package com.example.helgather.src.Main.recruit.models

import com.google.gson.annotations.SerializedName

data class GetRecruitLocationResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    @SerializedName("result")val getRecruitLocationResult: List<GetRecruitLocationResult>
)