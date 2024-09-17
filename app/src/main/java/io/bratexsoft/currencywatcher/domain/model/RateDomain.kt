package io.bratexsoft.currencywatcher.domain.model

import io.bratexsoft.currencywatcher.domain.model.wrapper.ExchangeRateDomain
import java.time.LocalDate

data class RateDomain(
	val date: LocalDate,
	val value: ExchangeRateDomain,
)
