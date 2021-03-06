package com.rowaida.todo.utils

import android.content.Context

class ToDoSharedPreference(applicationContext: Context) {

    private val sharedPrefName = "com.rowaida.todo.PREFERENCE_FILE_KEY"

    //FIXME make shared pref name as a var to be more readable
    private val sharedPref = applicationContext.getSharedPreferences(
        sharedPrefName, Context.MODE_PRIVATE
    )

    //FIXME this method is not closed for modification "SOLID", as if any one needs to pass a default value he will add extra param to the method so please modify
    fun getValue(key: String, default: String? = null): String? {
        return sharedPref.getString(key, default)
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