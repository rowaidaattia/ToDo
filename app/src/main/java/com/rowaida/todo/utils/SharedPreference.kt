package com.rowaida.todo.utils

import android.content.Context

class SharedPreference(applicationContext: Context) {

    private val sharedPref = applicationContext.getSharedPreferences(
        "com.rowaida.todo.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE
    )

    fun getValue(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun writeString(key: String, value: String) {
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun remove(key: String) {
        with (sharedPref.edit()) {
            remove(key)
            apply()
        }
    }

}