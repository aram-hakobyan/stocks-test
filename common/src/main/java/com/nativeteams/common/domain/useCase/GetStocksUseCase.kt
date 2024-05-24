package com.nativeteams.common.domain.useCase

import com.nativeteams.common.domain.model.Result
import com.nativeteams.common.domain.model.Stock
import com.nativeteams.common.domain.repository.StocksRepository
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