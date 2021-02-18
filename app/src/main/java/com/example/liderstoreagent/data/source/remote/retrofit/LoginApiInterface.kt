package com.example.liderstoreagent.data.source.remote.retrofit


import com.example.liderstoreagent.data.models.loginmodel.LoginData
import com.example.liderstoreagent.data.models.loginmodel.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiInterface {

    @POST("user/login/")
   suspend fun userLogin(
        @Body loginData: LoginData
    ) : Response<LoginResponse>

}