package com.b1nd.alimo.data.remote.service

import android.content.SharedPreferences
import javax.inject.Inject

class AlarmService @Inject constructor(
private val sharedPreferences: SharedPreferences
){
    fun getAlarmState(): Boolean =
        sharedPreferences.getBoolean("alarm", false) ?: true


    fun setAlarmState(value: Boolean) =
        sharedPreferences.edit().putBoolean("alarm", value).apply()

}