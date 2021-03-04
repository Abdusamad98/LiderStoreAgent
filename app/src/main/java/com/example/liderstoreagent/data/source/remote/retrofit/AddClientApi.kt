package com.example.liderstoreagent.data.source.remote.retrofit

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AddClientApi {
    @Multipart
    @POST("client/agent-client-report/")
    suspend fun addClient(
            @Header("Accept") app_json: String,
            @Part("name") name: String,
            @Part("address") address: String,
            @Part("responsible_person") responsible_person: String,
            @Part("phone_number1") phone_number1: String,
            @Part("phone_number2") phone_number2: String,
            @Part("longitude") longitude: Double,
            @Part("latitude") latitude: Double,
            @Part image: MultipartBody.Part?,
            @Part("assumption_value") assumption_value: Int,
            @Part("sale_agent") agentId: Int,
    ): Response<Any>

}