package com.example.helgather.src.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivityLoginBinding
import com.example.helgather.src.First.StartActivity
import com.example.helgather.src.Login.model.PostLoginRequest
import com.example.helgather.src.Login.model.PostLoginResponse
import com.example.helgather.src.Login.model.PostLoginResult
import com.example.helgather.src.Login.model.PostSignUpImageResponse
import com.example.helgather.src.Login.model.PostSignUpProfileResponse
import com.example.helgather.src.Login.model.PostSignUpResponse
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.util.ErrorDialog

class LoginActivity:BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),LoginInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moveSignUp()

        binding.btnLoginDone.setOnClickListener {
            val nickname = binding.edtLoginNickname.text.toString()
            val password = binding.edtLoginPassword.text.toString()
            LoginService(this@LoginActivity).tryPostLogin(PostLoginRequest(nickname,password))
        }
        
    }

    private fun moveSignUp(){
        binding.tvLoginSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    //error에 대한 설정
    private fun errorDialogStep(message : String){
        val sharedPref = sSharedPreferences.edit()
        sharedPref.putString("errorType","로그인 오류")
        sharedPref.putString("errorMessage",message)
        sharedPref.apply()
        ErrorDialog(this).show()
    }


    override fun onPostLoginSuccess(response: PostLoginResponse) {
        if(response.code == 200){
            val sharedPref = sSharedPreferences.edit()
            sharedPref.putInt("memberId",response.postLoginResult!!.memberId)
            sharedPref.putString("Authorization",(response.postLoginResult.grantType + " "+response.postLoginResult.accessToken))
            if(binding.cbLoginAutoLogin.isChecked){
                sharedPref.putBoolean("autoLogin", true)
            }else{
                sharedPref.putBoolean("autoLogin", false)
            }
            sharedPref.apply()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            showToastMessage("로그인 성공!")
            startActivity(intent)
            finish()
        }else{
            Log.d("loginError","----")
            errorDialogStep(response.message.toString())
        }
    }

    override fun onPostLoginFailure(message: String) {
        showToastMessage("통신 오류 : $message")
    }



    override fun onPostSignUpSuccess(response: PostSignUpResponse) {}
    override fun onPostSignUpFailure(message: String) {}

    override fun onPostSignuUpProfileSuccess(response: PostSignUpProfileResponse) {}
    override fun onPostSignUpProfileFailure(message: String) {}

    override fun onPostSignUpImageSuccess(response: PostSignUpImageResponse) {}

    override fun onPostSignUpImageFailure(message: String) {}
}