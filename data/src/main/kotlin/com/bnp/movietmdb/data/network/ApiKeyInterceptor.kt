package com.bnp.movietmdb.data.network

import okhttp3.Interceptor
import okhttp3.Response
import com.bnp.movietmdb.data.BuildConfig

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        val newRequest = originalRequest.newBuilder().url(urlWithApiKey).build()
        return chain.proceed(newRequest)
    }
}

