package com.b1nd.alimo.presentation.feature.onboarding.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentOnboardingFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFirstFragment:
    BaseFragment<FragmentOnboardingFirstBinding, OnboardingFirstViewModel>(
    R.layout.fragment_onboarding_first) {
    override val viewModel: OnboardingFirstViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}