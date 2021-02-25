package com.example.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.salarymodel.SalaryData
import com.example.liderstoreagent.data.models.sellmodel.SellProductData
import com.example.liderstoreagent.data.models.sellmodel.SellProductResponse
import com.example.liderstoreagent.domain.repositories.SalaryRepository
import com.example.liderstoreagent.domain.repositories.SellProductRepository
import com.example.liderstoreagent.domain.repositories.impl.SalaryRepositoryImpl
import com.example.liderstoreagent.domain.repositories.impl.SellProductRepositoryImpl
import com.example.liderstoreagent.domain.usecases.SalaryUseCase
import com.example.liderstoreagent.domain.usecases.SellProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class SalaryUseCaseImpl : SalaryUseCase {
    private val repository: SalaryRepository = SalaryRepositoryImpl()
    override val errorNotResponseLiveData = MutableLiveData<String>()
    override val errorResponseLiveData= MutableLiveData<String>()

    override fun getSalary(): LiveData<SalaryData> =
        liveData(Dispatchers.IO) {
            repository.getSalary().collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { pair ->
                        if (pair.first == 200) pair.second?.let { it1 -> emit(it1) }
                        if (pair.first == 500) errorResponseLiveData.postValue("Oylik ma'lumotlar topilmadi!")
                    }
                } else {
                    errorNotResponseLiveData.postValue("Error")
                }
            }
        }

}