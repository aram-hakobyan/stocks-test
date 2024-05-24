package com.nativeteams.stocksscreen.model

import com.nativeteams.common.domain.ext.formatted
import com.nativeteams.common.domain.ext.growthPercentage
import com.nativeteams.common.domain.model.Stock

fun Stock.asPresentation(): StockModel {
    spark.run {
        when (close.size) {
            0 -> return StockModel(
                symbol,
                fullExchangeName,
                "",
                "",
                "0.00",
                ""
            )

            else -> {
                val lastClose: Double = close.last()
                val diff: Double = lastClose - previousClose
                val diffPercentage: Double = (previousClose to lastClose).growthPercentage()
                val diffSign: String = if (diffPercentage.compareTo(0.00) > 0) "+" else ""

                return StockModel(
                    symbol,
                    fullExchangeName,
                    lastClose.formatted(),
                    diff.formatted(),
                    "${diffPercentage.formatted()}%",
                    diffSign
                )
            }
        }
    }
}

fun List<Stock>.asPresentation(): List<StockModel> = map { it.asPresentation() }