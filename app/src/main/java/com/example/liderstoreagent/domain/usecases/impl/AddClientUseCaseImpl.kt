package com.example.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.clientmodel.AddClientData
import com.example.liderstoreagent.domain.repositories.impl.AddClientRepositoryImpl
import com.example.liderstoreagent.domain.repositories.repo.AddClientRepository
import com.example.liderstoreagent.domain.usecases.AddClientUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class AddClientUseCaseImpl private constructor(): AddClientUseCase {

    private val repository: AddClientRepository = AddClientRepositoryImpl()
    override val errorAddClientLiveData = MutableLiveData<String>()
    override val locationLiveData= MutableLiveData<String>()

    override var locationSt: String
        get() = ""
        set(value) {
            locationLiveData.value = value
        }

    companion object {
        private var useCaseImpl : AddClientUseCaseImpl?= null
        fun getUseCase() : AddClientUseCaseImpl {
            if (useCaseImpl == null)
                useCaseImpl = AddClientUseCaseImpl()
            return useCaseImpl!!
        }
    }

    override fun addClient(addClientData: AddClientData): LiveData<Any> =
            liveData(Dispatchers.IO) {
                repository.addClient(addClientData).collect {
                    if (it.isSuccess) emit(it.getOrNull()!!)
                    else errorAddClientLiveData.postValue("Error")
                }
            }

}