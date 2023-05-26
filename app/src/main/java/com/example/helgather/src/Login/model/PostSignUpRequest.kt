package com.example.helgather.src.Login.model

data class PostSignUpRequest(
    val name: String,
    val phone: String,
    val nickname: String,
    val password: String,
    val birthYear: Int,
    val birthMonth: Int,
    val birthDay: Int
)