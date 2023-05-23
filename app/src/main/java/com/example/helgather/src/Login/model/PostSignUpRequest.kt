package com.example.helgather.src.Login.model

data class PostSignUpRequest(
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val nickname: String,
    val password: String,
    val phone: String,
    val userName: String
)