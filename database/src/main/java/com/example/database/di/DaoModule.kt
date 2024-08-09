package com.example.database.di

import com.example.database.Database
import com.example.database.dao.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesPostDao(
        database: Database,
    ): PostDao = database.postDao()
}