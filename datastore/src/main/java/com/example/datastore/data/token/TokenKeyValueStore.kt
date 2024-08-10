package com.example.datastore.data.token

interface TokenKeyValueStore {
    suspend fun storeToken(token: String)
    suspend fun getToken(): String
}