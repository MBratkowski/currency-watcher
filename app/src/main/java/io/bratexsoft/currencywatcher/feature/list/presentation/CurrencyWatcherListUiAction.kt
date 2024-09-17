package io.bratexsoft.currencywatcher.feature.list.presentation

import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyCodeDomain

sealed interface CurrencyWatcherListUiAction {
	data class OpenDetails(
		val currencyCode: CurrencyCodeDomain,
	) : CurrencyWatcherListUiAction
}
