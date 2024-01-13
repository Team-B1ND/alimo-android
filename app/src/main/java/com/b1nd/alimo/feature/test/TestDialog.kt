package com.b1nd.alimo.feature.test

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.b1nd.alimo.databinding.DialogStudentCodeBinding

class TestDialog: DialogFragment() {

    private lateinit var binding: DialogStudentCodeBinding
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view?.setPadding(1, 0, 1, 0)
        view?.z = 1f

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogStudentCodeBinding.inflate(inflater, container, false)
        return binding.root
    }
}