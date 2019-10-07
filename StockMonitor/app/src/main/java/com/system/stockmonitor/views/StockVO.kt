package com.system.stockmonitor.views

import com.system.stockmonitor.toCurrency
import com.system.stockmonitor.toPercentage

data class StockVO(
    val id: Int?,
    val symbol: String,
    val name: String,
    private val amount: Int,
    private val buyValue: Double,
    private val buySuggest: Double,
    private val saleValue: Double,
    private val saleSuggest: Double
) {

    val quantityF: String
        get() = amount.toString()

    val buyValueF: String
        get() = buyValue.toCurrency()

    val buyValueTotalF: String
        get() = (buyValue * amount).toCurrency()

    val buySuggestF: String
        get() = buySuggest.toCurrency()

    val buySuggestTotalF: String
        get() = (buySuggest * amount).toCurrency()

    val saleValueF: String
        get() = saleValue.toCurrency()

    val saleValueTotal: Double
        get() = (saleValue * amount)

    val saleValueTotalF: String
        get() = (saleValue * amount).toCurrency()

    val saleSuggestF: String
        get() = saleSuggest.toCurrency()

    val saleSuggestTotalF: String
        get() = (saleSuggest * amount).toCurrency()

    val growthPercentage: String
        get() = ((saleValue * 100 / buyValue) - 100).toPercentage()

    val differenceValueF: String
        get() = ((saleValue * amount) - (buyValue * amount)).toCurrency()

    fun isGrowing() = buyValue < saleValue
}
