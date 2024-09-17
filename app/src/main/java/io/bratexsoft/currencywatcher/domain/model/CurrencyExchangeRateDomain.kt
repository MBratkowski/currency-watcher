package io.bratexsoft.currencywatcher.domain.model

import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyCodeDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyNameDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.ExchangeRateDomain

data class CurrencyExchangeRateDomain(
	val name: CurrencyNameDomain,
	val code: CurrencyCodeDomain,
	val rate: ExchangeRateDomain,
)
