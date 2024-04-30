package com.b1nd.alimo.presentation.feature.onboarding.parent.pw.first

import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ParentFindPWFirstViewModel @Inject constructor(

): BaseViewModel() {
    fun onClickCertification() = viewEvent(ON_CLICK_CERTIFICATION)
    fun onClickCheck() = viewEvent(ON_CLICK_CHECK)
    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickNext() = viewEvent(ON_CLICK_NEXT)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)


    companion object{
        const val ON_CLICK_CERTIFICATION = "ON_CLICK_CERTIFICATION"
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
        const val ON_CLICK_CHECK = "ON_CLICK_CHECK"
        const val ON_CLICK_NEXT = "ON_CLICK_NEXT"
    }
}