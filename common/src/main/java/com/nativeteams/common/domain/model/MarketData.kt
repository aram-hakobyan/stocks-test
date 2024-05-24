package com.nativeteams.common.domain.model

data class MarketData(
    val marketSummaryAndSparkResponse: MarketSummary
)

data class MarketSummary(
    val result: List<Stock>,
    val error: Any?
)

data class Stock(
    val exchangeTimezoneName: String,
    val fullExchangeName: String,
    val symbol: String,
    val gmtOffSetMilliseconds: Long,
    val cryptoTradeable: Boolean,
    val exchangeDataDelayedBy: Int,
    val firstTradeDateMilliseconds: Long,
    val language: String,
    val regularMarketTime: MarketTime,
    val exchangeTimezoneShortName: String,
    val quoteType: String,
    val hasPrePostMarketData: Boolean,
    val marketState: String,
    val customPriceAlertConfidence: String,
    val market: String,
    val spark: Spark,
    val priceHint: Int,
    val tradeable: Boolean,
    val sourceInterval: Int,
    val exchange: String,
    val region: String,
    val shortName: String,
    val regularMarketPreviousClose: RegularMarketPreviousClose,
    val triggerable: Boolean
)

data class MarketTime(
    val raw: Long,
    val fmt: String
)

data class Spark(
    val dataGranularity: Int,
    val timestamp: List<Long>,
    val symbol: String,
    val end: Long,
    val previousClose: Double,
    val chartPreviousClose: Double,
    val start: Long,
    val close: List<Double>
)

data class RegularMarketPreviousClose(
    val raw: Double,
    val fmt: String
)