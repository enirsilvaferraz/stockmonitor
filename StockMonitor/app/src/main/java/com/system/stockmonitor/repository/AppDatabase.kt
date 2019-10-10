package com.system.stockmonitor.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(StockStored::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockStoredDao(): StockStoredDao
}