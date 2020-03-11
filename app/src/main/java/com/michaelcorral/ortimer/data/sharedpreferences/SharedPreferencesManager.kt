package com.michaelcorral.ortimer.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private const val SHARED_PREFERENCES_NAME = "OrTimerSharedPreferences"
    private lateinit var sharedPreferences: SharedPreferences

    // Put shared pref keys here to prevent
    // using the wrong key name (ex. typo)
    enum class Key {
        SessionStateKey
    }

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    // Add additional methods for different data types
    fun put(key: Key, value: String) {
        getSharedPreferencesEditor()
            .putString(key.name, value)
            .apply()
    }

    fun put(key: Key, value: Int) {
        getSharedPreferencesEditor()
            .putInt(key.name, value)
            .apply()
    }

    fun put(key: Key, value: Long) {
        getSharedPreferencesEditor()
            .putLong(key.name, value)
            .apply()
    }

    fun put(key: Key, value: Boolean) {
        getSharedPreferencesEditor()
            .putBoolean(key.name, value)
            .apply()
    }

    fun getString(key: Key, defaultValue: String = ""): String {
        return sharedPreferences.getString(key.name, defaultValue) ?: ""
    }

    fun getInt(key: Key, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key.name, defaultValue)
    }

    fun getBoolean(key: Key, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key.name, defaultValue)
    }

    fun remove(key: Key) {
        getSharedPreferencesEditor()
            .remove(key.name)
            .apply()
    }

    private fun getSharedPreferencesEditor(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }
}