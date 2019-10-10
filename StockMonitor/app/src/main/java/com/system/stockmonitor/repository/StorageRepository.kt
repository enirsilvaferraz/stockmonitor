package com.system.stockmonitor.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StorageRepository(val context: Context) {

    fun storeStocks(stocks: List<StockStored>) {
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("STOCKS", Gson().toJson(stocks))
        editor.apply()
    }

    fun storeStocks(stock: StockStored) {

        val stocks = getStocks().toMutableList()
        stocks.add(stock)
        storeStocks(stocks)
    }

    fun getStocks(): List<StockStored> {
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val type = object : TypeToken<List<StockStored>>() {}.type
        return Gson().fromJson(sharedPreference.getString("STOCKS", "[]"), type)
    }

    fun remove(id: Int?, symbol: String) {

        if (id != null) {
            storeStocks(getStocks().toMutableList().filterNot { it.id == id })
        } else {
            storeStocks(getStocks().toMutableList().filterNot { it.symbol == symbol && it.id == null })
        }
    }
}