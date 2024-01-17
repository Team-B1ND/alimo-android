package com.b1nd.alimo.onboarding.second

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R

import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentOnboardingSecondBinding

class OnboardingSecondFragment:BaseFragment<FragmentOnboardingSecondBinding, OnboardingSecondViewModel>(
    R.layout.fragment_onboarding_second) {
    override val viewModel: OnboardingSecondViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}