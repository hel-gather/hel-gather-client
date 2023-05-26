package com.example.helgather.src.Login.model

import com.example.helgather.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class PostLoginResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val postLoginResult : PostLoginResult ?= null
)