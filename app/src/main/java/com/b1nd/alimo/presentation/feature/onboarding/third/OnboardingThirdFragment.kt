package com.b1nd.alimo.presentation.feature.onboarding.third

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentOnboardingThirdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingThirdFragment:
    BaseFragment<FragmentOnboardingThirdBinding, OnboardingThirdViewModel>(
    R.layout.fragment_onboarding_third) {
    override val viewModel: OnboardingThirdViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}