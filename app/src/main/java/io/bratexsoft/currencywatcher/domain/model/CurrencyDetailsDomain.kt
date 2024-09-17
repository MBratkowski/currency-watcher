package io.bratexsoft.currencywatcher.domain.model

import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyCodeDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyNameDomain
import kotlinx.collections.immutable.ImmutableList

data class CurrencyDetailsDomain(
	val name: CurrencyNameDomain,
	val code: CurrencyCodeDomain,
	val rates: ImmutableList<RateDomain>,
)
