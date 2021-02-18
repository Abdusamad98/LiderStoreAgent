package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.productsmodel.ProductData

interface ProductsUseCase {
    val errorProductsLiveData: LiveData<Unit>
    fun getProducts(categoryId: String): LiveData<List<ProductData>>
}