package com.androidart.stocksscreen.model

import com.androidart.common.domain.ext.decimalFormat
import com.androidart.common.domain.ext.growthPercentage
import com.androidart.common.domain.model.Stock

fun Stock.asPresentation(): StockModel {
    spark.run {
        return runCatching {
            val lastClose: Double = close.last()
            val diff: Double = lastClose - previousClose
            val diffPercentage: Double = (previousClose to lastClose).growthPercentage()
            val diffSign: String = if (diffPercentage.compareTo(0.00) > 0) "+" else ""

            StockModel(
                symbol,
                fullExchangeName,
                lastClose.decimalFormat(),
                diff.decimalFormat(),
                "${diffPercentage.decimalFormat()}%",
                diffSign
            )
        }.getOrElse {
            StockModel.EMPTY
        }
    }
}

fun List<Stock>.asPresentation(): List<StockModel> = map {
    it.asPresentation()
}.filter {
    it != StockModel.EMPTY
}