package com.nativeteams.common.domain.repository

import com.nativeteams.common.domain.model.Result
import com.nativeteams.common.domain.model.Stock

interface StocksRepository {
    suspend fun getStocks(refresh: Boolean): Result<List<Stock>>
}