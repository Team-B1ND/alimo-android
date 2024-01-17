package com.b1nd.alimo.feature.onboarding

import androidx.activity.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseActivity
import com.b1nd.alimo.databinding.ActivityOnboardingBinding

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding, com.b1nd.alimo.feature.onboarding.OnboardingViewModel>(
    R.layout.activity_onboarding
) {

    override val viewModel: com.b1nd.alimo.feature.onboarding.OnboardingViewModel by viewModels()

    override fun initView() {
//        mBinding.onboardingFragment.getFragment<>()
        //        TODO("Not yet implemented")
    }

}
