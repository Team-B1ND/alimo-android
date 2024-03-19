package com.b1nd.alimo.presentation.utiles

import android.app.Activity
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlimoApplication: Application() {
    var nowActivity: Pair<Activity?, String?> = Pair(null, null)
    fun setActivity(activity: Activity, name: String) {
        nowActivity = Pair(activity, name)
//        Log.d("TAG", "setActivity: ${nowActivity.first} ${nowActivity.second}")
    }
}