package com.system.stockmonitor.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StockStored(

    @PrimaryKey(autoGenerate = true) val id: Int?,
    val symbol: String,
    val name: String,
    val buyValue: Double,
    var saleValue: Double,
    val amount: Int,
    val buySuggest: Double = 0.0,
    val saleSuggest: Double = 0.0
)