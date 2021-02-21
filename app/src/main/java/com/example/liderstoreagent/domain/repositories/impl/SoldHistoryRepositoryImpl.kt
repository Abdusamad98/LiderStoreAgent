package com.example.liderstoreagent.domain.repositories.impl

import com.example.liderstoreagent.data.models.clientmodel.ClientsData
import com.example.liderstoreagent.data.models.historymodel.SoldProductHistory
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.data.source.remote.retrofit.ClientsApiInterface
import com.example.liderstoreagent.data.source.remote.retrofit.SoldHistoryApi
import com.example.liderstoreagent.domain.repositories.ClientsRepository
import com.example.liderstoreagent.domain.repositories.SoldHistoryRepository
import com.example.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SoldHistoryRepositoryImpl : SoldHistoryRepository {
    private val api = ApiClient.retrofit.create(SoldHistoryApi::class.java)
    override suspend fun soldHistory(): Flow<Result<List<SoldProductHistory>?>> = flow {
        try {
            val response = api.soldHistory("order/agent-order-list/${TokenSaver.getAgentId()}/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }


}