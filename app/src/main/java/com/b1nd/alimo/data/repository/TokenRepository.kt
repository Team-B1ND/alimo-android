package com.b1nd.alimo.data.repository

import android.util.Log
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.local.dao.TokenDao
import com.b1nd.alimo.data.local.entity.TokenEntity
import com.b1nd.alimo.data.model.TokenModel
import com.b1nd.alimo.data.remote.service.TokenService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenDao: TokenDao
): TokenService {
    override suspend fun getToken(): Flow<Resource<TokenModel>> = flow{
        emit(Resource.Loading())
        try{
            val token = tokenDao.getToken()
            Log.d("TAG", "getToken: 시도 $token")
            emit(Resource.Success(TokenModel(token?.token, token?.refreshToken)))
//            if(token?.token != null){
//                Log.d("TAG", "getToken: 조건 $token")
//                emit(Resource.Success(TokenModel(token.token, token.refreshToken)))
//            }else{
//
//            }
        }catch (e: Exception){
            emit(Resource.Error(e))
        }
    }


    override suspend fun insert(token: String, refreshToken: String) {
        tokenDao.insert(TokenEntity(token = token, refreshToken = refreshToken))
    }

    override suspend fun deleteToken() {
        tokenDao.deleteToken()
    }


}