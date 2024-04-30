package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.DialogIncorrectCodeBinding
import com.b1nd.alimo.presentation.base.BaseDialogFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.IncorrectCodeViewModel.Companion.ON_CLICK_CLOSE
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncorrectCodeDialog: BaseDialogFragment<DialogIncorrectCodeBinding, IncorrectCodeViewModel>(R.layout.dialog_incorrect_code) {

    override val viewModel: IncorrectCodeViewModel by viewModels(
        { requireActivity() }
    )

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view?.setPadding(1, 0, 1, 0)
        view?.z = 1f
    }

    override fun initView() {

        bindingViewEvent { evnet ->
            evnet.onSuccessEvent {
                when(it) {
                    ON_CLICK_CLOSE -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            dialog?.dismiss()
                        }
                    }
                }
            }
        }
    }
}

