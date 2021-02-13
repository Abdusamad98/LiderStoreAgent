package com.example.liderstoreagent.domain.repositories

import com.example.liderstoreagent.data.models.LoginData
import com.example.liderstoreagent.data.models.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun userLogin(loginData: LoginData) : Flow<Result<Unit>>
}