package com.b1nd.alimo.feature.onboarding.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentOnboardingFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFirstFragment:BaseFragment<FragmentOnboardingFirstBinding, com.b1nd.alimo.feature.onboarding.first.OnboardingFirstViewModel>(
    R.layout.fragment_onboarding_first) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.first.OnboardingFirstViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}