package com.example.liderstoreagent.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.salarymodel.SalaryData
import com.example.liderstoreagent.domain.usecases.SalaryUseCase
import com.example.liderstoreagent.domain.usecases.impl.SalaryUseCaseImpl
import com.example.liderstoreagent.utils.isConnected

class SalaryViewModel : ViewModel() {

    private val useCase: SalaryUseCase = SalaryUseCaseImpl()
    val errorNotResponseLiveData: LiveData<String> = useCase.errorNotResponseLiveData
    val errorResponseLiveData = MediatorLiveData<String>()
    val progressSellLiveData = MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<SalaryData>()


    init {
        val f = useCase.errorResponseLiveData
        errorResponseLiveData.addSource(f) {
            progressSellLiveData.value = false
            errorResponseLiveData.value = it
        }

        getSalary()
    }

    fun getSalary() {
        if (isConnected()) {
            progressSellLiveData.value = true
            val lvd = useCase.getSalary()
            successLiveData.addSource(lvd) {
                progressSellLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value = Unit
        }

    }
}