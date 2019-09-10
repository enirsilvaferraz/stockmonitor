package com.system.stockmonitor.views

data class StockVO(
    val id: Int?,
    val symbol: String,
    val name: String,
    val buyValue: Double,
    val totalBuyValue: Double,
    val currentValue: Double,
    val totalCurrentValue: Double
) {
    fun isGrowing() = buyValue < currentValue
}
