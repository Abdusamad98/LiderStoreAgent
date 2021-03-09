package com.example.liderstoreagent.domain.repositories.impl

import android.util.Log
import com.example.liderstoreagent.app.App
import com.example.liderstoreagent.data.models.clientmodel.AddClientData
import com.example.liderstoreagent.data.source.remote.retrofit.AddClientApi
import com.example.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.example.liderstoreagent.domain.repositories.repo.AddClientRepository
import com.example.liderstoreagent.utils.log
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class AddClientRepositoryImpl : AddClientRepository {
    private val api = ApiClient.retrofit.create(AddClientApi::class.java)

    override suspend fun addClient(data: AddClientData): Flow<Result<Any?>> = flow {

        try {
            val image = Compressor.compress(App.instance, data.image)
            // pass it like this
            val requestFile: RequestBody =
                image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("image", image.name, requestFile)

            val marketName = data.marketName.toRequestBody("text/plain".toMediaTypeOrNull())
            val address = data.address.toRequestBody("text/plain".toMediaTypeOrNull())
            val responsiblePerson =
                data.responsiblePerson.toRequestBody("text/plain".toMediaTypeOrNull())
            val reqPhone1 = data.phoneNumber1.toRequestBody("text/plain".toMediaTypeOrNull())
            val reqPhone2 = data.phoneNumber2.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = api.addClient(
                "application/json",
                marketName,
                address,
                responsiblePerson,
                reqPhone1,
                reqPhone2,
                data.longitude,
                data.latitude,
                body,
                data.assumptionValue,
                data.agentId
            )
            when (response.code()) {
                201 -> {
                    emit(Result.success(true))
                }
                else -> {
                    log("${response.body()}", "EEERRORR")
                    emit(Result.success(false))
                }
            }


        } catch (e: Exception) {
            Log.d("SELL", "exception = $e")
        }
    }
}