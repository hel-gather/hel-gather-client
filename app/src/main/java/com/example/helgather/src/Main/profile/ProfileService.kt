package com.example.helgather.src.Main.profile

import com.example.helgather.config.ApplicationClass
import com.example.helgather.src.Login.model.PostSignUpProfileRequest
import com.example.helgather.src.Main.profile.model.GetProfileResponse
import com.example.helgather.src.Main.profile.model.GetSBDResponse
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PatchProfileImageResponse
import com.example.helgather.src.Main.profile.model.PatchProfileIntroductionRequest
import com.example.helgather.src.Main.profile.model.PatchProfileIntroductionResponse
import com.example.helgather.src.Main.profile.model.PatchProfileIntroductionResult
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResult
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

    fun tryPostExercise(member: Int,file: MultipartBody.Part) {
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)

        profileRetrofitInterface.postTodayExercise(member,file).enqueue(object : Callback<PostTodayExerciseResponse> {
            override fun onResponse(call: Call<PostTodayExerciseResponse>, response: Response<PostTodayExerciseResponse>) {
                if (response.isSuccessful) {
                    profileFragmentInterface.onPostTodayExerciseSuccess(response.body() as PostTodayExerciseResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr,PostTodayExerciseResponse::class.java)
                    val postTodayExerciseResult = errorResponse.postTodayExerciseResult
                    val postTodayExerciseResponse = PostTodayExerciseResponse(false,errorResponse.code,errorResponse.message,postTodayExerciseResult)
                    profileFragmentInterface.onPostTodayExerciseSuccess(postTodayExerciseResponse)
                }
            }

            override fun onFailure(call: Call<PostTodayExerciseResponse>, t: Throwable) {
                profileFragmentInterface.onPostTodayExerciseFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetSBD(member : Int){
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.getSBD(member = member).enqueue(object : Callback<GetSBDResponse>{
            override fun onResponse(
                call: Call<GetSBDResponse>,
                response: Response<GetSBDResponse>
            ) {
                if(response.isSuccessful){
                    profileFragmentInterface.onGetSBDSuccess(response.body() as GetSBDResponse)
                }else{
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr,GetSBDResponse::class.java)
                    val getSBDResult = errorResponse.getSBDResult
                    val getSBDResponse = GetSBDResponse(false,errorResponse.code,errorResponse.message,getSBDResult)
                    profileFragmentInterface.onGetSBDSuccess(getSBDResponse)
                }
            }

            override fun onFailure(call: Call<GetSBDResponse>, t: Throwable) {
                profileFragmentInterface.onGetSBDFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchProfile(memberId : Int,image : MultipartBody.Part){
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.patchProfileImage(memberId,image).enqueue(object :Callback<PatchProfileImageResponse>{
            override fun onResponse(
                call: Call<PatchProfileImageResponse>,
                response: Response<PatchProfileImageResponse>
            ) {
                if(response.isSuccessful){
                    profileFragmentInterface.onPatchProfileImageSuccess(response.body() as PatchProfileImageResponse)
                }else{
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr,PatchProfileImageResponse::class.java)
                    val patchProfileImageResult = errorResponse.patchProfileImageResult
                    val patchProfileImageResponse = PatchProfileImageResponse(false,errorResponse.code,errorResponse.message,patchProfileImageResult)
                    profileFragmentInterface.onPatchProfileImageSuccess(patchProfileImageResponse)
                }
            }

            override fun onFailure(call: Call<PatchProfileImageResponse>, t: Throwable) {
                profileFragmentInterface.onPatchProfileImageFailure(t.message ?: "통신 오류")
            }
        })

    }

    fun tryPatchIntroduction(memberId: Int, patchProfileIntroductionRequest : PatchProfileIntroductionRequest){
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.patchProfileIntroduction(memberId =  memberId, patchProfileIntroductionRequest)
            .enqueue(object  : Callback<PatchProfileIntroductionResponse>{
            override fun onResponse(
                call: Call<PatchProfileIntroductionResponse>,
                response: Response<PatchProfileIntroductionResponse>
            ) {
                if(response.isSuccessful){
                    profileFragmentInterface.onPatchProfileIntroductionSuccess(response.body() as PatchProfileIntroductionResponse)
                }else{
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr,PatchProfileIntroductionResponse::class.java)
                    val patchProfileIntroductionResult = errorResponse.patchProfileIntroductionResult
                    val patchProfileIntroductionResponse = PatchProfileIntroductionResponse(false,errorResponse.code,errorResponse.message,patchProfileIntroductionResult)
                    profileFragmentInterface.onPatchProfileIntroductionSuccess(patchProfileIntroductionResponse)
                }
            }

            override fun onFailure(call: Call<PatchProfileIntroductionResponse>, t: Throwable) {
                profileFragmentInterface.onPatchProfileIntroductionFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetProfile(memberId: Int) {
        val profileRetrofitInterface = ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.getProfile(memberId).enqueue(object : Callback<GetProfileResponse> {
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                if (response.isSuccessful) {
                    profileFragmentInterface.onGetProfileSuccess(response.body() as GetProfileResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, GetProfileResponse::class.java)
                    val getProfileResult = errorResponse.getProfileResult
                    val getProfileResponse = GetProfileResponse(
                        false,
                        errorResponse.code,
                        errorResponse.message,
                        getProfileResult
                    )
                    profileFragmentInterface.onGetProfileSuccess(getProfileResponse)
                }
            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                profileFragmentInterface.onGetProfileFailure(t.message ?: "통신 오류")
            }
        })
    }



}