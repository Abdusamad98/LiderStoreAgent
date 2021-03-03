package com.example.liderstoreagent.domain.repositories.repo

import com.example.liderstoreagent.data.models.loginmodel.LoginData
import com.example.liderstoreagent.data.models.loginmodel.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun userLogin(loginData: LoginData) : Flow<Result<LoginResponse?>>
}