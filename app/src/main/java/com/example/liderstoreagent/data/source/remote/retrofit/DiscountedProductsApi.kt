package com.example.liderstoreagent.data.source.remote.retrofit

import com.example.liderstoreagent.data.models.discountsmodel.DiscountedProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface DiscountedProductsApi {
    @GET
    suspend fun getDiscountedProducts(
        @Url url: String
        //  @Header("Authorization") token: String
    ): Response<List<DiscountedProduct>>
}