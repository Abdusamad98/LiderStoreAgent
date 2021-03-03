package com.example.liderstoreagent.domain.repositories.localrepo

import com.example.liderstoreagent.data.models.reportmodel.ReportHistory
import kotlinx.coroutines.flow.Flow

interface ReportHistoryRepository {
    suspend fun getReports(): Flow<Result<List<ReportHistory>?>>
}