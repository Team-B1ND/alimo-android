package com.b1nd.alimo.presentation.feature.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.b1nd.alimo.R
import com.b1nd.alimo.data.repository.AlarmRepository
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.presentation.feature.onboarding.OnboardingActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessageService : FirebaseMessagingService() {

    @Inject
    lateinit var firebaseTokenRepository: FirebaseTokenRepository

    @Inject
    lateinit var alarmRepository: AlarmRepository


    private val serviceScope = CoroutineScope(Dispatchers.IO)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "onNewToken: 위에")
        serviceScope.launch {
            Log.d("TAG", "onNewToken: $token")
            // FirebaseToken을 Database에 저장
            firebaseTokenRepository.insert(
                token
            )
        }

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("TAG", "onMessageReceived Data: ${message.data} ")
        Log.d(
            "TAG",
            "onMessageReceived Noti: ${message.notification?.title}  and ${message.notification?.body} "
        )

        // 알림 현재 상태
        var status: Boolean
        runBlocking {
            alarmRepository.getAlarmState().let {
                status = it
            }
        }
        // 현재 상태가 False면 리턴을 통해 Push Message를 받지않음
        if (status.not()) {
            return
        }
        val title = message.notification?.title
        val body = message.notification?.body
        val data = message.data
        val type = data["type"]
        Log.d("TAG", "title: $title, body: $body, data: $data, type: $type ")
//        serviceScope.launch {
//            alarmInsertUseCase.invoke(AlarmModel(
//                idx = 0,
//                title = title!!,
//                body = body!!,
//                type = type!!
//            ))
//        }

        // 알림 권한 창
        createNotificationChannel()

        // 알림을 클릭했을 때 열릴 액티비티 지정
        val intent = Intent(this, OnboardingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 소리
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, "Alimo_default_channels")
            .setSmallIcon(R.drawable.ic_alimo) // 알림 아이콘
            .setContentTitle("Alimo") // 알림 제목
            .setContentText("$body") // 알림 내용
            .setAutoCancel(true) // 알림을 클릭하면 자동으로 닫힘
            .setSound(defaultSoundUri) // 알림 소리
            .setContentIntent(pendingIntent) // 알림 클릭 시 실행될 Intent

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }


    // 알림 권한 창
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alimo Default Channel"
            val descriptionText = "Alimo Default Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Alimo_default_channels", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}