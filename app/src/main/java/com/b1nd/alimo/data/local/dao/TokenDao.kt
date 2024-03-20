package com.b1nd.alimo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.b1nd.alimo.data.local.base.BaseDao
import com.b1nd.alimo.data.local.entity.TokenEntity
import com.b1nd.alimo.presentation.utiles.Env

@Dao
interface TokenDao: BaseDao<TokenEntity> {

    @Query("SELECT * FROM ${Env.TokenTable} WHERE idx = 0")
    suspend fun getToken(): TokenEntity?

    @Query("DELETE FROM ${Env.TokenTable}")
    suspend fun deleteToken()
}