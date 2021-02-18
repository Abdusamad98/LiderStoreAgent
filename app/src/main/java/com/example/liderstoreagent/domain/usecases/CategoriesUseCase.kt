package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.categorymodel.CategoryData

interface CategoriesUseCase {
    val errorCategoriesLiveData : LiveData<Unit>
    fun getCategories() : LiveData<List<CategoryData>>
}