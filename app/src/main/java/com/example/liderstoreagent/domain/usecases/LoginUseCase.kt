package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.loginmodel.LoginData
import com.example.liderstoreagent.data.models.loginmodel.LoginResponse

interface LoginUseCase {
    val errorLoginLiveData : LiveData<String>
    fun userLogin(loginData: LoginData) : LiveData<LoginResponse>
}