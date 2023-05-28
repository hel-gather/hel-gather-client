package com.example.helgather.src.Login.model

import com.google.gson.annotations.SerializedName

data class PostSignUpProfileResponse(

    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    @SerializedName("result") val postSignUpProfileResult: PostSignUpProfileResult
)