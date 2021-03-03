package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.getproduct.productFullData

interface ProductFullUseCase {
    val errorProductLiveData: LiveData<Unit>
    fun getProduct(productId: String): LiveData<productFullData>
}