package com.nativeteams.common.data.datasource

import com.nativeteams.common.domain.model.Stock
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class StocksLocalDataSource(
    private var cachedStocks: List<Stock> = emptyList()
) {

    private val mutex = Mutex()

    suspend fun cache(list: List<Stock>) {
        mutex.withLock { cachedStocks = list }
    }

    suspend fun fetch(): List<Stock> {
        return mutex.withLock { cachedStocks }
    }

    suspend fun isEmpty(): Boolean = fetch().isEmpty()
}
