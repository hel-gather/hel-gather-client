package com.example.helgather.src.Login

import com.example.helgather.src.Login.model.PostSignUpRequest
import com.example.helgather.src.Login.model.PostSignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {

    @POST("/members/register")
    fun postSignUp(@Body params : PostSignUpRequest) : Call<PostSignUpResponse>
}