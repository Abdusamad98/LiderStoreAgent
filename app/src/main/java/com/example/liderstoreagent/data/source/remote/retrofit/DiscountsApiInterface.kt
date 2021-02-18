package com.example.liderstoreagent.data.source.remote.retrofit

import com.example.liderstoreagent.data.models.discountsmodel.Discounts
import retrofit2.Response
import retrofit2.http.GET

interface DiscountsApiInterface {
    @GET("expense_discount/agent-discount-list/")
    suspend fun getDiscounts(): Response<List<Discounts>>
}