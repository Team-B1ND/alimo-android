package com.b1nd.alimo.presentation.feature.onboarding

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.ActivityOnboardingBinding
import com.b1nd.alimo.presentation.base.BaseActivity
import com.b1nd.alimo.presentation.utiles.AlimoApplication
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity:BaseActivity<ActivityOnboardingBinding, OnboardingViewModel>(R.layout.activity_onboarding) {
    override val viewModel: OnboardingViewModel by viewModels()

    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUpdate()
    }


    override fun initView() {
//        TODO("Not yet implemented")
    }

    // 토큰 에러 설정
    override fun onStart() {
        super.onStart()
        (application as AlimoApplication).setActivity(this, "OnboardingActivity")

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 30) { // 알림 권한 요청 코드인지 확인
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 알림 권한이 허용된 경우
                // 권한이 허용되었을 때 실행할 작업을 여기에 추가
                viewModel.setAlarm(true)
//                Toast.makeText(this, "알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 알림 권한이 거부된 경우
                // 권한이 거부되었을 때 실행할 작업을 여기에 추가
                viewModel.setAlarm(false)
//                Toast.makeText(this, "알림 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUpdate() {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    appUpdateManager.startUpdateFlow(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                    )
                }
            }
    }

    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlow(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                    )
                }
            }
    }
}