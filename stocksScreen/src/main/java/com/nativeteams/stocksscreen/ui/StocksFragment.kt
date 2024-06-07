package com.nativeteams.stocksscreen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nativeteams.stocksscreen.databinding.FragmentStocksBinding
import com.nativeteams.stocksscreen.viewmodel.StocksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StocksFragment : Fragment() {

    private lateinit var binding: FragmentStocksBinding

    private val viewModel: StocksViewModel by viewModels()

    private val stocksAdapter by lazy { StocksAdapter() }

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    when (uiState) {
                        is StocksViewModel.StocksUiState.Error -> {
                            binding.hideProgress()
                            Snackbar.make(view, uiState.messageId, Snackbar.LENGTH_LONG).show()
                        }

                        StocksViewModel.StocksUiState.Loading -> {
                            binding.showProgress()
                        }

                        is StocksViewModel.StocksUiState.Success -> {
                            binding.hideProgress()
                            stocksAdapter.submitList(uiState.data)
                        }
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