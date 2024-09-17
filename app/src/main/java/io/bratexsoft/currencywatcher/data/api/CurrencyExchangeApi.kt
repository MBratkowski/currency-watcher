package io.bratexsoft.currencywatcher.data.api

import io.bratexsoft.currencywatcher.data.model.CurrencyDetailsResponseApi
import io.bratexsoft.currencywatcher.data.model.CurrencyResponseApi
import java.time.LocalDate

interface CurrencyExchangeApi {
	suspend fun getCurrencyRates(): List<CurrencyResponseApi>

	suspend fun getCurrencyDetails(
		code: String,
		startDate: LocalDate,
		endDate: LocalDate,
	): CurrencyDetailsResponseApi
}
