package com.example.liderstoreagent.data.source.remote.retrofit

import com.example.liderstoreagent.data.models.planmodel.PlanData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PlanApiInterface {
    @GET
    suspend fun getPlans(
        @Url url: String
        //  @Header("Authorization") token: String
    ): Response<List<PlanData>>

}