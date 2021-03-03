package com.example.liderstoreagent.domain.repositories.impl

import com.example.liderstoreagent.data.models.planmodel.PlanData
import com.example.liderstoreagent.data.source.local.TokenSaver
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.data.source.remote.retrofit.PlanApiInterface
import com.example.liderstoreagent.domain.repositories.repo.PlanRepository
import com.example.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlanRepositoryImpl : PlanRepository {
    private val api = ApiClient.retrofit.create(PlanApiInterface::class.java)
    override suspend fun getPlans(): Flow<Result<List<PlanData>?>> = flow {
        try {
            val response = api.getPlans("plan/agent-plan-detail/${TokenSaver.getAgentId()}/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
            }
        } catch (e: Exception) {
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }


}