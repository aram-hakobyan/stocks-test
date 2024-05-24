package com.nativeteams.stocksscreen.model

data class StockModel(
    val symbol: String,
    val fullExchangeName: String,
    val value: String,
    val diff: String,
    val diffPercentage: String,
    val diffSign: String
)
