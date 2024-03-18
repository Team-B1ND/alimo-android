package com.b1nd.alimo.presentation.feature.main.profile.withdrawal

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.DialogWithdrawalBinding
import com.b1nd.alimo.presentation.base.BaseDialogFragment
import com.b1nd.alimo.presentation.feature.main.profile.withdrawal.ProfileWithdrawalViewModel.Companion.ON_CLICK_CLOSE
import com.b1nd.alimo.presentation.feature.main.profile.withdrawal.ProfileWithdrawalViewModel.Companion.ON_CLICK_WITHDRAWAL
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileWithdrawalDialog(
    private val onClickListener: ProfileWithdrawalClickListener
): BaseDialogFragment<DialogWithdrawalBinding, ProfileWithdrawalViewModel>(R.layout.dialog_withdrawal) {

    override val viewModel: ProfileWithdrawalViewModel by viewModels()

    override fun initView() {
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        bindingViewEvent {  event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_CLOSE -> {
                        dialog?.dismiss()
                    }
                    ON_CLICK_WITHDRAWAL -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            onClickListener.onWithdrawal()
                            dialog?.dismiss()
                        }
                    }
                }
            }
        }
    }
}