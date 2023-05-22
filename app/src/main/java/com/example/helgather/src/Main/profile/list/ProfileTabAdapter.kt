package com.example.helgather.src.Main.profile.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.helgather.src.Main.profile.ProfileAuthFragment
import com.example.helgather.src.Main.profile.ProfileFragment
import com.example.helgather.src.Main.profile.ProfileSBDFragment

class ProfileTabAdapter(fragment : ProfileFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =2
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ProfileSBDFragment()
            1 -> ProfileAuthFragment()
            else -> ProfileSBDFragment()
        }
    }
}