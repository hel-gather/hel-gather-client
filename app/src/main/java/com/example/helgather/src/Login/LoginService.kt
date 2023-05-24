package com.example.helgather.src.Login

import android.util.Log
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.BaseResponse
import com.example.helgather.src.Login.model.PostSignUpRequest
import com.example.helgather.src.Login.model.PostSignUpResponse
import com.example.helgather.src.Login.model.PostSignUpResult
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
                Log.d(
                    "signUp",
                    "message ${response.body()?.message} / code ${response.body()?.code} / success ${response.body()?.isSuccess}"
                )
                if (response.body()?.postSignUpResult == null) {
                    Log.d("signUp", "1 ${response.body()?.message} dd ")
                    // BaseResponse에 정의된 필드 접근
                    val code = response.body()?.code
                    val message = response.body()?.message
                    val success = response.body()?.isSuccess

                    // PostSignUpResponse에 정의된 필드 접근
                    val postSignUpResult = response.body()?.postSignUpResult
                    loginInterface.onPostJoinSuccess(response.body() as PostSignUpResponse)
                } else {
                    Log.d("signUp", "responseBody is null")
                }
            }

            override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {
                loginInterface.onPostJoinFailure(t.message ?: "통신 오류")
            }
        })
    }
}