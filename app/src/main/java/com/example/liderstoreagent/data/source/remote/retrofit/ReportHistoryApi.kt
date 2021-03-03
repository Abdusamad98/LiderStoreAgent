package com.example.liderstoreagent.data.source.remote.retrofit

import com.example.liderstoreagent.data.models.reportmodel.ReportHistory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ReportHistoryApi {
    @GET
    suspend fun getHistory(
        @Url url: String
        //  @Header("Authorization") token: String
    ): Response<List<ReportHistory>>

}