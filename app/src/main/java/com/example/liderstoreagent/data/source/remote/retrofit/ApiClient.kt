package com.example.liderstoreagent.data.source.remote.retrofit
import com.example.liderstoreagent.app.App
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://ferzu-warehouse.herokuapp.com/api/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClientImage())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @JvmStatic
    private fun getHttpClientImage(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addLogging()
        return httpClient.build()
    }
}

fun OkHttpClient.Builder.addLogging(): OkHttpClient.Builder {
    val context = App.instance
    val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            com.example.liderstoreagent.utils.log(message)
        }

    })
    logging.level = HttpLoggingInterceptor.Level.HEADERS
    if (true) {
        addNetworkInterceptor(logging)
        addNetworkInterceptor(ChuckInterceptor(context))
    }
    return this
}