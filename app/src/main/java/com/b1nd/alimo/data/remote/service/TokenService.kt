package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.local.dao.TokenDao
import com.b1nd.alimo.data.local.entity.TokenEntity
import com.b1nd.alimo.data.model.TokenModel
import com.b1nd.alimo.data.remote.mapper.toModel
import javax.inject.Inject

//interface TokenService {
//    suspend fun getToken(): Flow<Resource<TokenModel>>
//
//    suspend fun insert(token: String,refreshToken: String)
//
//    suspend fun deleteToken()
//}

class TokenService @Inject constructor(
    private val tokenDao: TokenDao
){
    suspend fun getToken(): Resource<TokenModel> {
        return try {
            val token = tokenDao.getToken()
            Resource.Success(token.toModel())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
    suspend fun insert(token: String, refreshToken: String) {
        tokenDao.insert(TokenEntity(token = token, refreshToken = refreshToken))
    }

    suspend fun deleteToken() {
        tokenDao.deleteToken()
    }


}