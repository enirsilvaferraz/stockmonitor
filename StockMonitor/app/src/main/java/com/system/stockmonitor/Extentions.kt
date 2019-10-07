package com.system.stockmonitor

import com.system.stockmonitor.models.StockData
import com.system.stockmonitor.repository.StockStored
import com.system.stockmonitor.views.StockVO
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Double.toCurrency(): String =
    if (this == 0.0) "--" else NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)

fun Double.toPercentage(): String {
    val decimalFormat = DecimalFormat()
    decimalFormat.maximumFractionDigits = 2
    return decimalFormat.format(this) + "%"
}

fun StockData.toVO(stored: StockStored): StockVO =
    StockVO(
        id = stored.id,
        symbol = stored.symbol,
        name = stored.name,
        amount = stored.amount,
        buyValue = stored.buyValue,
        buySuggest = stored.buySuggest,
        saleValue = this.close,
        saleSuggest = stored.saleSuggest
    )

