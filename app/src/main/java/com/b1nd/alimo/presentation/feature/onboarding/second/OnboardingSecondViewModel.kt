package com.b1nd.alimo.presentation.feature.onboarding.second

import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingSecondViewModel @Inject constructor(

): BaseViewModel() {

    fun onClickStart() = viewEvent(ON_CLICK_START)


    companion object{
        const val ON_CLICK_START = "ON_CLICK_START"
    }
}