package com.example.helgather.src.Main.profile.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentProfileSbdBinding
import com.example.helgather.databinding.ProfileSbdListBinding
import com.example.helgather.src.Main.profile.ProfileFragmentInterface
import com.example.helgather.src.Main.profile.ProfileService
import com.example.helgather.src.Main.profile.list.ProfileSBDAdapter
import com.example.helgather.src.Main.profile.model.GetSBDResponse
import com.example.helgather.src.Main.profile.model.GetSBDResult
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PatchProfileImageResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse

class ProfileSBDFragment : BaseFragment<FragmentProfileSbdBinding> (FragmentProfileSbdBinding::bind , R.layout.fragment_profile_sbd),
    ProfileFragmentInterface {

    private var memberId = 0;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        memberId = ApplicationClass.sSharedPreferences.getInt("memberId",0)


        ProfileService(this@ProfileSBDFragment).tryGetSBD(member = memberId)

    }
    override fun onGetSBDSuccess(response: GetSBDResponse) {
        binding.rvProfileSbd.apply {
            adapter = ProfileSBDAdapter(response.getSBDResult,object : ProfileSBDAdapter.ProfileSBDClickListener<GetSBDResult>{
                override fun onUploadClick(view: ProfileSbdListBinding, pos: Int) {
                    Log.d("sbdtest","업로드 클릭")
                }

                override fun onVideoClick(view: ProfileSbdListBinding, pos: Int) {
                    Log.d("sbdtest","비디오 클릭")
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onGetSBDFailure(message: String) {
        showToastMessage("오류 : $message")
    }

    override fun onGetTodayExerciseSuccess(response: GetTodayExerciseResponse) {}
    override fun onGetTodayExerciseFailure(message: String) {}
    override fun onPostTodayExerciseSuccess(response: PostTodayExerciseResponse) {}
    override fun onPostTodayExerciseFailure(message: String) {}
    override fun onPatchProfileImageSuccess(response: PatchProfileImageResponse) {
    }

    override fun onPatchProfileImageFailure(message: String) {
    }
}