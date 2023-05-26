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

        val autoLogin = sSharedPreferences.getBoolean("autoLogin",false)



        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            if(autoLogin){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1000)
    }
}