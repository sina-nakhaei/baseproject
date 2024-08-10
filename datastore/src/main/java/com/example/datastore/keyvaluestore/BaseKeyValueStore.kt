package com.example.datastore.keyvaluestore

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

internal class BaseCommonKeyValueStore(
    context: Context,
    fileName: String
) {
    private val keyValueStore = KeyValueStoreDefault(context, fileName)

    suspend fun write(block: (Writer) -> Unit) {
        keyValueStore.write {
            block(it)
        }
    }

    fun writeSync(block: (Writer) -> Unit) {
        keyValueStore.writeSync(block)
    }

    fun <T> readAsFlow(block: (Reader) -> T): Flow<T> {
        return keyValueStore.readAsFlow {
            block(it)
        }
    }

    fun <T> readSync(block: (Reader) -> T): T {
        return keyValueStore.readSync {
            block(it)
        }
    }

    suspend fun <T> read(block: (Reader) -> T): T {
        return keyValueStore.read(block)
    }

    suspend fun clear() {
        keyValueStore.clear()
    }

    fun clearSync(){
        runBlocking(Dispatchers.IO) {
            keyValueStore.clear()
        }
    }

    suspend fun remove(key: String) {
        return keyValueStore.remove(key)
    }
}