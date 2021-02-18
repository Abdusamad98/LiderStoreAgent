package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.clientmodel.ClientsData

interface ClientsUseCase {
    val errorClientsLiveData : LiveData<Unit>
    fun getClients(filter:String) : LiveData<List<ClientsData>>
}