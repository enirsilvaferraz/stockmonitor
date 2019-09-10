package com.system.stockmonitor.views

data class StockVO(
    val id: Int?,
    val symbol: String,
    val name: String,
    val buyValue: Double,
    val totalBuyValue: Double,
    val currentValue: Double,
    val totalCurrentValue: Double,
    val diffValue: Double = totalCurrentValue - totalBuyValue
) {
    fun diffUnitPercent() = (currentValue * 100 / buyValue) - 100

    fun isGrowing() = buyValue < currentValue
}
