package com.example.liderstoreagent.domain.repositories.repo

import com.example.liderstoreagent.data.models.getproduct.productFullData
import kotlinx.coroutines.flow.Flow

interface ProductFullRepository {
    suspend fun productFullData(id:String): Flow<Result<productFullData?>>
}