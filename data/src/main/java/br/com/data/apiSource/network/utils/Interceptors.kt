package br.com.data.apiSource.network.utils

import br.com.data.localSource.entity.Auth
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

val DEFAULT_HEADER_INTERCEPTOR = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request().newBuilder()
                .addHeader(ACCEPT_HEADER_PARAMETER, ACCEPT_HEADER_VALUE)
                .addHeader(AUTHORIZATION, AUTH.value.toString())
                .build()
        )

    private val ACCEPT_HEADER_PARAMETER = "accept"
    private val ACCEPT_HEADER_VALUE = "application/vnd.github.v3+json"
    private val AUTHORIZATION = "Authorization"
}

val LOGGER_INTERCEPTOR =  HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

object AUTH {
    var value : Auth = Auth("", "")
}