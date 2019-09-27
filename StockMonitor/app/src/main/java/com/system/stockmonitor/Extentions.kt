package com.system.stockmonitor

import com.system.stockmonitor.models.StockData
import com.system.stockmonitor.repository.StockStored
import com.system.stockmonitor.views.StockVO
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Double.toCurrency(): String =
    NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)

fun Double.toPercentage(): String {
    val decimalFormat = DecimalFormat()
    decimalFormat.maximumFractionDigits = 2
    return decimalFormat.format(this) + "%"
}

fun StockData.toVO(stored: StockStored): StockVO =
    StockVO(
        stored.id,
        stored.symbol,
        stored.name,
        stored.buyValue,
        stored.buyValue * stored.amount,
        close,
        close * stored.amount
    )

