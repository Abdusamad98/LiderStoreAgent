package com.example.liderstoreagent.ui.viewmodels.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.reportmodel.ReportData
import com.example.liderstoreagent.domain.usecases.ReportUseCase
import com.example.liderstoreagent.domain.usecases.impl.ReportUseCaseImpl
import com.example.liderstoreagent.utils.isConnected
import com.example.liderstoreagent.utils.log

class ReportViewModel:ViewModel() {

    private val useCase: ReportUseCase = ReportUseCaseImpl()
    val errorReportLiveData : LiveData<String> = useCase.errorReportLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<Any>()


    init {

    }


    fun sendPackage(reportData: ReportData) {
        if(isConnected()){
            progressLiveData.value = true
            val lvd = useCase.sendReport(reportData)
            successLiveData.addSource(lvd) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(lvd)
                log(it.toString(),"RESPONSE")
            }
        } else {
            connectionErrorLiveData.value =Unit
        }

    }




}