package com.androidart.common.data.network

import com.androidart.common.domain.model.MarketSummary
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface StocksApi {

    @GET(URL_GET_SUMMARY)
    suspend fun getSummary(
        @Query("region") region: String = "US"
    ): MarketSummary

    companion object {
        private const val URL_GET_SUMMARY = "market/v2/get-summary/"
    }
}