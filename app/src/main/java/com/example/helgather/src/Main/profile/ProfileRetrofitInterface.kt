package com.example.helgather.src.Main.profile

import com.example.helgather.src.Main.profile.model.GetSBDResponse
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PatchProfileImageResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileRetrofitInterface {

    @GET("/exercises")
    fun getTodayExercise(@Query("member")member : Int) : Call<GetTodayExerciseResponse>


    @Multipart
    @POST("/exercises")
    fun postTodayExercise(@Query("member")member : Int
                           ,@Part file : MultipartBody.Part) : Call<PostTodayExerciseResponse>

    @GET("/sbd")
    fun getSBD(@Query("member")member : Int) : Call<GetSBDResponse>

    @Multipart
    @PATCH("/members/profile/{memberId}/image")
    fun patchProfileImage(@Path("memberId") memberId : Int,
                          @Part file : MultipartBody.Part): Call<PatchProfileImageResponse>


}