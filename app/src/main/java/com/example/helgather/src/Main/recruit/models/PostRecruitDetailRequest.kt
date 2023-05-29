package com.example.helgather.src.Main.recruit.models

data class PostRecruitDetailRequest(
    val description: String,
    val location: Int,
    val memberId: Int,
    val subLocation: Int,
    val title: String
)