package com.example.webtoapp.base.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    fun createRetrofitInstance(
        endpoint: String,
        converterFactory: Converter.Factory = gsonConverter(),
    ) = Retrofit.Builder()
        .baseUrl(endpoint)
        .addConverterFactory(converterFactory)
        .client()
        .build()

    private fun gsonConverter(): GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    )

    private fun okHttpClient(
        interceptor:
    ): OkHttpClient = OkHttpClient.Builder()
        .build()

}