package com.b1nd.alimo.feature.onboarding.second

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R

import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentOnboardingSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingSecondFragment:BaseFragment<FragmentOnboardingSecondBinding, com.b1nd.alimo.feature.onboarding.second.OnboardingSecondViewModel>(
    R.layout.fragment_onboarding_second) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.second.OnboardingSecondViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}