package com.b1nd.alimo.presentation.feature.onboarding.first


import android.os.Handler
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment

class OnboardingFirstFragment:BaseFragment<FragmentOnboardingFirstBinding, OnboardingFirstViewModel>(R.layout.fragment_onboarding_first) {
    override val viewModel: OnboardingFirstViewModel by viewModels()

    override fun initView() {
        Handler().postDelayed({
            findNavController().navigate(R.id.action_onboardingFirst_to_onboardingSecond)
        }, 2000)
    }
}