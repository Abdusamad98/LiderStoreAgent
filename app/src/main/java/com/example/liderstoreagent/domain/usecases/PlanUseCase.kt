package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.planmodel.PlanData

interface PlanUseCase {
    val errorPlanLiveData : LiveData<Unit>
    fun getPlans() : LiveData<List<PlanData>>
}