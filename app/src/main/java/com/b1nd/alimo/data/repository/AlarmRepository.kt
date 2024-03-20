package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.remote.service.AlarmService
import javax.inject.Inject


class AlarmRepository @Inject constructor(
    private val alarmService: AlarmService
){
    suspend fun getAlarmState() =
        alarmService.getAlarmState()


    suspend fun setAlarmState(value: Boolean) =
        alarmService.setAlarmState(value)
}
