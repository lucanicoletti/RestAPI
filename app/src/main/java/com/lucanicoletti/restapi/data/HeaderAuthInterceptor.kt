package com.lucanicoletti.restapi.data

import com.lucanicoletti.restapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request().newBuilder()
                .addHeader("X-Api-Key", BuildConfig.API_KEY)
                .build()
        )
    }
}