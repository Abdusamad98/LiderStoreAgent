package com.example.liderstoreagent.domain.usecases.impl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.reportmodel.ReportData
import com.example.liderstoreagent.domain.repositories.repo.ReportRepository
import com.example.liderstoreagent.domain.repositories.impl.ReportRepositoryImpl
import com.example.liderstoreagent.domain.usecases.ReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect


class ReportUseCaseImpl : ReportUseCase {

    private val repository: ReportRepository = ReportRepositoryImpl()
    override val errorReportLiveData = MutableLiveData<String>()

    override fun sendReport(reportData: ReportData): LiveData<Any> =
        liveData(Dispatchers.IO) {
            repository.reportSend(reportData).collect {
                if (it.isSuccess) emit(it.getOrNull()!!)
                else errorReportLiveData.postValue("Error")
            }
        }

}