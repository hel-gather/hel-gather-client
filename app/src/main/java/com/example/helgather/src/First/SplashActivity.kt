package com.example.helgather.src.First

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivitySplashBinding
import com.example.helgather.src.Login.LoginActivity
import com.example.helgather.src.Main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val autoLogin = sSharedPreferences.getInt("autoLogin",0)



        Handler(Looper.getMainLooper()).postDelayed({
            if(autoLogin == 1){
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                startActivity(Intent(this,MainActivity::class.java))
            }

            finish()
        }, 1000)
    }
}