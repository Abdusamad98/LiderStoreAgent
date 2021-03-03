package com.example.liderstoreagent.domain.repositories.repo

import com.example.liderstoreagent.data.models.planmodel.PlanData
import kotlinx.coroutines.flow.Flow

interface PlanRepository {
    suspend fun getPlans(): Flow<Result<List<PlanData>?>>
}