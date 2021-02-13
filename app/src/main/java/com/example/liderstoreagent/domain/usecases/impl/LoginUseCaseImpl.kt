package com.example.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.LoginData
import com.example.liderstoreagent.domain.repositories.LoginRepository
import com.example.liderstoreagent.domain.repositories.impl.LoginRepositoryImpl
import com.example.liderstoreagent.domain.usecases.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class LoginUseCaseImpl : LoginUseCase {
    private val repository : LoginRepository = LoginRepositoryImpl()

    override val errorLoginLiveData= MutableLiveData<String>()

    override fun userLogin(loginData: LoginData): LiveData<Unit> =
        liveData(Dispatchers.IO) {
        repository.userLogin(loginData).collect {
            if(it.isSuccess) emit(Unit)
            else errorLoginLiveData.postValue("Error")
        }
    }

}