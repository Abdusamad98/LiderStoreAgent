package com.example.liderstoreagent.domain.repositories.impl

import com.example.liderstoreagent.data.models.getproduct.productFullData
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.data.source.remote.retrofit.ProductApi
import com.example.liderstoreagent.domain.repositories.repo.ProductFullRepository
import com.example.liderstoreagent.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductFullRepositoryImpl : ProductFullRepository {
    private val api = ApiClient.retrofit.create(ProductApi::class.java)
    override suspend fun productFullData(productId: String): Flow<Result<productFullData?>> = flow {

        try {
            val response = api.getProduct("product/product-detail/${productId}/")
            if (response.code() == 200) {
                emit(Result.success(response.body()))
                log(response.body().toString(), "QQQ")
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
            log("TTT", "exception = $e" + "Xatolik!")
        }
    }


}