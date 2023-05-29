package com.example.helgather.src.Main.profile.model

import com.google.gson.annotations.SerializedName

data class PostSBDResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    @SerializedName("result")val postSBDResult: PostSBDResult
)