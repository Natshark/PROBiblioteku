package com.example.probiblioteku

import android.content.Context

class CacheManager (context: Context) {
    public val sharedPreferences = context.getSharedPreferences("my_cache", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun clearCache() {
        sharedPreferences.edit().clear().apply()
    }
}