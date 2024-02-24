package com.b1nd.alimo.presentation.feature.onboarding.parent.pw.second

import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ParentFindPWSecondViewModel @Inject constructor(

): BaseViewModel() {
    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickDone() = viewEvent(ON_CLICK_DONE)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)

    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_DONE = "ON_CLICK_DONE"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
    }
}