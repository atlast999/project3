package com.example.webtoapp.base.network

import com.example.webtoapp.base.credential.ICredentialManager
import com.example.webtoapp.base.util.notNullOrEmptyLet
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val credentialManager: ICredentialManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            credentialManager.getToken().notNullOrEmptyLet {
                addHeader("Authorization", "Bearer $it")
            }
        }.build()
        return chain.proceed(request)
    }
}