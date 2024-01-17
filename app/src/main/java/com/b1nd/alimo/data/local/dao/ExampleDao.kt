package com.b1nd.alimo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.b1nd.alimo.data.local.base.BaseDao
import com.b1nd.alimo.data.local.entity.ExampleEntity
import com.b1nd.alimo.presentation.utiles.Env

@Dao
interface ExampleDao : BaseDao<ExampleEntity> {

    @Query("SELECT * FROM ${Env.ExampleTable} WHERE idx = 0")
    suspend fun getThat(): ExampleEntity

    @Query("DELETE FROM ${Env.ExampleTable}")
    suspend fun deleteAll()
}
