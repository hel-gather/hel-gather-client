package com.example.helgather.src.Login

import android.util.Log
import com.example.helgather.config.ApplicationClass
import com.example.helgather.src.Login.model.PostSignUpRequest
import com.example.helgather.src.Login.model.PostSignUpResponse
import com.google.gson.Gson
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
                    val code = response.body()?.code
                    val message = response.body()?.message
                    // HTTP 상태 코드가 2xx입니다.
                    // 여기에서 응답 본문을 처리하십시오.
                    // result가 있는 경우에 대한 처리를 수행합니다.
                    val postSignUpResult = response.body()?.postSignUpResult
                    val postSignUpResponse = PostSignUpResponse(true,code!!.toInt(),message,postSignUpResult)
                    loginInterface.onPostSignUpSuccess(postSignUpResponse)
                } else {
                    // HTTP 상태 코드가 2xx가 아닙니다.
                    // 여기에서 오류 메시지를 처리하십시오.
                    val errorBodyStr = response.errorBody()?.string() // errorBody를 문자열로 변환
                    // errorBodyStr을 파싱하여 필요한 정보를 가져옵니다.
                    // 이를 위해 Gson을 사용할 수 있습니다.
                    val gson = Gson()
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
}