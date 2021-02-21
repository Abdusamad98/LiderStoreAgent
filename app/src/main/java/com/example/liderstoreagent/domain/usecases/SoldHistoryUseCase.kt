package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.historymodel.SoldProductHistory

interface SoldHistoryUseCase {
    val errorHistoryLiveData : LiveData<Unit>
    fun soldHistory() : LiveData<List<SoldProductHistory>>
}