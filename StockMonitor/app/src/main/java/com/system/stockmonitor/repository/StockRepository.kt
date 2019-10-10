package com.system.stockmonitor.repository

import androidx.lifecycle.LiveData

interface StockRepository {

    fun getAll(): LiveData<List<StockStored>>

    fun getAllSync(): List<StockStored>

    fun save(data: StockStored)

    fun save(data: List<StockStored>)

    fun delete(data: StockStored)

    fun update(data: StockStored)
}
