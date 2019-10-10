package com.system.stockmonitor.views

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.system.stockmonitor.R
import kotlinx.android.synthetic.main.item_stock.view.container
import kotlinx.android.synthetic.main.item_stock.view.diffUnitPercent
import kotlinx.android.synthetic.main.item_stock.view.stockName
import kotlinx.android.synthetic.main.item_stock.view.stockSymbol
import kotlinx.android.synthetic.main.item_stock_v3.view.*

class StockViewHolderV3(view: View, val callback: (model: StockVO) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bind(model: StockVO) {

        itemView.stockSymbol.text = model.symbol
        itemView.stockName.text = model.name
        itemView.diffUnitPercent.text = model.growthPercentage
        itemView.quantity.text = model.quantityF
        itemView.differenceValue.text = model.differenceValueF

        itemView.buyValue.text = model.buyValueF
        itemView.buyValueTotal.text = model.buyValueTotalF
        itemView.buySuggest.text = model.buySuggestF
        itemView.buySuggestTotal.text = model.buySuggestTotalF

        itemView.saleValue.text = model.saleValueF
        itemView.saleValueTotal.text = model.saleValueTotalF
        itemView.saleSuggest.text = model.saleSuggestF
        itemView.saleSuggestTotal.text = model.saleSuggestTotalF

        val color = ContextCompat.getColor(
            itemView.context, if (model.isGrowing()) R.color.green else R.color.red
        )

        itemView.diffUnitPercent.setTextColor(color)
        itemView.differenceValue.setTextColor(color)
        itemView.saleValue.setTextColor(color)
        itemView.saleValueTotal.setTextColor(color)

        itemView.container.setOnClickListener {
            callback(model)
        }
    }
}