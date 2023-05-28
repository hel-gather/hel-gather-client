package com.example.helgather.src.Main.profile.view

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.helgather.R
import com.example.helgather.config.ApplicationClass
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentProfileEditBinding
import com.example.helgather.src.Main.MainActivity
import com.example.helgather.src.Main.profile.ProfileFragmentInterface
import com.example.helgather.src.Main.profile.ProfileService
import com.example.helgather.src.Main.profile.model.GetSBDResponse
import com.example.helgather.src.Main.profile.model.GetTodayExerciseResponse
import com.example.helgather.src.Main.profile.model.PatchProfileImageResponse
import com.example.helgather.src.Main.profile.model.PatchProfileIntroductionRequest
import com.example.helgather.src.Main.profile.model.PatchProfileIntroductionResponse
import com.example.helgather.src.Main.profile.model.PostTodayExerciseResponse

class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>(FragmentProfileEditBinding::bind , R.layout.fragment_profile_edit),ProfileFragmentInterface {


    private var memberId = ApplicationClass.sSharedPreferences.getInt("memberId",0)
    private var memberName = ApplicationClass.sSharedPreferences.getString("memberName","헬서")
    private var squat  = 0
    private var benchpress = 0
    private var deadlift = 0
    private var introduction = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        squat = bundle?.getString("squat")!!.toInt()
        benchpress = bundle?.getString("benchpress")!!.toInt()
        deadlift = bundle?.getString("deadlift")!!.toInt()
        introduction = bundle?.getString("introduction")!!

        Log.d("profileTest2","$squat $benchpress $deadlift $introduction")



        binding.edtProfileEditSquat.setText(squat.toString())
        binding.edtProfileEditBenchpress.setText(benchpress.toString())
        binding.edtProfileEdtDeadlift.setText(deadlift.toString())
        binding.edtProfileEditIntroduction.setText(introduction)
        binding.tvProfileEditId.text = memberName


        binding.btnProfileEditComplete.setOnClickListener {
            ProfileService(this@ProfileEditFragment)
                .tryPatchIntroduction(memberId, PatchProfileIntroductionRequest(squat,benchpress,deadlift,introduction))
        }




    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)
    }

    override fun onBackPressed() {
        parentFragmentManager.popBackStack()
    }


    override fun onPatchProfileIntroductionSuccess(response: PatchProfileIntroductionResponse) {
        if(response.code == 200){
            parentFragmentManager.popBackStack()
        }else{
            showToastMessage(response.message)
        }
    }
    override fun onPatchProfileIntroductionFailure(message: String) {
        showToastMessage("오류 : $message")
    }



    override fun onGetTodayExerciseSuccess(response: GetTodayExerciseResponse) {}
    override fun onGetTodayExerciseFailure(message: String) {}
    override fun onPostTodayExerciseSuccess(response: PostTodayExerciseResponse) {}
    override fun onPostTodayExerciseFailure(message: String) {}
    override fun onGetSBDSuccess(response: GetSBDResponse) {}
    override fun onGetSBDFailure(message: String) {}
    override fun onPatchProfileImageSuccess(response: PatchProfileImageResponse) {}
    override fun onPatchProfileImageFailure(message: String) {}

}
