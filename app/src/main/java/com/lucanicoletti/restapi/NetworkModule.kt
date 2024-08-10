package com.lucanicoletti.restapi

import com.lucanicoletti.restapi.data.ApiService
import com.lucanicoletti.restapi.data.HeaderAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private lateinit var retrofitInstance: Retrofit

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun getRetrofit(): Retrofit {
        if (!::retrofitInstance.isInitialized) {
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addNetworkInterceptor(HeaderAuthInterceptor())
                .build()

            retrofitInstance = Retrofit.Builder()
                .baseUrl("https://randommer.io")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofitInstance
    }

    fun getApiService(): ApiService = getRetrofit().create(ApiService::class.java)
}