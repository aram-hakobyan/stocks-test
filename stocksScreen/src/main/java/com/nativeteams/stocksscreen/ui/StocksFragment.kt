package com.nativeteams.stocksscreen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nativeteams.stocksscreen.databinding.FragmentStocksBinding
import com.nativeteams.stocksscreen.viewmodel.StocksViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StocksFragment : Fragment() {

    private lateinit var binding: FragmentStocksBinding

    private val viewModel: StocksViewModel by viewModels()

    @Inject
    lateinit var stocksAdapter: StocksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setup()
        observeData(view)
        viewModel.loadStocks()
    }

    private fun observeData(view: View) {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    is StocksViewModel.StocksUiState.Error -> {
                        binding.hideProgress()
                        Snackbar.make(view, it.messageId, Snackbar.LENGTH_LONG).show()
                    }

                    StocksViewModel.StocksUiState.Loading -> {
                        binding.showProgress()
                    }

                    is StocksViewModel.StocksUiState.Success -> {
                        binding.hideProgress()
                        stocksAdapter.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun FragmentStocksBinding.setup() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stocksAdapter
        }

        swipeRefreshLayout.run {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.loadStocks(refresh = true)
            }
        }
    }

    private fun FragmentStocksBinding.showProgress() {
        if (!swipeRefreshLayout.isRefreshing) {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun FragmentStocksBinding.hideProgress() {
        progressBar.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
    }
}