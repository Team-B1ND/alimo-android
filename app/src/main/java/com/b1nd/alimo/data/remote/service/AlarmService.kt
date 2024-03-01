package com.b1nd.alimo.data.remote.service

interface AlarmService {
    suspend fun getAlarmState():Boolean

    suspend fun setAlarmState(value: Boolean)
}