package com.example.datastore.data.token

import android.content.Context
import com.example.datastore.keyvaluestore.BaseCommonKeyValueStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class TokenKeyValueStoreDefault @Inject constructor(
    @ApplicationContext context: Context
): TokenKeyValueStore {
    private val keyValueStore = BaseCommonKeyValueStore(context, "token_kvs")

    override suspend fun storeToken(token: String) {
        keyValueStore.write { it.set("token", token) }
    }

    override suspend fun getToken(): String {
        return keyValueStore.read { it.getString("token","") }
    }
}