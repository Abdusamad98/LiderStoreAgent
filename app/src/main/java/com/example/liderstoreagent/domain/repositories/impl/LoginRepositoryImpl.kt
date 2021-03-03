package com.example.liderstoreagent.domain.repositories.impl

import android.util.Log
import com.example.liderstoreagent.data.models.loginmodel.LoginData
import com.example.liderstoreagent.data.models.loginmodel.LoginResponse
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.data.source.remote.retrofit.LoginApiInterface
import com.example.liderstoreagent.domain.repositories.repo.LoginRepository
import com.example.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl : LoginRepository {
    private val api = ApiClient.retrofit.create(LoginApiInterface::class.java)

    override suspend fun userLogin(loginData: LoginData): Flow<Result<LoginResponse?>>
    = flow {
        try {
            val response = api.userLogin(loginData)
            if(response.code() == 200) {
                emit(Result.success(response.body()))
                TokenSaver.setPassword(loginData.password)
                TokenSaver.setLogin(loginData.username)
                TokenSaver.token = response.body()!!.token!!
                TokenSaver.setAgentId(response.body()!!.user!!.id)
                TokenSaver.setFirstName(response.body()!!.user!!.first_name)
                TokenSaver.setLastName(response.body()!!.user!!.last_name)
                log(response.body()!!.user.toString(),"Login qildi!")
            }

        } catch (e : Exception) {
            Log.d("TTT","exception = $e" + "Salom")
        }
    }

}