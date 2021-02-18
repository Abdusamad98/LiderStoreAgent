package com.example.liderstoreagent.domain.repositories

import com.example.liderstoreagent.data.models.clientmodel.ClientsData
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {
    suspend fun getClients(filter:String): Flow<Result<List<ClientsData>?>>
}

