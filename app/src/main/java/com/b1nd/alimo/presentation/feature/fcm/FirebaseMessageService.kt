package com.b1nd.alimo.presentation.feature.fcm

import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirebaseMessageService : FirebaseMessagingService() {

    @Inject
    lateinit var firebaseTokenRepository: FirebaseTokenRepository

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        serviceScope.launch {
            firebaseTokenRepository.insert(token)

        }

    }


}