package com.b1nd.alimo.feature.onboarding.parent.pw.second

import com.b1nd.alimo.base.BaseViewModel

class ParentFindPWSecondViewModel:BaseViewModel() {
    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickDone() = viewEvent(ON_CLICK_DONE)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)

    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_DONE = "ON_CLICK_DONE"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
    }
}