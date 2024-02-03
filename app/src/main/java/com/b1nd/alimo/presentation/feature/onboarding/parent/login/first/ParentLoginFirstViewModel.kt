package com.b1nd.alimo.presentation.feature.onboarding.parent.login.first

import com.b1nd.alimo.presentation.base.BaseViewModel

class ParentLoginFirstViewModel: BaseViewModel() {
    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickFindPW() = viewEvent(ON_CLICK_FIND_PW)
    fun onClickLogin() = viewEvent(ON_CLICK_LOGIN)
    fun onClickJoin() = viewEvent(ON_CLICK_JOIN)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)





    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_FIND_PW = "ON_CLICK_FIND_PW"
        const val ON_CLICK_LOGIN = "ON_CLICK_LOGIN"
        const val ON_CLICK_JOIN = "ON_CLICK_JOIN"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
    }
}