package com.example.liderstoreagent.domain.usecases.localusecaseimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.reportmodel.ReportHistory
import com.example.liderstoreagent.domain.repositories.localrepo.ReportHistoryRepository
import com.example.liderstoreagent.domain.repositories.localrepoimpl.ReportHistoryRepositoryImpl
import com.example.liderstoreagent.domain.usecases.localusecase.ReportsHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class ReportsHistoryUseCaseImpl : ReportsHistoryUseCase {
    override val errorLiveData = MutableLiveData<String>()
    private val repository: ReportHistoryRepository = ReportHistoryRepositoryImpl()

    override fun getReports(): LiveData<List<ReportHistory>> =
        liveData(Dispatchers.IO){
            repository.getReports().collect {
                if (it.isSuccess) emit(it.getOrNull()!!)
                else errorLiveData.postValue("Xatolik!")
            }
        }

}