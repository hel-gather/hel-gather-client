package com.example.helgather.src.Login.model

import androidx.annotation.Keep
import com.example.helgather.config.BaseResponse
import com.google.gson.annotations.SerializedName

@Keep
data class PostSignUpResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val postSignUpResult: PostSignUpResult? = null
)
