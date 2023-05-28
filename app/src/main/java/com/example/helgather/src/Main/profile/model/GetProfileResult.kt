package com.example.helgather.src.Main.profile.model

data class GetProfileResult(
    val benchPress: Int,
    val deadlift: Int,
    val exerciseCount: Int,
    val imageUrl: String,
    val introduction: String,
    val memberId: Int,
    val squat: Int
)