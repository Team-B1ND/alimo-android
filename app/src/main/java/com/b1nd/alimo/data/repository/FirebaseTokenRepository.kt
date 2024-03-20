package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.local.dao.FirebaseTokenDao
import com.b1nd.alimo.data.local.entity.FirebaseTokenEntity
import com.b1nd.alimo.data.model.FirebaseTokenModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.safeFlow
import javax.inject.Inject

class FirebaseTokenRepository @Inject constructor(
    private val firebaseTokenDao: FirebaseTokenDao
){

    suspend fun getToken() =
        safeFlow<FirebaseTokenModel> {
        val fcmToken = Resource.Success(firebaseTokenDao.getToken().toModel())
        emit(fcmToken)
    }

    suspend fun insert(fcmToken: String){
        val fcmToken = FirebaseTokenEntity(fcmToken = fcmToken)
        firebaseTokenDao.insert(fcmToken)
    }

}
