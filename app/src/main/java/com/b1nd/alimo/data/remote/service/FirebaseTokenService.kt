package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.local.entity.FirebaseTokenEntity

interface FirebaseTokenService {
    suspend fun getToken():FirebaseTokenEntity

    suspend fun insert(token:String)
}