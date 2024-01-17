package com.b1nd.alimo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.b1nd.alimo.data.local.dao.ExampleDao

@Database(
    entities = [
        ExampleDao::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AlimoDataBase: RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
}