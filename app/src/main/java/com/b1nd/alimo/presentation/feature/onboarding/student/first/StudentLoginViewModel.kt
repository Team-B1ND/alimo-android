package com.b1nd.alimo.feature.onboarding.student.first

import com.b1nd.alimo.base.BaseViewModel

class StudentLoginViewModel:BaseViewModel() {
    fun onClickBack() = viewEvent(ON_CLICK_BACK)

    fun onClickLoginOn() = viewEvent(ON_CLICK_LOGIN_ON)


    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)

    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_LOGIN_ON = "ON_CLICK_LOGIN_ON"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
    }
}