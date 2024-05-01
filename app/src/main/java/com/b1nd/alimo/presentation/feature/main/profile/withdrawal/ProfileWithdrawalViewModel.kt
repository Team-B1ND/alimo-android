package com.b1nd.alimo.presentation.feature.main.profile.withdrawal

import com.b1nd.alimo.presentation.base.BaseViewModel


class ProfileWithdrawalViewModel: BaseViewModel() {

    fun onClickClose() =
        viewEvent(ON_CLICK_CLOSE)

    fun onClickWithdrawal() =
        viewEvent(ON_CLICK_WITHDRAWAL)

    companion object {
        const val ON_CLICK_CLOSE = "ON_CLICK_CLOSE"
        const val ON_CLICK_WITHDRAWAL = "ON_CLICK_WITHDRAWAL"
    }
}