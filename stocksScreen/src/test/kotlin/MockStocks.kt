import com.androidart.common.domain.model.MarketPreviousClose
import com.androidart.common.domain.model.MarketTime
import com.androidart.common.domain.model.Spark
import com.androidart.common.domain.model.Stock

fun mockStocks(): List<Stock> {
    val marketTime = MarketTime(
        raw = 1620000000,
        fmt = "10:00AM EDT"
    )

    val marketPreviousClose = MarketPreviousClose(
        raw = 150.0,
        fmt = "150.00"
    )

    val spark = Spark(
        symbol = "AAPL",
        dataGranularity = 1,
        end = 1620000000,
        start = 1610000000,
        previousClose = 150.0,
        chartPreviousClose = 140.0,
        close = listOf(150.0, 151.0, 152.0),
        timestamp = listOf(1620000000, 1620000600, 1620001200)
    )

    return listOf(
        Stock(
            exchangeTimezoneName = "America/New_York",
            fullExchangeName = "NasdaqGS",
            symbol = "AAPL",
            gmtOffSetMilliseconds = -18000000,
            cryptoTradeable = false,
            exchangeDataDelayedBy = 0,
            firstTradeDateMilliseconds = 345479400000,
            language = "en-US",
            regularMarketTime = marketTime,
            exchangeTimezoneShortName = "EST",
            quoteType = "EQUITY",
            hasPrePostMarketData = true,
            marketState = "CLOSED",
            customPriceAlertConfidence = "HIGH",
            market = "us_market",
            spark = spark,
            priceHint = 2,
            tradeable = true,
            sourceInterval = 15,
            exchange = "NMS",
            region = "US",
            shortName = "Apple Inc.",
            regularMarketPreviousClose = marketPreviousClose,
            triggerable = true
        ),
        Stock(
            exchangeTimezoneName = "America/New_York",
            fullExchangeName = "NasdaqGS",
            symbol = "GOOGL",
            gmtOffSetMilliseconds = -18000000,
            cryptoTradeable = false,
            exchangeDataDelayedBy = 0,
            firstTradeDateMilliseconds = 345479400000,
            language = "en-US",
            regularMarketTime = marketTime,
            exchangeTimezoneShortName = "EST",
            quoteType = "EQUITY",
            hasPrePostMarketData = true,
            marketState = "CLOSED",
            customPriceAlertConfidence = "HIGH",
            market = "us_market",
            spark = spark.copy(symbol = "GOOGL"),
            priceHint = 2,
            tradeable = true,
            sourceInterval = 15,
            exchange = "NMS",
            region = "US",
            shortName = "Alphabet Inc.",
            regularMarketPreviousClose = marketPreviousClose.copy(raw = 2500.0, fmt = "2500.00"),
            triggerable = true
        )
    )
}