package com.example.liderstoreagent.data.source.remote.retrofit

import com.example.liderstoreagent.data.models.historymodel.SoldProductHistory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface SoldHistoryApi {
    @GET
    suspend fun soldHistory(
        @Url url: String,
        //  @Header("Authorization") token: String
    ): Response<List<SoldProductHistory>>
}