package com.example.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.loginmodel.LoginData
import com.example.liderstoreagent.data.models.loginmodel.LoginResponse
import com.example.liderstoreagent.domain.usecases.LoginUseCase
import com.example.liderstoreagent.domain.usecases.impl.LoginUseCaseImpl
import com.example.liderstoreagent.utils.isConnected

class LoginViewModel : ViewModel() {
    private val useCase: LoginUseCase = LoginUseCaseImpl()
    val errorLoginLiveData : LiveData<String> = useCase.errorLoginLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<LoginResponse>()

    init {

    }

    fun login(userName : String, password:String) {
        if(isConnected()){
            progressLiveData.value = true
            val lvd = useCase.userLogin(LoginData(userName,password))
            successLiveData.addSource(lvd) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value =Unit
        }

    }
}