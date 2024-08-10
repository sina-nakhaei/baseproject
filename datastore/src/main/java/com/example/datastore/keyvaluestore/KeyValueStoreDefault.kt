package com.example.datastore.keyvaluestore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

internal class KeyValueStoreDefault(
    private val context: Context,
    fileName: String
) : KeyValueStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = fileName
    )

    override suspend fun write(block: (Writer) -> Unit) {
        val writer = Writer()
        block(writer)
        context.dataStore.edit {
            val setItems = writer.getSetItems()
            it.putAll(*setItems)
        }
    }

    override fun writeSync(block: (Writer) -> Unit) {
        runBlocking(Dispatchers.IO) {
            write (block)
        }
    }

    override fun <T> readAsFlow(block: (Reader) -> T): Flow<T> {
        return context.dataStore.data.map {
            val reader = Reader(it)
            block(reader)
        }
    }

    override fun <T> readSync(block: (Reader) -> T): T {
        return runBlocking (Dispatchers.IO){
            readAsFlow(block).first()
        }
    }

    override suspend fun <T> read(block: (Reader) -> T): T {
        return context.dataStore.data.first().let {
            val reader = Reader(it)
            block(reader)
        }
    }

    override suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }

    override suspend fun remove(key: String) {
        context.dataStore.edit {
            it -= stringPreferencesKey(key)
        }
    }
}