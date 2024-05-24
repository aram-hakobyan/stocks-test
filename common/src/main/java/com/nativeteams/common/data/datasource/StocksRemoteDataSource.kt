package com.nativeteams.common.data.datasource

import com.nativeteams.common.data.network.StocksApi
import com.nativeteams.common.domain.model.Stock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class StocksRemoteDataSource(
    private val api: StocksApi,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getStocks(): List<Stock> = withContext(dispatcher) {
        api.getSummary().marketSummaryAndSparkResponse.result
    }
}