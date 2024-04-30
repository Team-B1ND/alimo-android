package com.b1nd.alimo.data.repository

import android.content.SharedPreferences
import javax.inject.Inject


class AlarmRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
){
    fun getAlarmState(): Boolean =
        sharedPreferences.getBoolean("alarm", false)


    fun setAlarmState(value: Boolean) =
        sharedPreferences.edit().putBoolean("alarm", value).apply()

}
