package com.example.helgather.src.Main.profile

import com.example.helgather.src.Main.profile.model.GetProfileResponse
import com.example.helgather.src.Main.profile.model.GetSBDResponse
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PatchProfileImageResponse
import com.example.helgather.src.Main.profile.model.PatchProfileIntroductionResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse

interface ProfileFragmentInterface {

    fun onGetTodayExerciseSuccess(response : GetTodayExerciseResponse)
    fun onGetTodayExerciseFailure(message : String)

    fun onPostTodayExerciseSuccess(response : PostTodayExerciseResponse)
    fun onPostTodayExerciseFailure(message : String)

    fun onGetSBDSuccess(response : GetSBDResponse)
    fun onGetSBDFailure(message : String)

    fun onPatchProfileImageSuccess(response : PatchProfileImageResponse)
    fun onPatchProfileImageFailure(message: String)

    fun onPatchProfileIntroductionSuccess(response : PatchProfileIntroductionResponse)
    fun onPatchProfileIntroductionFailure(message: String)

    fun onGetProfileSuccess(response : GetProfileResponse)
    fun onGetProfileFailure(message: String)

}