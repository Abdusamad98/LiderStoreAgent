package com.example.liderstoreagent.domain.repositories.repo

import com.example.liderstoreagent.data.models.salarymodel.SalaryData
import com.example.liderstoreagent.data.models.sellmodel.SellProductResponse
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {
    suspend fun getSalary(): Flow<Result<Pair<Int, SalaryData?>>>
}