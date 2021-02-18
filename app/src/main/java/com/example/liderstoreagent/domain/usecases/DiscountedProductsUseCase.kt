package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import com.example.liderstoreagent.data.models.productsmodel.ProductData

interface DiscountedProductsUseCase {
    val errorDiscountedProductsLiveData: LiveData<Unit>
    fun getDiscountedProducts(discountId: String): LiveData<List<DiscountedProduct>>
}