package com.example.datastore.keyvaluestore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


internal class Reader(
    private val pref: Preferences
) {

    fun getString(key: String): String? {
        return pref[stringPreferencesKey(key)]
    }

    fun getString(key: String, defaultValue: String): String {
        return pref[stringPreferencesKey(key)] ?: defaultValue
    }

    fun getInt(key: String): Int? {
        return pref[intPreferencesKey(key)]
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return pref[intPreferencesKey(key)] ?: defaultValue
    }

    fun getLong(key: String): Long? {
        return pref[longPreferencesKey(key)]
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return pref[longPreferencesKey(key)] ?: defaultValue
    }

    fun getDouble(key: String): Double? {
        return pref[doublePreferencesKey(key)]
    }

    fun getDouble(key: String, defaultValue: Double): Double {
        return pref[doublePreferencesKey(key)] ?: defaultValue
    }

    fun getFloat(key: String): Float? {
        return pref[floatPreferencesKey(key)]
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return pref[floatPreferencesKey(key)] ?: defaultValue
    }

    fun getBoolean(key: String): Boolean? {
        return pref[booleanPreferencesKey(key)]
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return pref[booleanPreferencesKey(key)] ?: defaultValue
    }
}