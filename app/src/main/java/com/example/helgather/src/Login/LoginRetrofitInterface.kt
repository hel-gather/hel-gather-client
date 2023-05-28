package com.example.helgather.src.Login

import com.example.helgather.src.Login.model.PostLoginRequest
import com.example.helgather.src.Login.model.PostLoginResponse
import com.example.helgather.src.Login.model.PostSignUpProfileRequest
import com.example.helgather.src.Login.model.PostSignUpProfileResponse
import com.example.helgather.src.Login.model.PostSignUpRequest
import com.example.helgather.src.Login.model.PostSignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginRetrofitInterface {

    @POST("/members/register")
    fun postSignUp(@Body params : PostSignUpRequest) : Call<PostSignUpResponse>

    @POST("members/login")
    fun postLogin(@Body params : PostLoginRequest) : Call<PostLoginResponse>

    @POST("/members/profile/{memberId}")
    fun postSignUpProfile(@Path ("memberId") memberId : Int
                          ,@Body params : PostSignUpProfileRequest) : Call<PostSignUpProfileResponse>


}