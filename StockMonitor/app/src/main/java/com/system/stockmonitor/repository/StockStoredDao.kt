package com.system.stockmonitor.repository

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StockStoredDao : StockRepository {

    @Query("SELECT * FROM stockstored")
    override fun getAll(): LiveData<List<StockStored>>

    @Query("SELECT * FROM stockstored")
    override fun getAllSync(): List<StockStored>

    @Insert
    override fun save(data: StockStored)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun save(data: List<StockStored>)

    @Update
    override fun update(data: StockStored)

    @Delete
    override fun delete(data: StockStored)
}