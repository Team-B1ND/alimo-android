package com.b1nd.alimo.presentation.feature.onboarding.second

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.repository.AlarmRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingSecondViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
): BaseViewModel() {
    private val _alarmState = MutableStateFlow(false)
    val alarmState = _alarmState.asStateFlow()

    fun onClickStart() = viewEvent(ON_CLICK_START)

    fun alarmCheck(){
        viewModelScope.launch {
            _alarmState.value = alarmRepository.getAlarmState()
        }
    }

    // 알림 권한을 허락 여부에 따라 알림 현재 상태 변경
    fun setAlarm(
        state: Boolean
    ){
        viewModelScope.launch {
            alarmRepository.setAlarmState(state)
        }
    }


    companion object{
        const val ON_CLICK_START = "ON_CLICK_START"
    }
}