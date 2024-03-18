package com.b1nd.alimo.presentation.feature.main.profile.student

import com.b1nd.alimo.presentation.base.BaseViewModel


class ProfileStudentCodeViewModel: BaseViewModel() {

    fun onClickClose() =
        viewEvent(ON_CLICK_CLOSE)

    fun onClickCopy() {
        viewEvent(ON_CLICK_COPY)
    }

    companion object {
        const val ON_CLICK_CLOSE = "ON_CLICK_CLOSE"
        const val ON_CLICK_COPY = "ON_CLICK_COPY"
    }
}