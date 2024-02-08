package com.b1nd.alimo.presentation.feature.onboarding.second

import com.b1nd.alimo.presentation.base.BaseViewModel

class OnboardingSecondViewModel: BaseViewModel() {

    fun onClickStart() = viewEvent(ON_CLICK_START)


    companion object{
        const val ON_CLICK_START = "ON_CLICK_START"
    }
}