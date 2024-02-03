package com.b1nd.alimo.presentation.feature.onboarding.second

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R

import com.b1nd.alimo.databinding.FragmentOnboardingSecondBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.second.OnboardingSecondViewModel.Companion.ON_CLICK_START
import com.b1nd.alimo.presentation.utiles.onSuccessEvent

class OnboardingSecondFragment:
    BaseFragment<FragmentOnboardingSecondBinding, OnboardingSecondViewModel>(
    R.layout.fragment_onboarding_second) {
    override val viewModel: OnboardingSecondViewModel by viewModels()

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