package com.system.stockmonitor.repository

data class StockStored(

    val id: Int?,
    val symbol: String,
    val name: String,
    val buyValue: Double,
    val amount: Int,
    val buySuggest: Double = 0.0,
    val saleSuggest: Double = 0.0
)