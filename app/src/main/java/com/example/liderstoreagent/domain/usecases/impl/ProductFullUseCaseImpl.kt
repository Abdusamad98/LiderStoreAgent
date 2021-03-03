package com.example.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.getproduct.productFullData
import com.example.liderstoreagent.domain.repositories.impl.ProductFullRepositoryImpl
import com.example.liderstoreagent.domain.repositories.repo.ProductFullRepository
import com.example.liderstoreagent.domain.usecases.ProductFullUseCase
import kotlinx.coroutines.flow.collect

class ProductFullUseCaseImpl : ProductFullUseCase {
    private val repository: ProductFullRepository = ProductFullRepositoryImpl()
    override val errorProductLiveData = MutableLiveData<Unit>()
    override fun getProduct(productId: String): LiveData<productFullData> = liveData {
        repository.productFullData(productId).collect {
            if (it.isSuccess) {
                emit(it.getOrNull()!!)
            } else {
                errorProductLiveData.postValue(Unit)
            }
        }
    }
}