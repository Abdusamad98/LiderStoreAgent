package com.example.liderstoreagent.domain.repositories.impl

import com.example.liderstoreagent.data.models.clientmodel.ClientsData
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.data.source.remote.retrofit.ClientsApiInterface
import com.example.liderstoreagent.domain.repositories.repo.ClientsRepository
import com.example.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClientsRepositoryImpl : ClientsRepository {
    private val api = ApiClient.retrofit.create(ClientsApiInterface::class.java)
    override suspend fun getClients(filter:String): Flow<Result<List<ClientsData>?>> = flow {
        try {
            val response = api.getClientList("report/client-debt-list/${TokenSaver.getAgentId()}/",filter)
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }

        } catch (e: Exception) {
          //emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }


}