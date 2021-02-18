package com.example.liderstoreagent.domain.repositories

import com.example.liderstoreagent.data.models.discountsmodel.Discounts
import kotlinx.coroutines.flow.Flow

interface DiscountsRepository {
    suspend fun getDiscounts() : Flow<Result<List<Discounts>?>>
}