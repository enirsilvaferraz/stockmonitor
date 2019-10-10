package com.system.stockmonitor.views

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.system.stockmonitor.R
import com.system.stockmonitor.repository.ApiRepository
import com.system.stockmonitor.repository.StockStored
import com.system.stockmonitor.repository.StorageRepository
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class RegisterActivity : AppCompatActivity() {

    val storage: StorageRepository by lazy {
        StorageRepository(this)
    }

    val business: ApiRepository by lazy {
        ApiRepository()
    }

    var stockStored: StockStored? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (intent.hasExtra("MODEL")) {
            stockStored = Gson().fromJson(intent.getStringExtra("MODEL"), StockStored::class.java)

            stockSymbol.setText(stockStored!!.symbol)
            stockName.setText(stockStored!!.name)
            amount.setText(stockStored!!.amount.toString())
            buyValue.setText(stockStored!!.buyValue.toString())
            buySuggest.setText(stockStored!!.buySuggest.toString())
            saleSuggest.setText(stockStored!!.saleSuggest.toString())
        }

        stockSymbol.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(5))
        stockSymbol.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (!p0.isNullOrBlank()) {

                    GlobalScope.launch(Main) {

                        try {

                            val credentials = business.getCredentials()
                            val stockInfo =
                                business.getStockInfo(stockSymbol.text.toString(), credentials)

                            stockName.setText(stockInfo.company_name)
                        } catch (e: Exception) {
                            Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    stockName.setText("")
                }
            }
        })

        deleteButton.setOnClickListener {
            if (stockStored != null) {
                storage.remove(stockStored!!.id, stockStored!!.symbol)
                Toast.makeText(this, "Removed!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        saveButton.setOnClickListener {

            if (verifyField(stockSymbol, "Stock Code", 5, 5)
                && verifyField(stockName, "Stock Name", null, null)
                && verifyField(amount, "Amount", null, null)
                && verifyField(buyValue, "Buy Value", null, null)
            ) {

                val id = if (stockStored != null) stockStored!!.id else Random.nextInt()

                val stored = StockStored(
                    id = id,
                    symbol = stockSymbol.text.toString(),
                    name = stockName.text.toString(),
                    buyValue = buyValue.text.toString().toDouble(),
                    amount = amount.text.toString().toInt(),
                    buySuggest = buySuggest.text.toString().toDouble(),
                    saleSuggest = saleSuggest.text.toString().toDouble()
                )

                storage.storeStocks(stored)

                finish()
            }
        }
    }

    private fun verifyField(
        field: TextInputEditText,
        name: String,
        minLength: Int?,
        maxLength: Int?
    ) =
        if (field.text.toString().isBlank()) {
            Toast.makeText(this, "$name is required!", Toast.LENGTH_SHORT).show()
            false
        } else if (minLength != null && field.text.toString().length < minLength) {
            Toast.makeText(this, "Minimum length for $name is $minLength!", Toast.LENGTH_SHORT)
                .show()
            false
        } else if (maxLength != null && field.text.toString().length < maxLength) {
            Toast.makeText(this, "Maximum length for $name is $maxLength!", Toast.LENGTH_SHORT)
                .show()
            false
        } else
            true

}
