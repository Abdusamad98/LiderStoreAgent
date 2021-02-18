package com.example.liderstoreagent.data.source.remote.retrofit

import com.example.liderstoreagent.data.models.productsmodel.ProductData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductsApiInterface {
    @GET
    suspend fun getProducts(
        @Url url: String
        //  @Header("Authorization") token: String
    ): Response<List<ProductData>>
}