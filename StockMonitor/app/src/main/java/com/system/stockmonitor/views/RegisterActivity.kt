package com.system.stockmonitor.views

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        stockSymbol.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(5))
        stockSymbol.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (!p0.isNullOrBlank()) {

                    GlobalScope.launch(Main) {

                        val credentials = business.getCredentials()
                        val stockInfo =
                            business.getStockInfo(stockSymbol.text.toString(), credentials)

                        stockName.setText(stockInfo.company_name)
                    }
                } else {
                    stockName.setText("")
                }
            }
        })

        saveButton.setOnClickListener {

            if (verifyField(stockSymbol, "Stock Code", 5, 5)
                && verifyField(stockName, "Stock Name", null, null)
                && verifyField(amount, "Amount", null, null)
                && verifyField(buyValue, "Buy Value", null, null)
            ) {

                val stored = StockStored(
                    Random.nextInt(),
                    stockSymbol.text.toString(),
                    stockName.text.toString(),
                    buyValue.text.toString().toDouble(),
                    amount.text.toString().toInt()
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
