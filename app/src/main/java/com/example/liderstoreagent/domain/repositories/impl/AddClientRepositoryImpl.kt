package com.example.liderstoreagent.domain.repositories.impl

import android.util.Log
import com.example.liderstoreagent.app.App
import com.example.liderstoreagent.data.models.clientmodel.AddClientData
import com.example.liderstoreagent.data.source.remote.retrofit.AddClientApi
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.domain.repositories.repo.AddClientRepository
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody


class AddClientRepositoryImpl : AddClientRepository {
    private val api = ApiClient.retrofit.create(AddClientApi::class.java)

    override suspend fun addClient(data: AddClientData): Flow<Result<Any?>> = flow {

        try {
            val image = Compressor.compress(App.instance, data.image)
            // pass it like this
            val requestFile: RequestBody =
                    image.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            // MultipartBody.Part is used to send also the actual file name
            val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("image", image.name, requestFile)
            val response = api.addClient(
                    "application/json",
                    data.marketName,
                    data.address,
                    data.responsiblePerson,
                    data.phoneNumber1,
                    data.phoneNumber2,
                    data.latitude,
                    data.longitude,
                    body,
                    data.assumptionValue,
                    data.agentId
            )

            when (response.code()) {
                201 -> {
                    emit(Result.success(true))
                }
                else -> {
                    emit(Result.success(false))
                }
            }


        } catch (e: Exception) {
            Log.d("SELL", "exception = $e")
        }
    }
}