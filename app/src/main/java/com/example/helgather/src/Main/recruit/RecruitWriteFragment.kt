package com.example.helgather.src.Main.recruit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentPostWriteBinding
import com.example.helgather.src.Main.MainActivity

class RecruitWriteFragment : BaseFragment<FragmentPostWriteBinding>(FragmentPostWriteBinding::bind, R.layout.fragment_post_write) {

    private var title : String ?= null
    private var locationCity : String ?= null
    private var locationDetail : String ?= null
    private var detail : String ?= null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPostWriteLocation.setOnClickListener {

//            parentFragmentManager.beginTransaction().add()
        }

        binding.btnPostWriteDone.setOnClickListener {
            title = binding.tvPostWrtieTitle.text.toString()
            locationCity = binding.tvPostWriteLocationCity.text.toString()
            locationDetail = binding.tvPostWriteLocationDetail.text.toString()
            detail = binding.tvPostWriteLocationDetail.text.toString()

            if(title == null){
                showToastMessage("제목을 입력해주세요")
            }else if(locationCity == null || locationDetail == null){
                showToastMessage("헬스 할 장소를 입력해주세요.")
            }else if(detail == null){
                showToastMessage("내용을 입력해주세요.")
            }

            //PostService(this@PostWriteFragment).tryPostPost
        }


    }

    private fun textMaintain(){
        binding.edtPostWriteTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 시 호출되는 콜백
                title = binding.edtPostWriteTitle.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.edtPostWriteDetail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 시 호출되는 콜백
                title = binding.edtPostWriteDetail.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)


    }



}