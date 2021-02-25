package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.salarymodel.SalaryData

interface SalaryUseCase {
    val errorNotResponseLiveData : LiveData<String>
    val errorResponseLiveData : LiveData<String>
    fun getSalary() : LiveData<SalaryData>

}