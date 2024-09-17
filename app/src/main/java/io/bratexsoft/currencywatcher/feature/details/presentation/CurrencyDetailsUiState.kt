package io.bratexsoft.currencywatcher.feature.details.presentation

import io.bratexsoft.currencywatcher.feature.details.model.CurrencyDetailsUiModel

sealed interface CurrencyDetailsUiState {
	data object Loading : CurrencyDetailsUiState

	data class Success(
		val details: CurrencyDetailsUiModel,
	) : CurrencyDetailsUiState

	data class Error(
		val throwable: Throwable
	) : CurrencyDetailsUiState
}
