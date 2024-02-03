package com.b1nd.alimo.presentation.feature.onboarding.parent.join.third

import com.b1nd.alimo.presentation.base.BaseViewModel

class ParentJoinThirdViewModel: BaseViewModel() {
    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)
    fun onClickJoin() = viewEvent(ON_CLICK_JOIN)
    fun onClickCertification() = viewEvent(ON_CLICK_CERTIFICATION)
    fun onClickCheck() = viewEvent(ON_CLICK_CHECK)

    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
        const val ON_CLICK_JOIN = "ON_CLICK_JOIN"
        const val ON_CLICK_CERTIFICATION = "ON_CLICK_CERTIFICATION"
        const val ON_CLICK_CHECK = "ON_CLICK_CHECK"

    }
}