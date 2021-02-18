package com.example.liderstoreagent.domain.usecases

import androidx.lifecycle.LiveData
import com.example.liderstoreagent.data.models.discountsmodel.Discounts

interface DiscountsUseCase {
    val errorDiscountsLiveData : LiveData<Unit>
    fun getDiscounts() : LiveData<List<Discounts>>
}