package com.b1nd.alimo.presentation.feature.main.profile.withdrawal

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.b1nd.alimo.databinding.DialogWithdrawalBinding


class ProfileWithdrawalDialog(
    private val onClickListener: ProfileWithdrawalClickListener
): DialogFragment() {

    private lateinit var binding: DialogWithdrawalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = DialogWithdrawalBinding.inflate(inflater, container, false)

        binding.layoutClose.setOnClickListener {
            dialog?.dismiss()
        }

        binding.layoutWithdrawal.setOnClickListener {
            onClickListener.onWithdrawal()
            dialog?.dismiss()
        }

        return binding.root
    }
}