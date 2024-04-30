package com.b1nd.alimo.presentation.feature.main.profile.student

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.DialogStudentCodeBinding
import com.b1nd.alimo.presentation.base.BaseDialogFragment
import com.b1nd.alimo.presentation.feature.main.profile.student.ProfileStudentCodeViewModel.Companion.ON_CLICK_CLOSE
import com.b1nd.alimo.presentation.feature.main.profile.student.ProfileStudentCodeViewModel.Companion.ON_CLICK_COPY
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileStudentCodeDialog constructor(
    private val onClickListener: ProfileStudentClickListener,
    private val studentCode: String?
): BaseDialogFragment<DialogStudentCodeBinding, ProfileStudentCodeViewModel>(R.layout.dialog_student_code) {


    override val viewModel: ProfileStudentCodeViewModel by viewModels({ requireParentFragment() })

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view?.setPadding(1, 0, 1, 0)
        view?.z = 1f
    }

    override fun initView() {
        mBinding.textStudentCode.text = studentCode
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_CLOSE -> {
                        dialog?.dismiss()
                    }
                    ON_CLICK_COPY -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            val manager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            manager.setPrimaryClip(
                                ClipData.newPlainText("학생코드가 복사되었습니다.", mBinding.textStudentCode.text.toString())
                            )
                            onClickListener.onCopy()
                            dialog?.dismiss()
                        }
                    }
                }
            }
        }
    }
}