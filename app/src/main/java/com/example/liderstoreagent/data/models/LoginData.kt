package com.example.liderstoreagent.data.models

data class LoginData(val username:String,val password:String)

data class LoginResponse(
    val token : String
)