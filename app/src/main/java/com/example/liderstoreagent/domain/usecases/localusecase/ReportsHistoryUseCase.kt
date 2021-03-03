package com.example.liderstoreagent.domain.usecases.localusecase

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.reportmodel.ReportHistory

interface ReportsHistoryUseCase {
    val errorLiveData : LiveData<String>
    fun getReports(): LiveData<List<ReportHistory>>
}