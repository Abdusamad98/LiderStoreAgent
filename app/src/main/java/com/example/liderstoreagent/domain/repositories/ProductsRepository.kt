package com.example.liderstoreagent.domain.repositories

import com.example.liderstoreagent.data.models.productsmodel.ProductData
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProducts(categoryId:String): Flow<Result<List<ProductData>?>>
}