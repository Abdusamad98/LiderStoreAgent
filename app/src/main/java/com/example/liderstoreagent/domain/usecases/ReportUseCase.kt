package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.reportmodel.ReportData

interface ReportUseCase {
    val errorReportLiveData : LiveData<String>
    fun sendReport(reportData: ReportData) : LiveData<Any>
}