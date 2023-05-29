package com.example.helgather.src.Main.profile.view


import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.MediaController
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentSbdVideoBinding
import com.example.helgather.src.Main.MainActivity

class ProfileSBDVideoFragment : BaseFragment<FragmentSbdVideoBinding>(FragmentSbdVideoBinding::bind , R.layout.fragment_sbd_video) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewContainer.rotation = 90f

        val sbdUrl = arguments?.getString("sbdUrl")
        binding.videoSbd.setVideoURI(Uri.parse(sbdUrl))
        binding.videoSbd.setOnPreparedListener { mediaPlayer ->
            val videoWidth = mediaPlayer.videoWidth
            val videoHeight = mediaPlayer.videoHeight

            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels

            val adjustedHeight = (screenWidth.toFloat() / videoWidth) * videoHeight
            val layoutParams = binding.videoSbd.layoutParams
            layoutParams.height = adjustedHeight.toInt()
            binding.videoSbd.layoutParams = layoutParams

            // 부모 뷰의 크기 조정하여 비디오 중앙에 맞추기
            val parentLayoutParams = binding.viewContainer.layoutParams
            parentLayoutParams.width = screenWidth
            parentLayoutParams.height = adjustedHeight.toInt()
            binding.viewContainer.layoutParams = parentLayoutParams
            binding.viewContainer.requestLayout()
        }

        // 비디오 재생 시작
        binding.videoSbd.start()

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoSbd)
        binding.videoSbd.setMediaController(mediaController)

        binding.videoSbd.setOnCompletionListener {
            parentFragmentManager.popBackStack()
        }
    }


    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavi(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        parentFragmentManager.popBackStack()
    }

}