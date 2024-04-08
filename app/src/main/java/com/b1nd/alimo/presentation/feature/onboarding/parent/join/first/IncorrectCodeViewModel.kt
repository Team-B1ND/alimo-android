package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first


import com.b1nd.alimo.presentation.base.BaseViewModel

class IncorrectCodeViewModel: BaseViewModel() {

    fun onClickClose() =
        viewEvent(ON_CLICK_CLOSE)

    companion object {
        const val ON_CLICK_CLOSE = "ON_CLICK_CLOSE"
    }
}