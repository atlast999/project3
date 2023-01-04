package com.example.webtoapp.base.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

fun interface IRequestInterceptor {
    fun onInterceptor(request: Request)
}

class InterceptorDispatcher(
    private val interceptors: Collection<IRequestInterceptor>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().build().apply {
            interceptors.forEach {
                it.onInterceptor(this)
            }
        }.let {
            chain.proceed(it)
        }
    }
}