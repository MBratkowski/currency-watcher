package io.bratexsoft.currencywatcher.domain.usecase

import io.bratexsoft.currencywatcher.domain.model.CurrencyDetailsDomain
import io.bratexsoft.currencywatcher.domain.repository.CurrencyRepository
import java.time.LocalDate
import javax.inject.Inject

private const val TWO_WEEKS = 2L

class GetCurrencyDetailsUseCase
	@Inject
	constructor(
		private val currencyRepository: CurrencyRepository,
	) {
		suspend operator fun invoke(code: String): Result<CurrencyDetailsDomain> {
			val endDate = LocalDate.now()
			val startDate = endDate.minusWeeks(TWO_WEEKS)
			return currencyRepository.getCurrencyDetails(
				code = code,
				startDate = startDate,
				endDate = endDate,
			)
		}
	}
