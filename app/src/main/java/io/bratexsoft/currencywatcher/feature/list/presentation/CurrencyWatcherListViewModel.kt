package io.bratexsoft.currencywatcher.feature.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyCodeDomain
import io.bratexsoft.currencywatcher.domain.usecase.GetCurrencyListRateUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyWatcherListViewModel @Inject constructor(
	private val getCurrencyListRateUseCase: GetCurrencyListRateUseCase,
) : ViewModel() {
	private val _viewAction: MutableSharedFlow<CurrencyWatcherListUiAction> = MutableSharedFlow()
	val viewAction = _viewAction

	private val _viewState: MutableStateFlow<CurrencyWatcherListUiState> =
		MutableStateFlow(CurrencyWatcherListUiState.Loading)
	val viewState = _viewState.asStateFlow()

	init {
		viewModelScope.launch {
			fetchCurrencyRates()
		}
	}

	private suspend fun fetchCurrencyRates() {
		getCurrencyListRateUseCase().fold(
			onSuccess = { currencyRates ->
				_viewState.value = CurrencyWatcherListUiState.Success(
					currencyRates = currencyRates.toImmutableList(),
				)
			},
			onFailure = { _viewState.value = CurrencyWatcherListUiState.Error(throwable = it) },
		)
	}

	fun onItemClick(currencyCodeDomain: CurrencyCodeDomain) {
		viewModelScope.launch {
			_viewAction.emit(CurrencyWatcherListUiAction.OpenDetails(currencyCodeDomain))
		}
	}
}
