package com.example.datastore.keyvaluestore

import kotlinx.coroutines.flow.Flow

internal interface KeyValueStore {

    suspend fun write(block: (Writer) -> Unit)

    fun writeSync(block: (Writer) -> Unit)

    fun <T> readAsFlow(block: (Reader) -> T): Flow<T>

    fun <T> readSync(block: (Reader) -> T): T

    suspend fun <T> read(block: (Reader) -> T): T

    suspend fun clear()

    suspend fun remove(key : String)
}