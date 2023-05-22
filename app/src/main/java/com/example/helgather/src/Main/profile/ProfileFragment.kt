package com.example.helgather.src.Main.profile

import android.os.Bundle
import android.view.View
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentProfileBinding
import com.example.helgather.src.Main.profile.list.ProfileTabAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayoutControl()

    }

    private fun tabLayoutControl(){
        binding.vpProfile.apply {
            adapter = ProfileTabAdapter(this@ProfileFragment)
        }
        TabLayoutMediator(binding.tabLayout,binding.vpProfile){ tab, position ->
            when(position){
                0-> tab.text = "SBD 운동 인증"
                1-> tab.text = "오늘의 운동 인증"
            }
        }.attach()
    }

}