package com.system.stockmonitor.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.stockmonitor.R
import com.system.stockmonitor.models.StockData
import com.system.stockmonitor.repository.ApiRepository
import com.system.stockmonitor.repository.StockStored
import com.system.stockmonitor.repository.StorageRepository
import kotlinx.android.synthetic.main.activity_stock_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class StockListActivity : BaseActivity() {

    private val business: ApiRepository by lazy {
        ApiRepository()
    }

    private val storage: StorageRepository by lazy {
        StorageRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_list)

        addButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = StockAdapter(arrayListOf()) {
            storage.remove(it.id, it.symbol)

            GlobalScope.launch(dispatcher) {
                getStocks()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(dispatcher) {
            while (true) {
                getStocks()
                delay(TimeUnit.MINUTES.toMillis(1))
            }
        }
    }

    private suspend fun getStocks() {

        progressBar.visibility = View.VISIBLE

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

                (recyclerview.adapter as StockAdapter).update(list)
                accountBalanceValue.text =
                    list.sumByDouble { it.totalCurrentValue }.toCurrency()

            } else {
                (recyclerview.adapter as StockAdapter).update(listOf())
                accountBalanceValue.text = 0.0.toCurrency()

                delay(1000)
            }

            progressBar.visibility = View.GONE

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
        }
    }

    private fun getStartDate(): String {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, -1)
        return SimpleDateFormat("yyyy-MM-dd'T'00:00:00", Locale.US).format(date.time)
    }

    private fun getEndDate() = SimpleDateFormat("yyyy-MM-dd'T'23:59:59", Locale.US).format(Date())
}

fun Double.toCurrency(): String =
    NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)

private fun StockData.toVO(stored: StockStored): StockVO =
    StockVO(
        stored.id,
        stored.symbol,
        stored.name,
        stored.buyValue,
        stored.buyValue * stored.amount,
        close,
        close * stored.amount
    )
