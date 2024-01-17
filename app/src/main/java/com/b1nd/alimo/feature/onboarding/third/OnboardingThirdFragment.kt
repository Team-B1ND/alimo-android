package com.b1nd.alimo.feature.onboarding.third

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingThirdBinding

import com.b1nd.alimo.base.BaseFragment

class OnboardingThirdFragment:BaseFragment<FragmentOnboardingThirdBinding, com.b1nd.alimo.feature.onboarding.third.OnboardingThirdViewModel>(
    R.layout.fragment_onboarding_third) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.third.OnboardingThirdViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}