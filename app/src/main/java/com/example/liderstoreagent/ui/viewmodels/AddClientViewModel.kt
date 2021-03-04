package com.example.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.clientmodel.AddClientData
import com.example.liderstoreagent.domain.usecases.AddClientUseCase
import com.example.liderstoreagent.domain.usecases.impl.AddClientUseCaseImpl
import com.example.liderstoreagent.utils.isConnected

class AddClientViewModel : ViewModel() {

    private val useCase: AddClientUseCase = AddClientUseCaseImpl.getUseCase()
    val errorAddClientLiveData: LiveData<String> = useCase.errorAddClientLiveData
    val progressLiveData = MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<Any>()
    val locationLiveData = useCase.locationLiveData

    fun addClient(addClientData: AddClientData) {
        if (isConnected()) {
            progressLiveData.value = true
            val lvd = useCase.addClient(addClientData)
            successLiveData.addSource(lvd) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value = Unit
        }

    }


}