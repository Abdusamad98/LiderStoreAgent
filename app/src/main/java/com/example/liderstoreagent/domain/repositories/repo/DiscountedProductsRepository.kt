package com.example.liderstoreagent.domain.repositories.repo

import com.example.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import kotlinx.coroutines.flow.Flow

interface DiscountedProductsRepository {
    suspend fun getDiscountedProducts(discountId:String): Flow<Result<List<DiscountedProduct>?>>
}