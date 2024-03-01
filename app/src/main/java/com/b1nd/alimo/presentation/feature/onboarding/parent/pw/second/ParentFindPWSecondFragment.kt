package com.b1nd.alimo.presentation.feature.onboarding.parent.pw.second

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentFindPwSecondBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.pw.second.ParentFindPWSecondViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.pw.second.ParentFindPWSecondViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.pw.second.ParentFindPWSecondViewModel.Companion.ON_CLICK_DONE
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentFindPWSecondFragment:
    BaseFragment<FragmentParentFindPwSecondBinding, ParentFindPWSecondViewModel>(
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
                        view?.hideKeyboard()
                    }
                }
            }
        }

    }


}