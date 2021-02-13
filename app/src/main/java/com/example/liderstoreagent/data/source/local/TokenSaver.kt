package com.example.liderstoreagent.data.source.local

import android.content.Context
import android.content.SharedPreferences

class TokenSaver(context: Context) {

    companion object {

        private lateinit var preferences: SharedPreferences
        fun init(context: Context) {
            preferences = context.getSharedPreferences("SAVER", Context.MODE_PRIVATE)
        }

        fun getPreferences() = preferences


        var token : String set(value) {
            preferences.edit().putString("token", value).apply()
        } get() {
            return preferences.getString("token", "")!!
        }

        //Save Password
        fun setPassword(password: String) {
            preferences.edit().putString("password", password).apply()
        }
        fun getPassword() = preferences.getString("password", "")


        //Save Login
        fun setLogin(login: String) {
            preferences.edit().putString("login", login).apply()
        }
        fun getLogin() = preferences.getString("login", "")


        fun setDefault(boolean: Boolean) {
            preferences.edit().putBoolean("bool", boolean).apply()
        }


        fun getDefault(): Boolean {
            return preferences.getBoolean("bool", true)
        }

        fun setType(page: String) {
            preferences.edit().putString("type", page).apply()
        }

        fun getType(): String? {
            return preferences.getString("type", "")
        }
    }
}