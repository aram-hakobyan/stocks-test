package com.nativeteams.common.data.network

import com.nativeteams.common.domain.model.MarketData
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface StocksApi {

    @GET(URL_GET_SUMMARY)
    suspend fun getSummary(
        @Query("region") region: String = "US"
    ): MarketData

    companion object {
        private const val URL_GET_SUMMARY = "market/v2/get-summary/"
    }
}