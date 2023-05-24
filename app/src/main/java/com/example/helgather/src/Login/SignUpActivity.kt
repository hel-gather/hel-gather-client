package com.example.helgather.src.Login

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivitySignupBinding
import com.example.helgather.src.Login.model.PostSignUpRequest
import com.example.helgather.src.Login.model.PostSignUpResponse
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.util.ErrorDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class SignUpActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate), LoginInterface{


    //비밀번호 패턴 체킹 -> 8자리 이상과 소,대문자
    val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=[\\\\]{};':\"\\\\|,.<>\\\\/?]).{8,}\$".toRegex()
    private val cal = Calendar.getInstance()
    private var birthYear = cal.get(Calendar.YEAR)
    private var birthMonth = cal.get(Calendar.MONTH)
    private var birthDay = cal.get(Calendar.DAY_OF_MONTH)
    private var birthDate : String = ""
    private var phoneNumber : String  = ""
    private var name : String = ""
    private var password : String =""
    private var passwordTwice : String = ""
    private var nickname : String = ""


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //닫음
        binding.ibtnSignUpClose.setOnClickListener{finish()}
        binding.tvSignUpBirth.setOnClickListener{setBirthDate()}
        setPhoneNumber() //핸드폰 번호 셋팅 -> 3-3-4혹은 3-4-4형식 맞게 textwatcher 사용

        allIsValid()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun allIsValid(){
        binding.btnSignUpDone.setOnClickListener {
            name = binding.edtSignUpName.text.toString()
            phoneNumber = binding.edtSignUpPhone.text.toString()
            nickname = binding.edtSignUpNickName.text.toString()
            password = binding.edtSignUpPassword.text.toString()
            passwordTwice = binding.edtSignUpPasswordTwice.text.toString()
            birthDate = binding.tvSignUpBirth.text.toString()
            if(birthDate.isNotEmpty()){
                birthYear = birthDate.substring(0, 4).toInt()
                birthMonth = birthDate.substring(5,7).toInt()
                birthDay = birthDate.substring(8,10).toInt()
            }

            val passwordCheck = binding.edtSignUpPassword.text.toString()
            if(!isValidBirthDate(birthDate)) {
                errorDialogStep("올바른 생년월일 형식을 확인하고\n 생년월일이 유효한지 확인해주세요.")
                binding.tvSignUpBirth.text= ""
            }else if(!passwordCheck.matches(passwordPattern)){
                errorDialogStep("비밀번호는 소문자, 대문자, 숫자, 특수문자를\n 각각 하나 이상 포함한 8자 이상이어야 합니다.")
            }else if(!isPhoneNumberValid(phoneNumber)){
                errorDialogStep("올바른 전화번호 형식을 입력해주세요.")
            }else if (password != passwordTwice){
                errorDialogStep(("패스워드가 일치하지 않습니다."))
            }else{  //클라이언트 딴에서 처리를 하고 이제 가입 통신을 함
                val signUpRequest = PostSignUpRequest(name,phoneNumber,nickname,password,birthYear,birthMonth,birthDay)
                Log.d("signUptest","$name $phoneNumber $nickname $password $birthYear $birthMonth $birthDay")
                LoginService(this@SignUpActivity).tryPostSignUp(signUpRequest)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setBirthDate(){
        val datePickerDialog = DatePickerDialog( this, { _, year, month, day ->
            val birthDate = LocalDate.of(year, month + 1, day)
            val formattedBirthDate = birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            binding.tvSignUpBirth.setText(formattedBirthDate)
        }, birthYear, birthMonth, birthDay)
        datePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isValidBirthDate(birthDate: String): Boolean {
        val currentYear = LocalDate.now().year
        return birthYear < currentYear
    }

    private fun setPhoneNumber(){
        binding.edtSignUpPhone.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private var previousText = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경이 발생한 경우 호출됨
                val currentText = s.toString()

                if (isFormatting || currentText == previousText) {
                    // 포맷팅 중인 경우나 이전 텍스트와 동일한 경우 추가적인 포맷팅 작업을 수행하지 않음
                    isFormatting = false
                    return
                }

                // 전화번호 형식으로 포맷팅
                phoneNumber = formatPhoneNumber(currentText)

                // 포맷팅된 텍스트로 수정
                isFormatting = true
                binding.edtSignUpPhone.setText(phoneNumber)
                binding.edtSignUpPhone.setSelection(phoneNumber.length)
            }

            override fun afterTextChanged(s: Editable?) {
                previousText = s.toString()
            }

            private fun formatPhoneNumber(phoneNumber: String): String {
                val digits = phoneNumber.replace("\\D".toRegex(), "")
                val formattedText = StringBuilder()

                if (digits.length >= 3) {
                    formattedText.append(digits.substring(0, 3))
                    if (digits.length >= 7) {
                        formattedText.append("-")
                        formattedText.append(digits.substring(3, 7))
                        if (digits.length >= 11) {
                            formattedText.append("-")
                            formattedText.append(digits.substring(7, 11))
                        } else {
                            formattedText.append(digits.substring(7))
                        }
                    } else {
                        formattedText.append(digits.substring(3))
                    }
                } else {
                    formattedText.append(digits)
                }

                return formattedText.toString()
            }
        })
    }

    //error에 대한 설정
    private fun errorDialogStep(message : String){
        val sharedPref = sSharedPreferences.edit()
        sharedPref.putString("errorType","가입 오류")
        sharedPref.putString("errorMessage",message)
        sharedPref.apply()
        ErrorDialog(this).show()
    }
    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        // 휴대전화 번호 정규식 패턴
        val pattern = "^(010|011|016|017|018|019)-[0-9]{3,4}-[0-9]{4}$"
        val regex = Regex(pattern)

        return regex.matches(phoneNumber)
    }

    override fun onPostJoinSuccess(response: PostSignUpResponse) {
        if(response.code == 200){
            Log.d("signUp","${response.postSignUpResult}")
            finish() //왜냐 액티비티에서 전달할때 finish하지 않앗기 때문에 로그인 창이 다시 onResume이 됨.
        }
        else{
            Log.d("signUp","200이아니야 ${response.message}")
            errorDialogStep(response.message.toString())
        }

    }

    override fun onPostJoinFailure(message: String) {
        showToastMessage("회원 가입 실패 사유 : $message")
    }
}