package com.example.helgather.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.helgather.config.ApplicationClass.Companion.sSharedPreferences
import com.example.helgather.databinding.ErrorDialogBinding

class ErrorDialog(context: Context) : Dialog(context) {
    private lateinit var binding : ErrorDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ErrorDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false) // false -> 화면 밖 터치 맊기 위함


        val message = sSharedPreferences.getString("errorMessage",null).toString()
        val errorMessage = sSharedPreferences.getString("errorType",null).toString()

        binding.tvErrorDialogTitle.text = errorMessage
        binding.tvErrorDialogReason.text = message

        binding.tvErrorDialogCheck.setOnClickListener {
            dismiss()
        }

    }
}