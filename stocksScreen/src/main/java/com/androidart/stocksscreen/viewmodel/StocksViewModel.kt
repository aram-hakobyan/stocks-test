package com.androidart.stocksscreen.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidart.common.domain.model.Result
import com.androidart.common.domain.model.Stock
import com.androidart.common.domain.useCase.GetStocksUseCase
import com.androidart.stocksscreen.R
import com.androidart.stocksscreen.model.StockModel
import com.androidart.stocksscreen.model.asPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StocksViewModel @Inject constructor(
    private val getStocksUseCase: GetStocksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<StocksUiState>(StocksUiState.Loading)
    val uiState: StateFlow<StocksUiState>
        get() = _uiState.asStateFlow()

    fun loadStocks(refresh: Boolean = false) = viewModelScope.launch {
        _uiState.update { StocksUiState.Loading }

        when (val result = getStocksUseCase(refresh)) {
            is Result.Success -> {
                onStocksLoaded(result.data)
            }

            is Result.Failure -> {
                onError(R.string.network_error)
            }
        }
    }

    private fun onStocksLoaded(
        stocks: List<Stock>
    ) {
        _uiState.update {
            StocksUiState.Success(
                stocks.asPresentation()
            )
        }
    }

    private fun onError(
        @StringRes messageId: Int
    ) {
        _uiState.update {
            StocksUiState.Error(messageId)
        }
    }

    sealed interface StocksUiState {
        data object Loading : StocksUiState
        data class Success(val data: List<StockModel>) : StocksUiState
        data class Error(@StringRes val messageId: Int) : StocksUiState
    }
}