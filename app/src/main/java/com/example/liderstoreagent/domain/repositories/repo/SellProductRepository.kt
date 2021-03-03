package com.example.liderstoreagent.domain.repositories.repo

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.sellmodel.SellProductData
import com.example.liderstoreagent.data.models.sellmodel.SellProductResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface SellProductRepository {
    suspend fun sellProduct(productData: SellProductData)
    : Flow<Result<Pair<Int,SellProductResponse?>>>
}