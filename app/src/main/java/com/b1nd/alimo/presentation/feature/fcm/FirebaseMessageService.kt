package com.b1nd.alimo.presentation.feature.fcm

import android.util.Log
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessageService : FirebaseMessagingService() {

    @Inject
    lateinit var firebaseTokenRepository: FirebaseTokenRepository


    private val serviceScope = CoroutineScope(Dispatchers.IO)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "onNewToken: 위에")
        serviceScope.launch {
            Log.d("TAG", "onNewToken: $token")
            firebaseTokenRepository.insert(
                token
            )
        }

    }


}