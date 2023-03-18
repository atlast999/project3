package com.example.webtoapp.base.network

import android.app.Application
import android.net.Uri
import androidx.annotation.IntRange
import com.example.webtoapp.base.BaseApplication
import com.example.webtoapp.base.credential.ICredentialManager
import com.example.webtoapp.base.serialize.GsonSerializer
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

object Network {

    fun createRetrofitInstance(
        endpoint: String,
        converterFactory: Converter.Factory = gsonConverter(),
        credentialManager: ICredentialManager,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(endpoint)
        .addConverterFactory(converterFactory)
        .client(okHttpClient(credentialManager))
        .build()

    private fun gsonConverter(): GsonConverterFactory = GsonConverterFactory.create(
        GsonSerializer.provideGson()
    )

    private fun okHttpClient(
        credentialManager: ICredentialManager,
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(defaultCache(BaseApplication.getInstance()))
        .addInterceptor(defaultLogger(BaseApplication.getInstance()))
        .addInterceptor(AuthenticationInterceptor(credentialManager))
        .readTimeout(Duration.ofSeconds(30))
        .connectTimeout(Duration.ofSeconds(30))
        .build()


    private fun defaultCache(
        application: Application,
        @IntRange(from = 1024 * 1024) size: Long = 10 * 1024 * 1024
    ) = Cache(application.cacheDir, size)

    private fun defaultLogger(
        application: BaseApplication,
        logLevel: HttpLoggingInterceptor.Level? = null
    ): Interceptor =
        HttpLoggingInterceptor(logger = {
            val length = it.length
            if (length > 51200 /*50 kB*/)
                Platform.get().log(
                    level = Platform.WARN,
                    message = "Log omitted cause tldr; $length in bytes"
                )
            else Platform.get().log(Uri.decode(it))
        }).apply {
            level =
                logLevel ?: if (application.isDebuggableMode()) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }
}