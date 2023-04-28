package com.example.helgather.config

import android.app.Application
import android.content.SharedPreferences

class ApplicationClass : Application() {
    //향후 BASE_URL이 나오게 된다면 추가 예정 + 추후에 레트로핏 진행 예정
    val API_URL = ""

    companion object{
        lateinit var sSharedPreferences: SharedPreferences

    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = applicationContext.getSharedPreferences("Hel_Gather_App", MODE_PRIVATE)

    }
}