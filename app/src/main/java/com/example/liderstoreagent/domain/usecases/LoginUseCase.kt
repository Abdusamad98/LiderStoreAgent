package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.LoginData

interface LoginUseCase {

    val errorLoginLiveData : LiveData<String>
    fun userLogin(loginData: LoginData) : LiveData<Unit>
}