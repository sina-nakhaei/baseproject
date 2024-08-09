package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val userId: Int,
    val title: String,
    val body: String
)