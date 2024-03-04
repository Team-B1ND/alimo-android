package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.local.dao.TokenDao
import com.b1nd.alimo.data.local.entity.TokenEntity
import com.b1nd.alimo.data.model.TokenModel
import com.b1nd.alimo.data.model.toModel
import com.b1nd.alimo.data.remote.service.TokenService
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenDao: TokenDao
): TokenService {
    override suspend fun getToken(): TokenModel {
        val tokenEntity = tokenDao.getToken()
        return tokenEntity?.toModel() ?: TokenModel(token = null, refreshToken = null)
    }

    override suspend fun insert(token: String, refreshToken: String) {
        tokenDao.insert(TokenEntity(token = token, refreshToken = refreshToken))
    }

    override suspend fun deleteToken() {
        tokenDao.deleteToken()
    }


}