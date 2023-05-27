package com.example.helgather.src.Main.profile

import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileRetrofitInterface {

    @GET("/today-exercises")
    fun getTodayExercise(@Query("member")member : Int) : Call<GetTodayExerciseResponse>

    @POST("/today-exercises")
    fun PostTodayExercise(@Query("member")member : Int) : Call<PostTodayExerciseResponse>
}