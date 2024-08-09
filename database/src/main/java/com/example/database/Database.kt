package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.PostDao
import com.example.database.model.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1,
//    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true,
)
internal abstract class Database : RoomDatabase() {
    abstract fun postDao(): PostDao
}