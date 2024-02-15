package com.b1nd.alimo.presentation.feature.onboarding.student.first

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.local.dao.FirebaseTokenDao
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class StudentLoginViewModel @Inject constructor(
    private val firebaseTokenRepository: FirebaseTokenRepository,
    private val firebaseTokenDao: FirebaseTokenDao
): BaseViewModel() {

    fun tokenCheck(){
        viewModelScope.launch {
            val tokenEntity = firebaseTokenDao.getToken()
            println("Token: ${tokenEntity.fcmToken}")
        }
    }
    fun onClickBack() = viewEvent(ON_CLICK_BACK)

    fun onClickLoginOn() = viewEvent(ON_CLICK_LOGIN_ON)


    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)

    fun onClickLoginOff() = viewEvent(ON_CLICK_LOGIN_OFF)

    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_LOGIN_ON = "ON_CLICK_LOGIN_ON"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
        const val ON_CLICK_LOGIN_OFF = "ON_CLICK_LOGIN_OFF"
    }
}