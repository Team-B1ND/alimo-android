package com.b1nd.alimo.feature.onboarding.parent.pw.second

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentFindPwFirstBinding
import com.b1nd.alimo.databinding.FragmentParentFindPwSecondBinding
import com.b1nd.alimo.feature.onboarding.parent.pw.second.ParentFindPWSecondViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.feature.onboarding.parent.pw.second.ParentFindPWSecondViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.feature.onboarding.parent.pw.second.ParentFindPWSecondViewModel.Companion.ON_CLICK_DONE
import com.b1nd.alimo.utiles.onSuccessEvent

class ParentFindPWSecondFragment:BaseFragment<FragmentParentFindPwSecondBinding, ParentFindPWSecondViewModel>(
    R.layout.fragment_parent_find_pw_second
) {
    override val viewModel: ParentFindPWSecondViewModel by viewModels()

    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it){
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_DONE -> {
                        findNavController().navigate(R.id.action_parentFindPwSecond_to_onBoardingThird)
                    }
                    ON_CLICK_BACKGROUND -> {
                        mBinding.idEditTextLayout.clearFocus()
                        mBinding.pwEditTextLayout.clearFocus()
                        hideKeyboard()
                    }
                }
            }
        }

    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}