package com.example.helgather.src.Main.profile.model

import com.google.gson.annotations.SerializedName

data class PostTodayExerciseResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val postTodayExerciseResult: PostTodayExerciseResult
)