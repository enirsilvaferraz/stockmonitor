package com.system.stockmonitor.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.stockmonitor.R
import com.system.stockmonitor.repository.ApiRepository
import com.system.stockmonitor.repository.StorageRepository
import kotlinx.android.synthetic.main.activity_stock_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit


class StockListActivity : BaseActivity() {

    private val presenter: StockListPresenter by lazy {
        StockListPresenter(this, ApiRepository(), StorageRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_list)

        addButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = StockAdapter(arrayListOf()) {

            GlobalScope.launch(dispatcher) {
                presenter.remove(it.id, it.symbol)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(dispatcher) {
            while (true) {
                presenter.load()
                delay(TimeUnit.MINUTES.toMillis(1))
            }
        }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun showData(title: String, list: List<StockVO>) {
        accountBalanceValue.text = title
        (recyclerview.adapter as StockAdapter).update(list)
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
