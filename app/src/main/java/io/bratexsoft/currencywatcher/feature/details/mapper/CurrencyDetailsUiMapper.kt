package io.bratexsoft.currencywatcher.feature.details.mapper

import androidx.annotation.VisibleForTesting
import io.bratexsoft.currencywatcher.domain.model.CurrencyDetailsDomain
import io.bratexsoft.currencywatcher.feature.details.model.CurrencyDetailsRateUiModel
import io.bratexsoft.currencywatcher.feature.details.model.CurrencyDetailsUiModel
import io.bratexsoft.currencywatcher.feature.details.model.RateDiffStatusUiModel
import kotlinx.collections.immutable.toImmutableList
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val SCALE = 4
private const val CALCULATION_VALUE = 0.10

class CurrencyDetailsUiMapper
	@Inject
	constructor() {
		private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

		fun mapToUiModel(domainModel: CurrencyDetailsDomain): CurrencyDetailsUiModel {
			val averageRate = domainModel.rates.map { it.value.value }.average()

			return CurrencyDetailsUiModel(
				currencyCode = domainModel.code.value,
				currencyName = domainModel.name.value,
				rates =
					domainModel
						.rates
						.map { rate ->
							mapToRateUiModel(
								date = rate.date,
								currentRate = rate.value.value,
								averageRate = averageRate,
							)
						}.toImmutableList(),
			)
		}

		@VisibleForTesting
		internal fun mapToRateUiModel(
			date: LocalDate,
			currentRate: Double,
			averageRate: Double,
		): CurrencyDetailsRateUiModel {
			val currentRateBigDecimal = currentRate.toBigDecimal()
			val averageRateBigDecimal = averageRate.toBigDecimal().setScale(SCALE, RoundingMode.FLOOR)
			val diffBigDecimal = (currentRateBigDecimal - averageRateBigDecimal).abs()
			val diffStatus =
				if (diffBigDecimal >
					averageRateBigDecimal
						.multiply(BigDecimal(CALCULATION_VALUE))
						.setScale(SCALE, RoundingMode.FLOOR)
				) {
					RateDiffStatusUiModel.Significant
				} else {
					RateDiffStatusUiModel.Normal
				}
			return CurrencyDetailsRateUiModel(
				date = date.format(dateFormatter),
				value = currentRate,
				diffStatus = diffStatus,
			)
		}
	}
