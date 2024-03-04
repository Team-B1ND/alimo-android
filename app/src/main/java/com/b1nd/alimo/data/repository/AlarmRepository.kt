package com.b1nd.alimo.data.repository

import android.content.SharedPreferences
import com.b1nd.alimo.data.remote.service.AlarmService
import javax.inject.Inject

class AlarmRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
): AlarmService{
    override suspend fun getAlarmState() =
        sharedPreferences.getBoolean("alarm", false) ?: true

    override suspend fun setAlarmState(value: Boolean) =
        sharedPreferences.edit().putBoolean("alarm", value).apply()
}