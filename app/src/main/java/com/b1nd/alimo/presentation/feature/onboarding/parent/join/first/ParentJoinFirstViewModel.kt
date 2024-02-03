package com.b1nd.alimo.feature.onboarding.parent.join.first

import com.b1nd.alimo.base.BaseViewModel
import com.b1nd.alimo.feature.onboarding.parent.login.first.ParentLoginFirstViewModel

class ParentJoinFirstViewModel:BaseViewModel() {

    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickLogin() = viewEvent(ON_CLICK_LOGIN)
    fun onClickNext() = viewEvent(ON_CLICK_NEXT)
    fun onClickStudentCode() = viewEvent(ON_CLICK_STUDENT_CODE)

    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_LOGIN = "ON_CLICK_LOGIN"
        const val ON_CLICK_NEXT = "ON_CLICK_NEXT"
        const val ON_CLICK_STUDENT_CODE = "ON_CLICK_STUDENT_CODE"
    }
}