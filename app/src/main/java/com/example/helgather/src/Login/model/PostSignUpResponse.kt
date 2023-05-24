package com.example.helgather.src.Login.model

import androidx.annotation.Keep
import com.example.helgather.config.BaseResponse
import com.google.gson.annotations.SerializedName


@Keep
data class PostSignUpResponse(
    @SerializedName("result") val postSignUpResult: PostSignUpResult?,
) : BaseResponse()