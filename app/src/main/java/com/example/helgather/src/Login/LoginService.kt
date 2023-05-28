package com.example.helgather.src.Login

import android.net.Uri
import android.util.Log
import com.example.helgather.config.ApplicationClass
import com.example.helgather.src.Login.model.PostLoginRequest
import com.example.helgather.src.Login.model.PostLoginResponse
import com.example.helgather.src.Login.model.PostSignUpImageResponse
import com.example.helgather.src.Login.model.PostSignUpProfileRequest
import com.example.helgather.src.Login.model.PostSignUpProfileResponse
import com.example.helgather.src.Login.model.PostSignUpRequest
import com.example.helgather.src.Login.model.PostSignUpResponse
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginInterface: LoginInterface) {


    fun tryPostSignUp(postSignUpRequest: PostSignUpRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object : Callback<PostSignUpResponse> {
            override fun onResponse(
                call: Call<PostSignUpResponse>,
                response: Response<PostSignUpResponse>
            ) {
                if (response.isSuccessful) {
                    loginInterface.onPostSignUpSuccess(response.body() as PostSignUpResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string() // errorBody를 문자열로 변환
                    val errorResponse = gson.fromJson(errorBodyStr, PostSignUpResponse::class.java)
                    val postSignUpResult = errorResponse.postSignUpResult
                    val postSignUpResponse = PostSignUpResponse(false,errorResponse.code,errorResponse.message,postSignUpResult)
                    loginInterface.onPostSignUpSuccess(postSignUpResponse)
                }

            }

            override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {
                loginInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostLogin(postLoginRequest: PostLoginRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object : Callback<PostLoginResponse>{
            override fun onResponse(
                call: Call<PostLoginResponse>,
                response: Response<PostLoginResponse>
            ) {
                if(response.isSuccessful){
                    loginInterface.onPostLoginSuccess(response.body() as PostLoginResponse)
                }else{
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, PostLoginResponse::class.java)
                    val postLoginResult = errorResponse.postLoginResult
                    Log.d("loginErrorCheck","code = ${errorResponse?.code} , message = ${errorResponse?.message}")
                    val postLoginResponse = PostLoginResponse(false,errorResponse.code,errorResponse.message,postLoginResult)
                    loginInterface.onPostLoginSuccess(postLoginResponse)
                }
            }

            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                loginInterface.onPostLoginFailure(t.message ?: "통신 오류")
            }
        })

    }


    fun tryPostSignUpProfile(memberId : Int,postSignUpProfileRequest: PostSignUpProfileRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postSignUpProfile(memberId,postSignUpProfileRequest).enqueue(object : Callback<PostSignUpProfileResponse>{
            override fun onResponse(
                call: Call<PostSignUpProfileResponse>,
                response: Response<PostSignUpProfileResponse>
            ) {
                if(response.isSuccessful){
                    loginInterface.onPostSignuUpProfileSuccess(response.body() as PostSignUpProfileResponse)
                }else{
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, PostSignUpProfileResponse::class.java)
                    val postSignUpProfileResult = errorResponse.postSignUpProfileResult
                    val postSignUpProfileResponse = PostSignUpProfileResponse(false,errorResponse.code,errorResponse.message,postSignUpProfileResult)
                    loginInterface.onPostSignuUpProfileSuccess(postSignUpProfileResponse)
                }
            }

            override fun onFailure(call: Call<PostSignUpProfileResponse>, t: Throwable) {
                loginInterface.onPostSignUpProfileFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSignUpImage(memberId : Int, image: MultipartBody.Part) {

        val retrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        retrofitInterface.postSignUpImage(memberId, image).enqueue(object : Callback<PostSignUpImageResponse>{
            override fun onResponse(
                call: Call<PostSignUpImageResponse>,
                response: Response<PostSignUpImageResponse>
            ) {
                if(response.isSuccessful){
                    loginInterface.onPostSignUpImageSuccess(response.body() as PostSignUpImageResponse)
                } else {
                    val gson = Gson()
                    val errorBodyStr = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBodyStr, PostSignUpImageResponse::class.java)
                    val postSignUpImageResult = errorResponse.postSignUpImageResult
                    val postSignUpImageResponse = PostSignUpImageResponse(false, errorResponse.code, errorResponse.message, postSignUpImageResult)
                    loginInterface.onPostSignUpImageSuccess(postSignUpImageResponse)
                }
            }

            override fun onFailure(call: Call<PostSignUpImageResponse>, t: Throwable) {
                loginInterface.onPostSignUpImageFailure(t.message ?: "통신 오류")
            }
        })
    }

}