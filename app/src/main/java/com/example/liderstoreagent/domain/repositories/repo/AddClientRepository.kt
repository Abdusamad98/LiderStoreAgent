package com.example.liderstoreagent.domain.repositories.repo

import com.example.liderstoreagent.data.models.clientmodel.AddClientData
import kotlinx.coroutines.flow.Flow

interface AddClientRepository {
    suspend fun addClient(data: AddClientData): Flow<Result<Any?>>
}

