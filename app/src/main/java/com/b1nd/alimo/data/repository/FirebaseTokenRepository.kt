package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.local.dao.FirebaseTokenDao
import com.b1nd.alimo.data.local.entity.FirebaseTokenEntity
import com.b1nd.alimo.data.remote.service.FirebaseTokenService
import javax.inject.Inject

class FirebaseTokenRepository @Inject constructor(
    private val firebaseTokenDao: FirebaseTokenDao
):FirebaseTokenService {
    override suspend fun getToken(): FirebaseTokenEntity = firebaseTokenDao.getToken()
    override suspend fun insert(token: String) {
        val firebaseTokenEntity = FirebaseTokenEntity(fcmToken = token)
        firebaseTokenDao.insert(firebaseTokenEntity)
    }


}