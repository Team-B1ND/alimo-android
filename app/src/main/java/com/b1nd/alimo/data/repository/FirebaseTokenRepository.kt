package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.local.dao.FirebaseTokenDao
import com.b1nd.alimo.data.local.entity.FirebaseTokenEntity
import com.b1nd.alimo.data.model.FirebaseTokenModel
import com.b1nd.alimo.data.remote.service.FirebaseTokenService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseTokenRepository @Inject constructor(
    private val firebaseTokenDao: FirebaseTokenDao
):FirebaseTokenService {
    override suspend fun getToken(): Flow<Resource<FirebaseTokenModel>> = flow {
        emit(Resource.Loading())
        try {
            val token = firebaseTokenDao.getToken()?.fcmToken
            if(token != null){
                emit(Resource.Success(data = FirebaseTokenModel(token)))
            }
        } catch (e: Exception){
            emit(Resource.Error(e))
        }
    }
    override suspend fun insert(token: String) {
        val firebaseTokenEntity = FirebaseTokenEntity(fcmToken = token)
        firebaseTokenDao.insert(firebaseTokenEntity)
    }


}