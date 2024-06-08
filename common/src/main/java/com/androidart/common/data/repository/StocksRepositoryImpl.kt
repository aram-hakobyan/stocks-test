package com.androidart.common.data.repository

import com.androidart.common.data.datasource.StocksLocalDataSource
import com.androidart.common.data.datasource.StocksRemoteDataSource
import com.androidart.common.domain.model.Result
import com.androidart.common.domain.model.Stock
import com.androidart.common.domain.repository.StocksRepository
import javax.inject.Inject

class StocksRepositoryImpl @Inject constructor(
    private val remoteDataSource: StocksRemoteDataSource,
    private val localDataSource: StocksLocalDataSource
) : StocksRepository {

    override suspend fun getStocks(
        refresh: Boolean
    ): Result<List<Stock>> {
        return try {
            val stocks = fetchFromDatasource(refresh)
            Result.Success(stocks)
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }

    private suspend fun fetchFromDatasource(refresh: Boolean): List<Stock> {
        val stocks = if (refresh || localDataSource.isEmpty()) {
            remoteDataSource.getStocks().also { networkResult ->
                localDataSource.cache(networkResult)
            }
        } else {
            localDataSource.fetch()
        }
        return stocks
    }
}