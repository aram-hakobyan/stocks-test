package com.nativeteams.stocksscreen.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nativeteams.stocksscreen.R
import com.nativeteams.stocksscreen.databinding.StockItemBinding
import com.nativeteams.stocksscreen.model.StockModel
import javax.inject.Inject

class StocksAdapter @Inject constructor() : ListAdapter<StockModel, RecyclerView.ViewHolder>(
    StockDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as StockItemViewHolder).bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        parent.context.run {
            val binding = StockItemBinding.inflate(
                LayoutInflater.from(this), parent, false
            )
            StockItemViewHolder(this, binding)
        }


    class StockItemViewHolder(
        private val context: Context, binding: StockItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val symbolTextView: TextView = itemView.findViewById(R.id.stockName)
        private val fullNameTextView: TextView = itemView.findViewById(R.id.stockFullName)
        private val valueTextView: TextView = itemView.findViewById(R.id.stockValue)
        private val diffTextView: TextView = itemView.findViewById(R.id.stockValueDiff)
        private val diffPercentageTextView: TextView =
            itemView.findViewById(R.id.stockValueDiffPercentage)

        fun bind(stock: StockModel) {
            symbolTextView.text = stock.symbol
            fullNameTextView.text = stock.fullExchangeName

            valueTextView.text = stock.value
            diffTextView.text = context.getString(
                R.string.diffFormat, stock.diffSign, stock.diff
            )

            val diffPercentageText = "${stock.diffSign}${stock.diffPercentage}"
            diffPercentageTextView.text = context.getString(
                R.string.diffPercentageFormat, diffPercentageText
            )

            val color = getColorBySign(
                diffPercentageText.first()
            )
            diffTextView.setTextColor(color)
            diffPercentageTextView.setTextColor(color)
        }

        private fun getColorBySign(sign: Char): Int {
            val colorId = when (sign) {
                '+' -> com.nativeteams.common.R.color.green
                '-' -> com.nativeteams.common.R.color.red
                else -> com.nativeteams.common.R.color.gray
            }
            return context.getColor(colorId)
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