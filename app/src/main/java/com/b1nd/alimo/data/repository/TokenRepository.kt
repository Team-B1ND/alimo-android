package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.model.TokenModel
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.TokenService
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenService: TokenService
){
    suspend fun getToken() =
        safeFlow<TokenModel> {
            val token = tokenService.getToken()
            emit(token)
    }


    suspend fun insert(token: String, refreshToken: String) {
        tokenService.insert(token = token, refreshToken = refreshToken)
    }

    suspend fun deleteToken() {
        tokenService.deleteToken()
    }


}