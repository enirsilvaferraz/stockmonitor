package com.system.stockmonitor.views

import com.system.stockmonitor.repository.ApiRepository
import com.system.stockmonitor.repository.StorageRepository
import com.system.stockmonitor.toCurrency
import com.system.stockmonitor.toVO
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class StockListPresenter(
    private val view: StockListActivity,
    private val business: ApiRepository,
    private val storage: StorageRepository
) {

    suspend fun load() {

        view.showLoading()

        try {

            val stored = storage.getStocks()

            if (stored.isNotEmpty()) {

                val credentials = business.getCredentials()

                val list = stored.map {
                    business.getStockData(
                        it.symbol,
                        getStartDate(),
                        getEndDate(),
                        credentials
                    ).toVO(it)
                }

                view.showData(list.sumByDouble { it.totalCurrentValue }.toCurrency(), list)

            } else {
                view.showData(0.0.toCurrency(), listOf())
                delay(1000)
            }

            view.hideLoading()

        } catch (e: Exception) {

            view.showMessage(e.message)
            view.hideLoading()
        }
    }

    private fun getStartDate(): String {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, -1)
        return SimpleDateFormat("yyyy-MM-dd'T'00:00:00", Locale.US).format(date.time)
    }

    private fun getEndDate() = SimpleDateFormat("yyyy-MM-dd'T'23:59:59", Locale.US).format(Date())

    fun remove(id: Int?, symbol: String) {
        storage.remove(id, symbol)
    }

}
