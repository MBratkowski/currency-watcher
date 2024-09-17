package io.bratexsoft.currencywatcher.mapper

import io.bratexsoft.currencywatcher.domain.model.CurrencyDetailsDomain
import io.bratexsoft.currencywatcher.domain.model.RateDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyCodeDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyNameDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.ExchangeRateDomain
import io.bratexsoft.currencywatcher.feature.details.mapper.CurrencyDetailsUiMapper
import io.bratexsoft.currencywatcher.feature.details.model.RateDiffStatusUiModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.collections.immutable.persistentListOf
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class CurrencyDetailsUiMapperTest {
	private lateinit var mapper: CurrencyDetailsUiMapper

	@Before
	fun setup() {
		mapper = CurrencyDetailsUiMapper()
	}

	@Test
	fun `mapToUiModel correctly maps domain model to UI model`() {
		val domainModel =
			CurrencyDetailsDomain(
				code = CurrencyCodeDomain("USD"),
				name = CurrencyNameDomain("US Dollar"),
				rates =
					persistentListOf(
						RateDomain(LocalDate.of(2023, 1, 1), ExchangeRateDomain(1.0)),
						RateDomain(LocalDate.of(2023, 1, 2), ExchangeRateDomain(1.1)),
						RateDomain(LocalDate.of(2023, 1, 3), ExchangeRateDomain(1.2)),
					),
			)

		val result = mapper.mapToUiModel(domainModel)

		assertEquals("USD", result.currencyCode)
		assertEquals("US Dollar", result.currencyName)
		assertEquals(3, result.rates.size)

		assertEquals("2023-01-01", result.rates[0].date)
		assertEquals(1.0, result.rates[0].value)
		assertEquals("2023-01-02", result.rates[1].date)
		assertEquals(1.1, result.rates[1].value)
		assertEquals("2023-01-03", result.rates[2].date)
		assertEquals(1.2, result.rates[2].value)
	}

	@Test
	fun `mapToRateUiModel correctly determines RateDiffStatusUiModel_Normal`() {
		val date = LocalDate.of(2023, 1, 1)
		val currentRate = 2.5983
		val averageRate = 2.5983

		val result =
			mapper.mapToRateUiModel(
				date = date,
				currentRate = currentRate,
				averageRate = averageRate,
			)

		assertEquals(RateDiffStatusUiModel.Normal, result.diffStatus)
	}

	@Test
	fun `mapToRateUiModel correctly determines RateDiffStatusUiModel_Significant for higher value`() {
		val date = LocalDate.of(2023, 1, 1)
		val currentRate = 2.8582 // Just above 10% higher than average
		val averageRate = 2.5983

		val result =
			mapper.mapToRateUiModel(
				date = date,
				currentRate = currentRate,
				averageRate = averageRate,
			)

		assertEquals(RateDiffStatusUiModel.Significant, result.diffStatus)
	}

	@Test
	fun `mapToRateUiModel correctly determines RateDiffStatusUiModel_Significant for lower value`() {
		val date = LocalDate.of(2023, 1, 1)
		val currentRate = 2.3384
		val averageRate = 2.5983

		val result =
			mapper.mapToRateUiModel(
				date = date,
				currentRate = currentRate,
				averageRate = averageRate,
			)

		assertEquals(RateDiffStatusUiModel.Significant, result.diffStatus)
	}

	@Test
	fun `mapToRateUiModel correctly determines RateDiffStatusUiModel_Normal for edge case`() {
		val date = LocalDate.of(2023, 1, 1)
		val currentRate = 2.8581
		val averageRate = 2.5983

		val result =
			mapper.mapToRateUiModel(
				date = date,
				currentRate = currentRate,
				averageRate = averageRate,
			)

		assertEquals(RateDiffStatusUiModel.Normal, result.diffStatus)
	}

	@Test
	fun `mapToUiModel correctly handles empty rates list`() {
		val domainModel =
			CurrencyDetailsDomain(
				code = CurrencyCodeDomain("USD"),
				name = CurrencyNameDomain("US Dollar"),
				rates = persistentListOf(),
			)

		val result = mapper.mapToUiModel(domainModel)

		assertEquals("USD", result.currencyCode)
		assertEquals("US Dollar", result.currencyName)
		assertTrue(result.rates.isEmpty())
	}
}
