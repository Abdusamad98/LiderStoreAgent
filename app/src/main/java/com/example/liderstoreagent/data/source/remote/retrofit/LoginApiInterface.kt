package com.example.liderstoreagent.data.source.remote.retrofit

import com.example.liderstoreagent.data.models.LoginData
import com.example.liderstoreagent.data.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiInterface {

    @POST("user/login/")
    fun userLogin(
        @Body loginData:LoginData
    ) : Response<LoginResponse>
}