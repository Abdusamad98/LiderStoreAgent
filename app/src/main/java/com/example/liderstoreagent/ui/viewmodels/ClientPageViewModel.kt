package com.example.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.clientmodel.ClientsData
import com.example.liderstoreagent.domain.usecases.ClientsUseCase
import com.example.liderstoreagent.domain.usecases.impl.ClientsUseCaseImpl
import com.example.liderstoreagent.utils.isConnected

class ClientPageViewModel:ViewModel() {

    private val useCase: ClientsUseCase = ClientsUseCaseImpl()
    val errorCategoriesLiveData : LiveData<Unit> = useCase.errorClientsLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<List<ClientsData>>()


    init {
       getClients("all")
    }


    fun getClients(filter:String) {
        if(isConnected()){
            progressLiveData.value = true
            val liveData = useCase.getClients(filter)
            successLiveData.addSource(liveData) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorLiveData.value =Unit
        }

    }

    val closeLiveData = MutableLiveData<Unit>()
    fun closeSearch(){
        closeLiveData.value = Unit
    }
}