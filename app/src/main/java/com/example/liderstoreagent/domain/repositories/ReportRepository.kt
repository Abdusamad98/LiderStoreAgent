package com.example.liderstoreagent.domain.repositories

import com.example.liderstoreagent.data.models.reportmodel.ReportData
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    suspend fun reportSend(data:ReportData): Flow<Result<Any?>>
}