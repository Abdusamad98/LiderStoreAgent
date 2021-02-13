package com.example.liderstoreagent.domain.repositories.impl

import android.util.Log
import com.example.liderstoreagent.data.models.LoginData
import com.example.liderstoreagent.data.models.LoginResponse
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.data.source.remote.retrofit.LoginApiInterface
import com.example.liderstoreagent.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl : LoginRepository {
    private val api = ApiClient.retrofit.create(LoginApiInterface::class.java)

    override suspend fun userLogin(loginData: LoginData): Flow<Result<Unit>>
    = flow {
        try {
            val response = api.userLogin(loginData)
            if(response.code() == 200) {
                TokenSaver.token = response.body()!!.token
                emit(Result.success(Unit))
            }
        } catch (e : Exception) {
            Log.d("TTT","exception = $e")
        }
    }

}