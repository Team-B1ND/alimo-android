package com.b1nd.alimo.feature.onboarding.second

import com.b1nd.alimo.base.BaseViewModel

class OnboardingSecondViewModel:BaseViewModel() {

    fun onClickStart() = viewEvent(ON_CLICK_START)


    companion object{
        const val ON_CLICK_START = "ON_CLICK_START"
    }
}