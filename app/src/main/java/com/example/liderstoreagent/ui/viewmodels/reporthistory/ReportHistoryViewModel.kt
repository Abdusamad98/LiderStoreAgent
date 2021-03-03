package com.example.liderstoreagent.ui.viewmodels.reporthistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.reportmodel.ReportHistory
import com.example.liderstoreagent.domain.usecases.localusecase.ReportsHistoryUseCase
import com.example.liderstoreagent.domain.usecases.localusecaseimpl.ReportsHistoryUseCaseImpl
import com.example.liderstoreagent.utils.isConnected

class ReportHistoryViewModel : ViewModel() {
    private val useCase: ReportsHistoryUseCase = ReportsHistoryUseCaseImpl()
    val errorLiveData: LiveData<String> = useCase.errorLiveData
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val progressLiveData = MutableLiveData<Boolean>()
    val successLiveDataGet = MediatorLiveData<List<ReportHistory>>()

    init {
        getReports()
    }
    fun getReports() {
        if (isConnected()) {
            progressLiveData.value = true
            val lvd = useCase.getReports()
            successLiveDataGet.addSource(lvd) {
                progressLiveData.value = false
                successLiveDataGet.value = it
                successLiveDataGet.removeSource(lvd)
            }
        } else {
            connectionErrorLiveData.value = Unit
        }
    }


}