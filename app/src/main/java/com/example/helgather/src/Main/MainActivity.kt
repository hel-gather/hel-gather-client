package com.example.helgather.src.Main

import android.os.Bundle
import com.example.helgather.R
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivityMainBinding
import com.example.helgather.src.Main.chatting.ChattingFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBottomNavi()
    }

    private fun runBottomNavi(){
        binding.btmNavMain.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.btm_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frm_main, HomeFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.btm_chatting -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frm_main, ChattingFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.btm_post -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frm_main, PostFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.btm_profile -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frm_main, ProfileFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.btm_home
        }
    }
}