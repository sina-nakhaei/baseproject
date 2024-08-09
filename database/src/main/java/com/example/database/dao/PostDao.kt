package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Upsert
import com.example.database.model.PostEntity

@Dao
interface PostDao {
    @Upsert
    suspend fun insert(post: PostEntity)
}