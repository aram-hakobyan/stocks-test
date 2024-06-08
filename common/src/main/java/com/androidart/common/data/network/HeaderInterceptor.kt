package com.androidart.common.data.network

import com.androidart.common.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val host: String,
    private val tokenRepository: TokenRepository
) : Interceptor {

    companion object {
        private const val HEADER_TOKEN = "X-RapidAPI-KEY"
        private const val HEADER_HOST = "X-RapidAPI-HOST"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val token = tokenRepository.fetchToken()

        builder.addHeader(HEADER_TOKEN, token)
        builder.addHeader(HEADER_HOST, host)

        return chain.proceed(builder.build())
    }
}