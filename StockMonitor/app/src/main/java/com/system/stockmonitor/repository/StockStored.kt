package com.system.stockmonitor.repository

data class StockStored(

    val id: Int?,
    val symbol: String,
    val name: String,
    val buyValue: Double,
    val amount: Int

)