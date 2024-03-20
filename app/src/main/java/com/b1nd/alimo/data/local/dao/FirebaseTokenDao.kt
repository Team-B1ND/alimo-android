package com.b1nd.alimo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.b1nd.alimo.data.local.base.BaseDao
import com.b1nd.alimo.data.local.entity.FirebaseTokenEntity
import com.b1nd.alimo.presentation.utiles.Env


@Dao
interface FirebaseTokenDao: BaseDao<FirebaseTokenEntity> {

    @Query("SELECT * FROM ${Env.FirebaseTokenTable} WHERE idx = 0")
    suspend fun getToken(): FirebaseTokenEntity?

    @Query("DELETE FROM ${Env.FirebaseTokenTable}")
    suspend fun deleteToken()
}