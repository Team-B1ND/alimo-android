package com.b1nd.alimo.onboarding.third

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingThirdBinding

import com.b1nd.alimo.base.BaseFragment

class OnboardingThirdFragment:BaseFragment<FragmentOnboardingThirdBinding, OnboardingThirdViewModel>(
    R.layout.fragment_onboarding_third) {
    override val viewModel: OnboardingThirdViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}