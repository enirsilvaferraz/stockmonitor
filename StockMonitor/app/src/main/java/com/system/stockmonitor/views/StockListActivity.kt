package com.system.stockmonitor.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.stockmonitor.R
import kotlinx.android.synthetic.main.activity_stock_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class StockListActivity : BaseActivity() {

    private val stockViewModel: StockListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_list)

        stockViewModel.stocks.observe(this, Observer {
            (recyclerview.adapter as StockAdapter).update(it)
        })

        stockViewModel.title.observe(this, Observer {
            accountBalanceValue.text = it
        })

        stockViewModel.inProgress.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        addButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = StockAdapter(arrayListOf()) {
            goToRegister(it.id)
        }
    }

    override fun onResume() {
        super.onResume()
        stockViewModel.requestData()
    }

    fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToRegister(id: Int?) {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra("MODEL", id)
        startActivity(intent)
    }
}
