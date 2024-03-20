package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.local.dao.TokenDao
import com.b1nd.alimo.data.local.entity.TokenEntity
import com.b1nd.alimo.data.model.TokenModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.safeFlow
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenDao: TokenDao
){
    suspend fun getToken() =
        safeFlow<TokenModel> {
            val token = Resource.Success(tokenDao.getToken().toModel())

            emit(token)
        }


    suspend fun insert(token: String, refreshToken: String) {
        val token = TokenEntity(token = token, refreshToken = refreshToken)
        tokenDao.insert(token)
    }

    suspend fun deleteToken() {
        tokenDao.deleteToken()
    }


}