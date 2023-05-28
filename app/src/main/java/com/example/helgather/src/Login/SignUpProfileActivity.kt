package com.example.helgather.src.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivitySignupProfileBinding
import com.example.helgather.src.Login.model.PostLoginResponse
import com.example.helgather.src.Login.model.PostSignUpImageResponse
import com.example.helgather.src.Login.model.PostSignUpProfileRequest
import com.example.helgather.src.Login.model.PostSignUpProfileResponse
import com.example.helgather.src.Login.model.PostSignUpResponse

class SignUpProfileActivity : BaseActivity<ActivitySignupProfileBinding>(ActivitySignupProfileBinding::inflate),LoginInterface {


    private var squat :Int? = 0
    private var benchpress : Int? = 0
    private var deadlift:Int? = 0
    private var introduction : String? = null
    private var nickname : String? = null
    private var memberId : Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nickname = intent.getStringExtra("nickname")
        memberId = intent.getIntExtra("memberId",0)

        binding.tvSignUpProfileSubTitle.text = "${nickname}님의 간단한 소개 입력"

        binding.btnSignUpProfileComplete.setOnClickListener {
            squat = binding.edtSignUpProfileSquat.text.toString().toIntOrNull() ?: 0
            benchpress = binding.edtSignUpProfileBenchpress.text.toString().toIntOrNull() ?: 0
            deadlift = binding.edtSignUpProfileDeadlift.text.toString().toIntOrNull() ?: 0

            introduction = binding.edtSignUpProfileIntroduction.text.toString()

            LoginService(this@SignUpProfileActivity).tryPostSignUpProfile(memberId, PostSignUpProfileRequest(squat,benchpress,deadlift,introduction))
        }

        binding.btnSignUpProfileSkip.setOnClickListener {
            val intent = Intent(this@SignUpProfileActivity, SignUpImageActivity::class.java)
            intent.putExtra("memberId",memberId)
            startActivity(intent)
            finish()
        }




    }

    override fun onPostSignuUpProfileSuccess(response: PostSignUpProfileResponse) {
        if(response.code == 200){
            val intent = Intent(this@SignUpProfileActivity, SignUpImageActivity::class.java)
            intent.putExtra("memberId",memberId)
            startActivity(intent)
            finish()
        }
    }

    override fun onPostSignUpProfileFailure(message: String) {
        showToastMessage("오류 : $message")
    }

    override fun onPostLoginSuccess(response: PostLoginResponse) {}

    override fun onPostLoginFailure(message: String) {}

    override fun onPostSignUpSuccess(response: PostSignUpResponse) {}

    override fun onPostSignUpFailure(message: String) {}

    override fun onPostSignUpImageSuccess(response: PostSignUpImageResponse) {}

    override fun onPostSignUpImageFailure(message: String) {}

}