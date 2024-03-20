package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.model.FirebaseTokenModel
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.FirebaseTokenService
import javax.inject.Inject

class FirebaseTokenRepository @Inject constructor(
    private val firebaseTokenService: FirebaseTokenService
){

    suspend fun getToken() = safeFlow<FirebaseTokenModel> {
        emit(
            firebaseTokenService.getToken()
        )
    }

    suspend fun insert(token: String){
        firebaseTokenService.insert(token)
    }

}
