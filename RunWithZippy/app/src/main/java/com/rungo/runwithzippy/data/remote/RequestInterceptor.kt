package com.rungo.runwithzippy.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        return chain.proceed(request)
    }
}