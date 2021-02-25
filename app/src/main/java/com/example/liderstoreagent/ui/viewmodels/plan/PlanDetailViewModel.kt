package com.example.liderstoreagent.ui.viewmodels.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liderstoreagent.data.models.planmodel.PlanDetail
import com.example.liderstoreagent.domain.usecases.PlanDetailUseCase
import com.example.liderstoreagent.domain.usecases.impl.PlanDetailUseCaseImpl
import com.example.liderstoreagent.utils.isConnected

class PlanDetailViewModel : ViewModel() {

    private val useCase: PlanDetailUseCase = PlanDetailUseCaseImpl()
    val errorNotEmptyResponseLiveData : LiveData<String> = useCase.errorEmptyResponseLiveData
    val errorPlanDetailLiveData : LiveData<Unit> = useCase.errorPlanDetailLiveData
    val progressLiveData= MutableLiveData<Boolean>()
    val connectionErrorLiveData = MutableLiveData<Unit>()
    val successLiveData = MediatorLiveData<List<PlanDetail>>()

    init {

    }
    fun getPlansDetails(planId:String) {
        if(isConnected()){
            progressLiveData.value = true
            val liveData = useCase.getPlansDetail(planId)
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