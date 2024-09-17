package io.bratexsoft.currencywatcher.feature.list.presentation

import io.bratexsoft.currencywatcher.domain.model.CurrencyExchangeRateDomain
import kotlinx.collections.immutable.ImmutableList

sealed interface CurrencyWatcherListUiState {
	data object Loading : CurrencyWatcherListUiState

	data class Success(
		val currencyRates: ImmutableList<CurrencyExchangeRateDomain>,
	) : CurrencyWatcherListUiState

	data class Error(
		val throwable: Throwable
	) : CurrencyWatcherListUiState
}
