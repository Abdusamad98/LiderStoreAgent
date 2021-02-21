package com.example.liderstoreagent.domain.repositories

import com.example.liderstoreagent.data.models.historymodel.SoldProductHistory
import kotlinx.coroutines.flow.Flow

interface SoldHistoryRepository {
    suspend fun soldHistory()
            : Flow<Result<List<SoldProductHistory>?>>
}