package com.androidart.common.domain.repository

import com.androidart.common.domain.model.Result
import com.androidart.common.domain.model.Stock

interface StocksRepository {
    suspend fun getStocks(refresh: Boolean): Result<List<Stock>>
}