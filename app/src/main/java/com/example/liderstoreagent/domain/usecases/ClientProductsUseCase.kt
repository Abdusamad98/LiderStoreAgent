package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts

interface ClientProductsUseCase {
    val errorClientProductsLiveData: LiveData<Unit>
    fun getClientProducts(clientId: String): LiveData<List<ClientProducts>>
}