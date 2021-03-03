package com.example.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts
import com.example.liderstoreagent.domain.repositories.impl.ClientProductsRepositoryImpl
import com.example.liderstoreagent.domain.repositories.repo.ClientProductsRepository
import com.example.liderstoreagent.domain.usecases.ClientProductsUseCase
import kotlinx.coroutines.flow.collect

class ClientProductUseCaseImpl : ClientProductsUseCase {
    private val repository: ClientProductsRepository = ClientProductsRepositoryImpl()
    override val errorClientProductsLiveData = MutableLiveData<Unit>()
    override fun getClientProducts(clientId: String): LiveData<List<ClientProducts>> = liveData {
        repository.getClientProducts(clientId).collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorClientProductsLiveData.postValue(Unit)
            }
        }
    }
}