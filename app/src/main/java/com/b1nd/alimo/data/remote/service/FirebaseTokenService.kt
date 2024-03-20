package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.local.dao.FirebaseTokenDao
import com.b1nd.alimo.data.local.entity.FirebaseTokenEntity
import com.b1nd.alimo.data.model.FirebaseTokenModel
import com.b1nd.alimo.data.remote.mapper.toModel
import javax.inject.Inject

//interface FirebaseTokenService {
//    suspend fun getToken(): Flow<Resource<FirebaseTokenModel>>
//
//    suspend fun insert(token:String)
//}

class FirebaseTokenService @Inject constructor(
    private val firebaseTokenDao: FirebaseTokenDao
){
    suspend fun getToken(): Resource<FirebaseTokenModel> {
        return try {
            val fcmToken = firebaseTokenDao.getToken()
            Resource.Success(fcmToken.toModel())
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    suspend fun insert(token: String){
        val firebaseTokenEntity = FirebaseTokenEntity(fcmToken = token)
        firebaseTokenDao.insert(firebaseTokenEntity)
    }
}
