package com.b1nd.alimo.presentation.feature.onboarding

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.repository.AlarmRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
):BaseViewModel() {
    private val _alarmState = MutableStateFlow(false)
    val alarmState = _alarmState.asStateFlow()


    fun setAlarm(
        state: Boolean
    ){
        viewModelScope.launch {
            alarmRepository.setAlarmState(state)
        }
    }
}