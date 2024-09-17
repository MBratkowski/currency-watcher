package io.bratexsoft.currencywatcher.domain.usecase

import io.bratexsoft.currencywatcher.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyListRateUseCase
	@Inject
	constructor(
		private val currencyRepository: CurrencyRepository,
	) {
		suspend operator fun invoke() = currencyRepository.getCurrencyRates()
	}
