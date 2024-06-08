package com.androidart.common.domain.useCase

import com.androidart.common.domain.model.Result
import com.androidart.common.domain.model.Stock
import com.androidart.common.domain.repository.StocksRepository
import javax.inject.Inject

class GetStocksUseCase @Inject constructor(
    private val stockRepository: StocksRepository
) {
    suspend operator fun invoke(
        refresh: Boolean = false
    ): Result<List<Stock>> {
        return stockRepository.getStocks(refresh)
    }
}