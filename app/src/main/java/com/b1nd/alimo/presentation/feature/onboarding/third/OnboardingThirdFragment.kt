package com.b1nd.alimo.presentation.feature.onboarding.third

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingThirdBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.third.OnboardingThirdViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.third.OnboardingThirdViewModel.Companion.ON_CLICK_DODAM_LOGIN
import com.b1nd.alimo.presentation.feature.onboarding.third.OnboardingThirdViewModel.Companion.ON_CLICK_JOIN
import com.b1nd.alimo.presentation.feature.onboarding.third.OnboardingThirdViewModel.Companion.ON_CLICK_LOGIN
import com.b1nd.alimo.presentation.feature.onboarding.third.OnboardingThirdViewModel.Companion.ON_CLICK_PARENT
import com.b1nd.alimo.presentation.feature.onboarding.third.OnboardingThirdViewModel.Companion.ON_CLICK_STUDENT
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingThirdFragment:
    BaseFragment<FragmentOnboardingThirdBinding, OnboardingThirdViewModel>(
    R.layout.fragment_onboarding_third) {
    override val viewModel: OnboardingThirdViewModel by viewModels()

    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it){
                    ON_CLICK_STUDENT -> {
                        mBinding.parentImage.setImageResource(R.drawable.img_parent_off)
                        mBinding.studentImage.setImageResource(R.drawable.img_student_on)
                        mBinding.parentImage.setBackgroundResource(R.drawable.btn_shape_rounded_off)
                        mBinding.studentImage.setBackgroundResource(R.drawable.btn_shape_rounded_on_2)
                        mBinding.loginBtn.visibility = View.VISIBLE
                        mBinding.loginText.visibility = View.GONE
                        mBinding.joinBtn.visibility = View.GONE
                        mBinding.studentText.setTextColor(requireContext().getColor(R.color.Main900))
                        mBinding.parentText.setTextColor(requireContext().getColor(R.color.Gray500))

                    }
                    ON_CLICK_PARENT -> {
                        mBinding.studentImage.setImageResource(R.drawable.img_student_off)
                        mBinding.parentImage.setImageResource(R.drawable.img_parent_on)
                        mBinding.studentImage.setBackgroundResource(R.drawable.btn_shape_rounded_off)
                        mBinding.parentImage.setBackgroundResource(R.drawable.btn_shape_rounded_on_2)
                        mBinding.loginBtn.visibility = View.GONE
                        mBinding.loginText.visibility = View.VISIBLE
                        mBinding.joinBtn.visibility = View.VISIBLE
                        mBinding.parentText.setTextColor(requireContext().getColor(R.color.Main900))
                        mBinding.studentText.setTextColor(requireContext().getColor(R.color.Gray500))

                    }
                    ON_CLICK_LOGIN -> {
                        findNavController().navigate(R.id.action_onboardingThird_to_parentLoginFirst)
                    }
                    ON_CLICK_JOIN -> {
                        findNavController().navigate(R.id.action_onboardingThird_to_parentJoinFirst)
                    }
                    ON_CLICK_DODAM_LOGIN -> {
                        findNavController().navigate(R.id.action_onboardingThird_to_studentLoginFirst)
                    }
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }


    }
}