package com.example.helgather.src.Main

import android.os.Bundle
import android.view.View
import com.example.helgather.R
import com.example.helgather.config.BaseFragment
import com.example.helgather.databinding.FragmentPostBinding

class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::bind , R.layout.fragment_post) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}