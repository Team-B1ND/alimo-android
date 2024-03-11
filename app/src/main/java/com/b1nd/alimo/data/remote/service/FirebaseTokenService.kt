package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.FirebaseTokenModel
import kotlinx.coroutines.flow.Flow

interface FirebaseTokenService {
    suspend fun getToken(): Flow<Resource<FirebaseTokenModel>>

    suspend fun insert(token:String)
}