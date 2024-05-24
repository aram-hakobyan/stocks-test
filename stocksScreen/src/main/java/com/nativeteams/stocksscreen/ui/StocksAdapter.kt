package com.nativeteams.stocksscreen.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nativeteams.stocksscreen.R
import com.nativeteams.stocksscreen.databinding.StockItemBinding
import com.nativeteams.stocksscreen.model.StockModel

class StocksAdapter : ListAdapter<StockModel, RecyclerView.ViewHolder>(
    StockDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as StockItemViewHolder).bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        StockItemViewHolder(
            StockItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    class StockItemViewHolder(
        binding: StockItemBinding
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
            diffTextView.text = diffTextView.context.getString(
                R.string.diffFormat, stock.diffSign, stock.diff
            )

            val diffPercentageText = "${stock.diffSign}${stock.diffPercentage}"
            diffPercentageTextView.text = diffPercentageTextView.context.getString(
                R.string.diffPercentageFormat, diffPercentageText
            )

            val color = when (diffPercentageText.first()) {
                '+' -> Color.GREEN
                '-' -> Color.RED
                else -> Color.GRAY
            }
            diffTextView.setTextColor(color)
            diffPercentageTextView.setTextColor(color)
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