package com.nativeteams.common.data.repository

import com.nativeteams.common.data.datasource.StocksLocalDataSource
import com.nativeteams.common.data.datasource.StocksRemoteDataSource
import com.nativeteams.common.domain.model.Result
import com.nativeteams.common.domain.model.Stock
import com.nativeteams.common.domain.repository.StocksRepository
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