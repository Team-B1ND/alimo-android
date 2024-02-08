package com.b1nd.alimo.presentation.feature.onboarding.parent.join.second

import com.b1nd.alimo.presentation.base.BaseViewModel

class ParentJoinSecondViewModel: BaseViewModel() {

    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickNext() = viewEvent(ON_CLICK_NEXT)
    fun onClickLogin() = viewEvent(ON_CLICK_LOGIN)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)
    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_NEXT = "ON_CLICK_NEXT"
        const val ON_CLICK_LOGIN = "ON_CLICK_LOGIN"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
    }
}