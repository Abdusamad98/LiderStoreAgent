package com.example.liderstoreagent.data.source.remote.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ReportApiInterface {


    @Multipart
    @POST("report/agent-photo-report/")
    suspend fun sendReport(
        @Header("Accept") app_json: String,
        @Part("comment") comment:RequestBody,
        @Part image:MultipartBody.Part?,
        @Part("sale_agent") agentId:Int
    ) : Response<Any>

}