package com.b1nd.alimo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.b1nd.alimo.data.local.dao.ExampleDao
import com.b1nd.alimo.data.local.entity.ExampleEntity

@Database(
    entities = [
        ExampleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AlimoDataBase: RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
}