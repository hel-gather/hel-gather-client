package com.example.helgather.src.Main

import android.os.Bundle
import android.view.View
import com.example.helgather.R
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivityMainBinding
import com.example.helgather.src.Main.chatting.ChattingFragment
import com.example.helgather.src.Main.post.PostFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBottomNavi()
    }

    private fun runBottomNavi() {
        binding.btmNavMain.run {
            setOnItemSelectedListener { item ->
                val fragmentTag: String = when (item.itemId) {
                    R.id.btm_home -> "HOME_FRAGMENT"
                    R.id.btm_chatting -> "CHATTING_FRAGMENT"
                    R.id.btm_post -> "POST_FRAGMENT"
                    R.id.btm_profile -> "PROFILE_FRAGMENT"
                    else -> "HOME_FRAGMENT"
                }

                var selectedFragment = supportFragmentManager.findFragmentByTag(fragmentTag)

                supportFragmentManager.beginTransaction().apply {
                    // 기존에 추가된 프래그먼트를 숨김
                    supportFragmentManager.fragments.forEach { hide(it) }

                    if (selectedFragment == null) {
                        selectedFragment = when (item.itemId) {
                            R.id.btm_home -> HomeFragment()
                            R.id.btm_chatting -> ChattingFragment()
                            R.id.btm_post -> PostFragment()
                            R.id.btm_profile -> ProfileFragment()
                            else -> HomeFragment()
                        }
                        add(R.id.frm_main, selectedFragment!!, fragmentTag)
                    } else {
                        // 선택된 프래그먼트를 표시
                        show(selectedFragment!!)
                    }
                }.commitAllowingStateLoss()

                true
            }
            selectedItemId = R.id.btm_home
        }
    }

    fun hideBottomNavi(hide: Boolean){
        if(hide)
            binding.btmNavMain.visibility = View.GONE
        else
            binding.btmNavMain.visibility = View.VISIBLE
    }


}