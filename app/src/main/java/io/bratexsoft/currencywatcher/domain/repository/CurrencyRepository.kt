package io.bratexsoft.currencywatcher.domain.repository

import io.bratexsoft.currencywatcher.domain.model.CurrencyDetailsDomain
import io.bratexsoft.currencywatcher.domain.model.CurrencyExchangeRateDomain
import java.time.LocalDate

interface CurrencyRepository {
	suspend fun getCurrencyRates(): Result<List<CurrencyExchangeRateDomain>>

	suspend fun getCurrencyDetails(
		code: String,
		startDate: LocalDate,
		endDate: LocalDate,
	): Result<CurrencyDetailsDomain>
}
