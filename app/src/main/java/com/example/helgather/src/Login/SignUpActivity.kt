package com.example.helgather.src.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivitySignupBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Util.ErrorDialog

class SignUpActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {

    //이메일 패턴 체킹
    private val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    //비밀번호 패턴 체킹 -> 6자리 이상과 문자와 숫자의 조합
    private val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}\$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        checkPattern()

        binding.ibtnSignUpClose.setOnClickListener{
            finish()
        }

    }

    @SuppressLint("CommitPrefEdits")
    private fun checkPattern(){
        binding.btnSignUpDone.setOnClickListener {
            val emailCheck = binding.edtSignUpEmail.text.toString()
            val passwordCheck = binding.edtSignUpPassword.text.toString()

            if(emailCheck.matches(emailPattern)&& passwordCheck.matches(passwordPattern)){
                //여기에 나중에 통신부분 만들어야함
                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val sSharedPref = ApplicationClass.sSharedPreferences.edit()
                if(!emailCheck.matches(emailPattern)){
                    sSharedPref.putString("errorType","이메일 형식 오류")
                    sSharedPref.putString("errorMessage","옳바른 이메일 형식을 입력하세요.")
                }else if(!passwordCheck.matches(passwordPattern)){
                    sSharedPref.putString("errorType","패스워드 형식 오류")
                    sSharedPref.putString("errorMessage","올바른 패스워드 형식을 입력하세요.\n 6자 이상,영문+숫자 포함")
                }
                sSharedPref.apply()
                ErrorDialog(this).show()
            }
        }
    }
}