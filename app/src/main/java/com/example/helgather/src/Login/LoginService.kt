package com.example.helgather.src.Login

import com.example.helgather.config.ApplicationClass
import com.example.helgather.src.Login.model.PostSignUpRequest
import com.example.helgather.src.Login.model.PostSignUpResponse
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
                loginInterface.onPostJoinSuccess(response.body() as PostSignUpResponse)
            }

            override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {
                loginInterface.onPostJoinFailure(t.message ?: "통신 오류")
            }
        })
    }
}