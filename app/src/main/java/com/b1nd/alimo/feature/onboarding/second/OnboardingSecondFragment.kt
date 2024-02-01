package com.b1nd.alimo.feature.onboarding.second

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R

import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentOnboardingSecondBinding
import com.b1nd.alimo.feature.onboarding.second.OnboardingSecondViewModel.Companion.ON_CLICK_START
import com.b1nd.alimo.utiles.onSuccessEvent

class OnboardingSecondFragment:BaseFragment<FragmentOnboardingSecondBinding, com.b1nd.alimo.feature.onboarding.second.OnboardingSecondViewModel>(
    R.layout.fragment_onboarding_second) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.second.OnboardingSecondViewModel by viewModels()

    override fun initView() {
        bindingViewEvent {event ->
            event.onSuccessEvent {
                when(it){
                    ON_CLICK_START ->{
                        findNavController().navigate(R.id.action_onboardingSecond_to_onboardingThird)
                    }
                }
            }
        }
    }
}