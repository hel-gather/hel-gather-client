package com.example.helgather.src.Main.profile

import com.example.helgather.config.ApplicationClass
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileService(val profileFragmentInterface : ProfileFragmentInterface) {


    fun tryGetTodayExercise(member : Int){
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.getTodayExercise(member = member).enqueue(object : Callback<GetTodayExerciseResponse>{
            override fun onResponse(
                call: Call<GetTodayExerciseResponse>,
                response: Response<GetTodayExerciseResponse>
            ) {
                if(response.isSuccessful){
                    profileFragmentInterface.onGetTodayExerciseSuccess(response.body() as GetTodayExerciseResponse)
                }else{
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string() // errorBody를 문자열로 변환
                    val errorResponse = gson.fromJson(errorBodyStr, GetTodayExerciseResponse::class.java)
                    val getTodayExerciseResult = errorResponse.getTodayExerciseResult
                    val getTodayExerciseResponse = GetTodayExerciseResponse(false,errorResponse.code,errorResponse.message,getTodayExerciseResult)
                    profileFragmentInterface.onGetTodayExerciseSuccess(getTodayExerciseResponse)
                }
            }

            override fun onFailure(call: Call<GetTodayExerciseResponse>, t: Throwable) {
                profileFragmentInterface.onGetTodayExerciseFailure(t.message ?: "통신 오류")
            }
        } )
    }


}