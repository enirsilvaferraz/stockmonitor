package com.system.stockmonitor.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.system.stockmonitor.R
import com.system.stockmonitor.toCurrency
import com.system.stockmonitor.toPercentage
import kotlinx.android.synthetic.main.item_stock.view.*

class StockAdapter(
    private val list: ArrayList<StockVO>,
    private val callback: (model: StockVO) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockViewHolderV3(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_stock_v3,
                parent,
                false
            ),
            callback
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StockViewHolderV3).bind(list[position])
    }

    fun update(subList: List<StockVO>) {
        list.clear()
        list.addAll(subList)
        notifyDataSetChanged()
    }
}
