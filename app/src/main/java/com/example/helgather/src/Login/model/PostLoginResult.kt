package com.example.helgather.src.Login.model

data class PostLoginResult(
    val accessToken: String,
    val grantType: String,
    val memberId: Int,
    val nickname: String,
    val refreshToken: String
)