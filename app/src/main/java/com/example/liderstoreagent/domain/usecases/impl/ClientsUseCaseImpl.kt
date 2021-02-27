package com.example.liderstoreagent.domain.usecases.impl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.clientmodel.ClientsData
import com.example.liderstoreagent.domain.repositories.ClientsRepository
import com.example.liderstoreagent.domain.repositories.impl.ClientsRepositoryImpl
import com.example.liderstoreagent.domain.usecases.ClientsUseCase
import kotlinx.coroutines.flow.collect

class ClientsUseCaseImpl : ClientsUseCase {
    private val repository: ClientsRepository = ClientsRepositoryImpl()
    override val errorClientsLiveData = MutableLiveData<Unit>()

    override fun getClients(filter: String): LiveData<List<ClientsData>> = liveData {
        repository.getClients(filter).collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorClientsLiveData.postValue(Unit)
            }
        }


    }
}