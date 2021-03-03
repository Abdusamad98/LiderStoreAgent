package com.example.liderstoreagent.domain.repositories.localrepoimpl

import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.data.models.reportmodel.ReportHistory
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.data.source.remote.retrofit.ReportHistoryApi
import com.example.liderstoreagent.domain.repositories.localrepo.ReportHistoryRepository
import com.example.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReportHistoryRepositoryImpl : ReportHistoryRepository {

    private val api = ApiClient.retrofit.create(ReportHistoryApi::class.java)

    override suspend fun getReports(): Flow<Result<List<ReportHistory>?>> = flow {
        try {
            val response = api.getHistory("report/sale-agent-report/${TokenSaver.getAgentId()}/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
            log("TTT", "exception = $e")
        }
    }


}