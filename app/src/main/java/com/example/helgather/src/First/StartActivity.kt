package com.example.helgather.src.First

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.helgather.R
import com.example.helgather.config.BaseActivity
import com.example.helgather.databinding.ActivityStartBinding
import com.example.helgather.src.Login.LoginActivity

class StartActivity : BaseActivity<ActivityStartBinding>(ActivityStartBinding::inflate) {

    val deadliftList = listOf<Int>(R.drawable.ic_start_deadlift1
        ,R.drawable.ic_start_deadlift2,R.drawable.ic_start_deadlift3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.btnStartStart.setOnClickListener {
            val intent = Intent(this@StartActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.vpStartWeightUp.run {
            adapter = StartDeadliftApdater(deadliftList)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        val nsv = binding.nsvStart
        nsv.viewTreeObserver.addOnScrollChangedListener {
            val visibleTop = nsv.scrollY
            val visibleBottom = visibleTop + nsv.measuredHeight

            val vpTop = binding.vpStartWeightUp.top
            val vpBottom = binding.vpStartWeightUp.bottom

            val position = when {
                visibleBottom <= vpBottom -> 0
                visibleBottom <= vpBottom + 500 -> 1
                else -> 2
            }
            binding.vpStartWeightUp.currentItem = position
        }












    }
}