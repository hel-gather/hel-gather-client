package com.example.helgather.src.Main.profile

import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse

interface ProfileFragmentInterface {

    fun onGetTodayExerciseSuccess(response : GetTodayExerciseResponse)

    fun onGetTodayExerciseFailure(message : String)

    fun onPostTodayExerciseSuccess(response : PostTodayExerciseResponse)

    fun onPostTodayExerciseFailure(message : String)

}