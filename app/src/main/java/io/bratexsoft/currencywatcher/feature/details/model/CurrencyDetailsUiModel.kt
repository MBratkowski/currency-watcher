package io.bratexsoft.currencywatcher.feature.details.model

import kotlinx.collections.immutable.ImmutableList

class CurrencyDetailsUiModel(
	val currencyCode: String,
	val currencyName: String,
	val rates: ImmutableList<CurrencyDetailsRateUiModel>,
)
