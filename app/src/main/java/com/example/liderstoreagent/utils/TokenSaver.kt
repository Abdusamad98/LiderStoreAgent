package com.example.liderstoreagent.utils

import android.content.Context
import android.content.SharedPreferences

class TokenSaver(context: Context) {

    companion object {

        private lateinit var preferences: SharedPreferences
        fun init(context: Context) {
            preferences = context.getSharedPreferences("SAVER", Context.MODE_PRIVATE)
        }

        fun getPreferences() = preferences

        fun setToken(token: String){
            preferences.edit().putString("token", token).apply()
        }

        fun getToken() = preferences.getString("token", "")


        fun setDefault(boolean: Boolean){
            preferences.edit().putBoolean("bool",boolean).apply()
        }

        fun getDefault():Boolean{
            return preferences.getBoolean("bool", true)
        }

        fun setType(page: String){
            preferences.edit().putString("type",page).apply()
        }
        fun getType(): String? {
            return preferences.getString("type", "")
        }
    }
}