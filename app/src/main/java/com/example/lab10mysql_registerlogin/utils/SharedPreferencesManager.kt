package com.example.lab10mysql_registerlogin.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("recycan_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
    }

    // Save Login
    fun saveLoginStatus(
        isLoggedIn: Boolean,
        userId: Int,
        userName: String
    ) {
        preferences.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            putInt(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, userName)
            apply()
        }
    }


    // Check Login
    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Get User ID
    fun getUserId(): Int {
        return preferences.getInt(KEY_USER_ID, 0)
    }

    // Get User Name
    fun getUserName(): String {
        return preferences.getString(KEY_USER_NAME, "") ?: ""
    }

    // Logout
    fun logout() {
        preferences.edit().clear().apply()
    }
}