package com.b1nd.alimo.feature.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseActivity
import com.b1nd.alimo.databinding.ActivityOnboardingBinding

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding, com.b1nd.alimo.feature.onboarding.OnboardingViewModel>(
    R.layout.activity_onboarding
) {

    override val viewModel: com.b1nd.alimo.feature.onboarding.OnboardingViewModel by viewModels()

    override fun initView() {

    }


}
