package com.example.datastore.di

import com.example.datastore.data.token.TokenKeyValueStore
import com.example.datastore.data.token.TokenKeyValueStoreDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface Module {

    @Binds
    @Singleton
    fun bindTokenKeyValueStore(
        kvs: TokenKeyValueStoreDefault
    ) : TokenKeyValueStore
}