package com.system.stockmonitor.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.system.stockmonitor.R
import com.system.stockmonitor.toCurrency
import com.system.stockmonitor.toPercentage

class StockAdapter(
    private val list: ArrayList<StockVO>,
    private val callback: (model: StockVO) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_stock,
                parent,
                false
            ),
            callback
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StockViewHolder).bind(list[position])
    }

    fun update(subList: List<StockVO>) {
        list.clear()
        list.addAll(subList)
        notifyDataSetChanged()
    }

    class StockViewHolder(view: View, val callback: (model: StockVO) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val container = itemView.findViewById<ConstraintLayout>(R.id.container)
        private val stockSymbol = itemView.findViewById<AppCompatTextView>(R.id.stockSymbol)
        private val stockName = itemView.findViewById<AppCompatTextView>(R.id.stockName)
        private val currentValue = itemView.findViewById<AppCompatTextView>(R.id.currentValue)
        private val buyValue = itemView.findViewById<AppCompatTextView>(R.id.buyValue)
        private val totalBuyValue = itemView.findViewById<AppCompatTextView>(R.id.totalBuyValue)
        private val totalValue = itemView.findViewById<AppCompatTextView>(R.id.totalValue)
        private val diffValue = itemView.findViewById<AppCompatTextView>(R.id.diffValue)
        private val diffUnitPercent = itemView.findViewById<AppCompatTextView>(R.id.diffUnitPercent)

        fun bind(model: StockVO) {

            stockSymbol.text = model.symbol
            stockName.text = model.name
            buyValue.text = model.buyValue.toCurrency()
            totalBuyValue.text = model.totalBuyValue.toCurrency()
            currentValue.text = model.currentValue.toCurrency()
            totalValue.text = model.totalCurrentValue.toCurrency()
            diffValue.text = model.diffValue().toCurrency()
            diffUnitPercent.text = model.diffUnitPercent().toPercentage()

            val color = ContextCompat.getColor(
                itemView.context, if (model.isGrowing()) R.color.green else R.color.red
            )

            currentValue.setTextColor(color)
            totalValue.setTextColor(color)
            diffValue.setTextColor(color)
            diffUnitPercent.setTextColor(color)

            container.setOnLongClickListener {
                callback(model)
                true
            }
        }
    }
}
