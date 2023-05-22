package com.example.helgather.src.Main.profile

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentProfileSbdBinding
import com.example.helgather.databinding.ProfileSbdListBinding
import com.example.helgather.src.Main.profile.list.ProfileSBDAdapter
import com.example.helgather.src.Main.profile.model.ProfileSBDTest
import com.example.helgather.util.ErrorDialog

class ProfileSBDFragment : BaseFragment<FragmentProfileSbdBinding> (FragmentProfileSbdBinding::bind , R.layout.fragment_profile_sbd) {

    val sbdList = arrayListOf<ProfileSBDTest>(ProfileSBDTest("스쿼트",R.drawable.pic_preview),
        ProfileSBDTest("벤치프레스",R.drawable.pic_preview),
        ProfileSBDTest("데드리프트",R.drawable.pic_preview))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerViewControl()
    }

    private fun recyclerViewControl(){
        binding.rvProfileSbd.apply {
            adapter = ProfileSBDAdapter(sbdList, object : ProfileSBDAdapter.ProfileSBDClickListener<ProfileSBDTest>{
                override fun onUploadClick(view: ProfileSbdListBinding, pos: Int) {
                    ErrorDialog(context).show()
                }
                override fun onVideoClick(view: ProfileSbdListBinding, pos: Int) {
                    ErrorDialog(context).show()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }
}