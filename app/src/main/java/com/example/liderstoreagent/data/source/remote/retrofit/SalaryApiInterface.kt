package com.example.liderstoreagent.data.source.remote.retrofit

import com.example.liderstoreagent.data.models.productsmodel.ProductData
import com.example.liderstoreagent.data.models.salarymodel.SalaryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface SalaryApiInterface {
    @GET
    suspend fun getSalary(
        @Url url: String
        //  @Header("Authorization") token: String
    ): Response<SalaryData>
}