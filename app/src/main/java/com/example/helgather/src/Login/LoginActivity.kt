package com.example.helgather.src.Login

import android.content.Intent
import android.os.Bundle
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivityLoginBinding

class LoginActivity:BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moveSignUp()
        
    }

    private fun moveSignUp(){
        binding.tvLoginSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchLogin(){
        //로그인시 통신 후 메인화면 갈지말지 설정
        binding.btnLoginDone.setOnClickListener {


        }
    }

}