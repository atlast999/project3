package com.example.webtoapp.base.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

fun interface IRequestInterceptor {
    fun onInterceptor(request: Request, builder: Request.Builder)
}

class InterceptorDispatcher(
    private val interceptors: Collection<IRequestInterceptor>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().apply {
            interceptors.forEach {
                it.onInterceptor()
            }
        }
    }
}