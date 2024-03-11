package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.TokenModel
import kotlinx.coroutines.flow.Flow

interface TokenService {
    suspend fun getToken(): Flow<Resource<TokenModel>>

    suspend fun insert(token: String,refreshToken: String)

    suspend fun deleteToken()
}