package com.b1nd.alimo.presentation.feature.onboarding

import androidx.activity.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.ActivityOnboardingBinding
import com.b1nd.alimo.presentation.base.BaseActivity

class OnboardingActivity:BaseActivity<ActivityOnboardingBinding, OnboardingViewModel>(R.layout.activity_onboarding) {
    override val viewModel: OnboardingViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}