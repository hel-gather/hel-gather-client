package com.example.helgather.src.Main.recruit

import com.example.helgather.src.Main.recruit.models.GetRecruitLocationResponse
import com.example.helgather.src.Main.recruit.models.GetRecruitDetailResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitChatRequest
import com.example.helgather.src.Main.recruit.models.PostRecruitChatResponse
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailRequest
import com.example.helgather.src.Main.recruit.models.PostRecruitDetailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecruitRetrofitInterface {

    @GET("/recruitments/all")
    fun getRecruitAll(): Call<GetRecruitLocationResponse>

    @GET("/recruitments")
    fun getRecruitLocation(
        @Query("location") location : Int,
        @Query("subLocation")subLocation : Int)
    :Call<GetRecruitLocationResponse>

    @GET("/recruitments/{id}")
    fun getRecruitDetail(
        @Path("id") id: Int
    ):Call<GetRecruitDetailResponse>

    @POST("/recruitments")
    fun postRecruitDetail(
        @Body request : PostRecruitDetailRequest
    ):Call<PostRecruitDetailResponse>

    @POST("/recruitments/{id}/chat")
    fun postRecruitChat(
        @Path("id") id : Int,
        @Body request : PostRecruitChatRequest
    ):Call<PostRecruitChatResponse>
}