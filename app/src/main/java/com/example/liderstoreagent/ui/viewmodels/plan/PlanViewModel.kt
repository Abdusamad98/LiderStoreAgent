package com.example.liderstoreagent.ui.viewmodels.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.planmodel.PlanData
import com.example.liderstoreagent.domain.usecases.PlanUseCase
import com.example.liderstoreagent.domain.usecases.impl.PlanUseCaseImpl
import com.example.liderstoreagent.utils.isConnected

class PlanViewModel :ViewModel() {

    private val useCase: PlanUseCase = PlanUseCaseImpl()
    val errorPlanLiveData : LiveData<Unit> = useCase.errorPlanLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<List<PlanData>>()

    init {
        getPlans()
    }

    fun getPlans() {
        if(isConnected()){
            progressLiveData.value = true
            val liveData = useCase.getPlans()
            successLiveData.addSource(liveData) {
                progressLiveData.value = false
                successLiveData.value = it
                successLiveData.removeSource(liveData)
            }
        } else {
            connectionErrorLiveData.value =Unit
        }

    }
}