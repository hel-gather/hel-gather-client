package com.example.helgather.src.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.helgather.R
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivityMainBinding
import com.example.helgather.src.Main.chatting.ChattingFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBottomNavi()
    }

    private fun runBottomNavi() {
        binding.btmNavMain.run {
            setOnItemSelectedListener { item ->
                val selectedFragment: Fragment = when (item.itemId) {
                    R.id.btm_home -> HomeFragment()
                    R.id.btm_chatting -> ChattingFragment()
                    R.id.btm_post -> PostFragment()
                    R.id.btm_profile -> ProfileFragment()
                    else -> HomeFragment()
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.frm_main, selectedFragment)
                    .addToBackStack(null) // 백스택에 현재 프래그먼트를 추가
                    .commitAllowingStateLoss()

                true
            }
            selectedItemId = R.id.btm_home
        }
    }
}