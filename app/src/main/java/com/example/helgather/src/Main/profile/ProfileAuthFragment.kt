package com.example.helgather.src.Main.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentProfileAuthBinding
import com.example.helgather.src.Main.profile.list.ProfileAuthAdapter
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse
import com.example.helgather.util.GridSpacingItemDecoration

class ProfileAuthFragment : BaseFragment<FragmentProfileAuthBinding> (FragmentProfileAuthBinding::bind , R.layout.fragment_profile_auth),ProfileFragmentInterface{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val memberId = sSharedPreferences.getInt("memberId",0)

        ProfileService(this@ProfileAuthFragment).tryGetTodayExercise(member = 1)

    }

    override fun onGetTodayExerciseSuccess(response: GetTodayExerciseResponse) {
        Log.d("ProfileTest","$response")
        if(response.code == 200){
            binding.rvProfileAuth.apply {
                adapter = ProfileAuthAdapter(response.getTodayExerciseResult)
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            //layoutManager = GridLayoutManager(context,3)
                //addItemDecoration(GridSpacingItemDecoration(5,3))
            }
        }
    }

    override fun onGetTodayExerciseFailure(message: String) {
        showToastMessage(message)
    }

    override fun onPostTodayExerciseSuccess(response: PostTodayExerciseResponse) {
    }

    override fun onPostTodayExerciseFailure(message: String) {
    }
}