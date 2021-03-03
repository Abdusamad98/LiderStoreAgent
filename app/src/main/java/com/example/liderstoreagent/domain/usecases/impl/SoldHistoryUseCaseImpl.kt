package com.example.liderstoreagent.domain.usecases.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.liderstoreagent.data.models.historymodel.SoldProductHistory
import com.example.liderstoreagent.domain.repositories.repo.SoldHistoryRepository
import com.example.liderstoreagent.domain.repositories.impl.SoldHistoryRepositoryImpl
import com.example.liderstoreagent.domain.usecases.SoldHistoryUseCase
import kotlinx.coroutines.flow.collect

class SoldHistoryUseCaseImpl : SoldHistoryUseCase {
    private val repository: SoldHistoryRepository = SoldHistoryRepositoryImpl()
    override val errorHistoryLiveData = MutableLiveData<Unit>()
    override fun soldHistory(): LiveData<List<SoldProductHistory>>  = liveData {
        repository.soldHistory().collect {
            if (it.isSuccess) {
                it.getOrNull()?.let { it1 -> emit(it1) }
            } else {
                errorHistoryLiveData.postValue(Unit)
            }
        }
    }

}

