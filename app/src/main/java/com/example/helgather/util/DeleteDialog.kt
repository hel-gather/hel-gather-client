package com.example.helgather.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.helgather.databinding.DeleteDialogBinding

class DeleteDialog(context : Context) : Dialog(context) {

    private lateinit var binding : DeleteDialogBinding
    private var listener: DeleteDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DeleteDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)

        binding.btnDeleteDialogYes.setOnClickListener {
            listener?.onPositiveButtonClicked()
            dismiss()
        }

        binding.btnDeleteDialogNo.setOnClickListener {
            listener?.onNegativeButtonClicked()
            dismiss()
        }

    }
}