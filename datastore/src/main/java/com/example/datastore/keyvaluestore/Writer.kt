package com.example.datastore.keyvaluestore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal class Writer {
    private val list: MutableList<Preferences.Pair<*>> = mutableListOf()

    fun set(key: String, value: String) {
        list += stringPreferencesKey(key) to value
    }

    fun set(key: String, value: Int) {
        list += intPreferencesKey(key) to value
    }

    fun set(key: String, value: Long) {
        list += longPreferencesKey(key) to value
    }

    fun set(key: String, value: Float) {
        list += floatPreferencesKey(key) to value
    }

    fun set(key: String, value: Double) {
        list += doublePreferencesKey(key) to value
    }

    fun set(key: String, value: Boolean) {
        list += booleanPreferencesKey(key) to value
    }

    fun getSetItems(): Array<Preferences.Pair<*>> {
        return list.toTypedArray()
    }
}