package com.system.stockmonitor.models

data class StockData(
    val datetime: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val financial_volume: Long,
    val number_of_trades: Long,
    val number_of_stocks: Long
)
