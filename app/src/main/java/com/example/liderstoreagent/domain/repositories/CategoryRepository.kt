package com.example.liderstoreagent.domain.repositories

import com.example.liderstoreagent.data.models.categorymodel.CategoryData
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories() : Flow<Result<List<CategoryData>?>>
}