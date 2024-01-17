package com.b1nd.alimo.presentation.feature.onboarding.second

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R

import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentOnboardingSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingSecondFragment:
    BaseFragment<FragmentOnboardingSecondBinding, OnboardingSecondViewModel>(
    R.layout.fragment_onboarding_second) {
    override val viewModel: OnboardingSecondViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}