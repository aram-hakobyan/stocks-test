package com.androidart.common.data.datasource

import com.androidart.common.data.network.StocksApi
import com.androidart.common.domain.model.Stock
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