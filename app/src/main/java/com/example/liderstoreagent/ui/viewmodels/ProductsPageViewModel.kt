package com.example.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.categorymodel.CategoryData
import com.example.liderstoreagent.data.models.productsmodel.ProductData
import com.example.liderstoreagent.domain.usecases.CategoriesUseCase
import com.example.liderstoreagent.domain.usecases.ProductsUseCase
import com.example.liderstoreagent.domain.usecases.impl.CategoriesUseCaseImpl
import com.example.liderstoreagent.domain.usecases.impl.ProductsUseCaseImpl
import com.example.liderstoreagent.utils.isConnected

class ProductsPageViewModel : ViewModel() {

    private val categoriesUseCase: CategoriesUseCase = CategoriesUseCaseImpl()
    val errorCategoriesLiveData: LiveData<Unit> = categoriesUseCase.errorCategoriesLiveData
    val progressCategoriesLiveData = MutableLiveData<Boolean>()
    val connectionErrorCategoriesLiveData = MutableLiveData<Unit>()
    val successCategoriesLiveData = MediatorLiveData<List<CategoryData>>()

    private val productsUseCase: ProductsUseCase = ProductsUseCaseImpl()
    val errorProductsLiveData: LiveData<Unit> = productsUseCase.errorProductsLiveData
    val progressProductsLiveData = MutableLiveData<Boolean>()
    val connectionErrorProductsLiveData = MutableLiveData<Unit>()
    val successProductsLiveData = MediatorLiveData<List<ProductData>>()

    init {
        getCategories()
        getProducts(1)
    }

    fun getProducts(categoryId: Int) {
        if (isConnected()) {
            progressProductsLiveData.value = true
            val liveData = productsUseCase.getProducts(categoryId.toString())
            successProductsLiveData.addSource(liveData) {
                progressProductsLiveData.value = false
                successProductsLiveData.value = it
                successProductsLiveData.removeSource(liveData)
            }
        } else {
            // progressLiveData.value = false
            connectionErrorProductsLiveData.value = Unit
        }
    }

    fun getCategories() {
        if (isConnected()) {
            progressCategoriesLiveData.value = true
            val liveData = categoriesUseCase.getCategories()
            successCategoriesLiveData.addSource(liveData) {
                progressCategoriesLiveData.value = false
                successCategoriesLiveData.value = it
                successCategoriesLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorCategoriesLiveData.value = Unit
        }

    }

    val closeLiveData = MutableLiveData<Unit>()
    fun closeSearch(){
        closeLiveData.value = Unit
    }
}