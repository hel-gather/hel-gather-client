package com.example.helgather.src.Login

import com.example.helgather.src.Login.model.PostLoginResponse
import com.example.helgather.src.Login.model.PostSignUpResponse

interface LoginInterface {
    fun onPostLoginSuccess(response: PostLoginResponse)
    fun onPostLoginFailure(message : String)
    fun onPostSignUpSuccess(response: PostSignUpResponse)
    fun onPostSignUpFailure(message: String)
}