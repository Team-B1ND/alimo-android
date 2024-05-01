package com.b1nd.alimo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.b1nd.alimo.data.local.dao.ExampleDao
import com.b1nd.alimo.data.local.dao.FirebaseTokenDao
import com.b1nd.alimo.data.local.dao.TokenDao
import com.b1nd.alimo.data.local.entity.ExampleEntity
import com.b1nd.alimo.data.local.entity.FirebaseTokenEntity
import com.b1nd.alimo.data.local.entity.TokenEntity

@Database(
    entities = [
        ExampleEntity::class,
        FirebaseTokenEntity::class,
        TokenEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AlimoDataBase: RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
    abstract fun firebaseTokenDao(): FirebaseTokenDao
    abstract fun tokenDao(): TokenDao
}