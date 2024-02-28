package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.model.TokenModel

interface TokenService {
    suspend fun getToken(): TokenModel

    suspend fun insert(token: String,refreshToken: String)
}