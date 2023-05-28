package com.example.helgather.src.Main.profile.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentProfileAuthBinding
import com.example.helgather.src.Main.profile.ProfileFragmentInterface
import com.example.helgather.src.Main.profile.ProfileService
import com.example.helgather.src.Main.profile.list.ProfileAuthAdapter
import com.example.helgather.src.Main.profile.model.GetSBDResponse
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PatchProfileImageResponse
import com.example.helgather.src.Main.profile.model.PatchProfileIntroductionResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse

class ProfileAuthFragment : BaseFragment<FragmentProfileAuthBinding> (FragmentProfileAuthBinding::bind , R.layout.fragment_profile_auth),
    ProfileFragmentInterface {

    val memberId = ApplicationClass.sSharedPreferences.getInt("memberId",0)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ProfileService(this@ProfileAuthFragment).tryGetTodayExercise(memberId)

    }

    override fun onGetTodayExerciseSuccess(response: GetTodayExerciseResponse) {
        Log.d("ProfileTest","$response")
        if(response.code == 200){
            binding.rvProfileAuth.apply {
                adapter = ProfileAuthAdapter(response.getTodayExerciseResult)
                layoutManager = GridLayoutManager(context,3)
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

    override fun onGetSBDSuccess(response: GetSBDResponse) {
    }

    override fun onGetSBDFailure(message: String) {
    }

    override fun onPatchProfileImageSuccess(response: PatchProfileImageResponse) {
    }

    override fun onPatchProfileImageFailure(message: String) {
    }

    override fun onPatchProfileIntroductionSuccess(response: PatchProfileIntroductionResponse) {
    }

    override fun onPatchProfileIntroductionFailure(message: String) {
    }
}