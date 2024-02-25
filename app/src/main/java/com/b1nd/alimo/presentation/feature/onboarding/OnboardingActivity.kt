package com.b1nd.alimo.presentation.feature.onboarding

import androidx.activity.viewModels
import com.b1nd.alimo.BuildConfig
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.ActivityOnboardingBinding
import com.b1nd.alimo.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.hs.dgsw.smartschool.dodamdodam.dauth.DAuth.settingDAuth

@AndroidEntryPoint
class OnboardingActivity:BaseActivity<ActivityOnboardingBinding, OnboardingViewModel>(R.layout.activity_onboarding) {
    override val viewModel: OnboardingViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }


    override fun onStart() {
        super.onStart()
        settingDAuth(
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET,
            BuildConfig.REDIRECT_URL)


    }
}