package io.bratexsoft.currencywatcher.data.mapper

import io.bratexsoft.currencywatcher.data.model.CurrencyDetailsRateResponseApi
import io.bratexsoft.currencywatcher.data.model.CurrencyDetailsResponseApi
import io.bratexsoft.currencywatcher.data.model.CurrencyRateResponseApi
import io.bratexsoft.currencywatcher.domain.model.CurrencyDetailsDomain
import io.bratexsoft.currencywatcher.domain.model.CurrencyExchangeRateDomain
import io.bratexsoft.currencywatcher.domain.model.RateDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyCodeDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyNameDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.ExchangeRateDomain
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import javax.inject.Inject

class CurrencyMapper
	@Inject
	constructor() {
		fun mapToDomain(currencyRate: CurrencyRateResponseApi): CurrencyExchangeRateDomain? {
			return CurrencyExchangeRateDomain(
				name = currencyRate.currency?.toCurrencyName() ?: return null,
				code = currencyRate.code?.toCurrencyCode() ?: return null,
				rate = currencyRate.mid?.toExchangeRate() ?: return null,
			)
		}

		fun mapToDomain(currencyRate: CurrencyDetailsResponseApi): CurrencyDetailsDomain? {
			return CurrencyDetailsDomain(
				name = currencyRate.currency?.toCurrencyName() ?: return null,
				code = currencyRate.code?.toCurrencyCode() ?: return null,
				rates =
					currencyRate
						.rates
						?.mapNotNull { it.toRateDomain() }
						?.sortedByDescending { it.date }
						?.toImmutableList()
						?: return null,
			)
		}

		private fun String.toCurrencyName() = CurrencyNameDomain(this)

		private fun String.toCurrencyCode() = CurrencyCodeDomain(this)

		private fun Double.toExchangeRate() = ExchangeRateDomain(this)

		private fun CurrencyDetailsRateResponseApi.toRateDomain(): RateDomain? {
			val date = effectiveDate?.let { LocalDate.parse(it) } ?: return null
			val value = mid?.toExchangeRate() ?: return null
			return RateDomain(date = date, value = value)
		}
	}
