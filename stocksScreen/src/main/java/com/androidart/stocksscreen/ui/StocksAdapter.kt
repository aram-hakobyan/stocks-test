package com.androidart.stocksscreen.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidart.stocksscreen.R
import com.androidart.stocksscreen.databinding.StockItemBinding
import com.androidart.stocksscreen.model.StockModel

class StocksAdapter : ListAdapter<StockModel, RecyclerView.ViewHolder>(
    StockDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as StockItemViewHolder).bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = StockItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StockItemViewHolder(binding)
    }

    class StockItemViewHolder(
        private val binding: StockItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(stock: StockModel) = binding.run {
            stockName.text = stock.symbol
            stockFullName.text = stock.fullExchangeName
            stockValue.text = stock.value
            stockValueDiff.text = root.context.getString(
                R.string.diffFormat, stock.diffSign, stock.diff
            )
            val diffPercentageText = "${stock.diffSign}${stock.diffPercentage}"
            stockValueDiffPercentage.text = root.context.getString(
                R.string.diffPercentageFormat, diffPercentageText
            )
            val color = root.context.getColorBySign(
                diffPercentageText.firstOrNull()
            )
            stockValueDiff.setTextColor(color)
            stockValueDiffPercentage.setTextColor(color)
        }

        private fun Context.getColorBySign(sign: Char?): Int {
            val colorId = when (sign) {
                '+' -> com.androidart.common.R.color.green
                '-' -> com.androidart.common.R.color.red
                else -> com.androidart.common.R.color.gray
            }
            return getColor(colorId)
        }
    }

    class StockDiffCallback : DiffUtil.ItemCallback<StockModel>() {
        override fun areItemsTheSame(oldItem: StockModel, newItem: StockModel): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: StockModel, newItem: StockModel): Boolean {
            return oldItem == newItem
        }
    }
}