package com.system.stockmonitor.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.stockmonitor.repository.ApiRepository
import com.system.stockmonitor.repository.StockRepository
import com.system.stockmonitor.toCurrency
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class StockListViewModel(
    private val repository: StockRepository,
    private val business: ApiRepository
) :
    ViewModel() {

    val inProgress = MutableLiveData(true)

    val stocks = Transformations.map(repository.getAll()) {
        it.map { stored ->
            StockVO(
                id = stored.id,
                symbol = stored.symbol,
                name = stored.name,
                amount = stored.amount,
                buyValue = stored.buyValue,
                buySuggest = stored.buySuggest,
                saleValue = stored.saleValue,
                saleSuggest = stored.saleSuggest
            )
        }
    }

    val title = Transformations.map(stocks) {
        it.sumByDouble { vo -> vo.saleValueTotal }.toCurrency()
    }

    fun requestData() = viewModelScope.launch {

        while (true) {

            inProgress.postValue(true)

            val value = repository.getAllSync()
            if (!value.isNullOrEmpty()) {

                val data = business.getStockUpdated(
                    value,
                    getStartDate(),
                    getEndDate(),
                    business.getCredentials()
                )

                repository.save(data)
            }

            inProgress.postValue(false)

            delay(TimeUnit.MINUTES.toMillis(1))
        }
    }

    private fun getStartDate(): String {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, -1)
        return SimpleDateFormat("yyyy-MM-dd'T'00:00:00", Locale.US).format(date.time)
    }

    private fun getEndDate() = SimpleDateFormat("yyyy-MM-dd'T'23:59:59", Locale.US).format(Date())

}
