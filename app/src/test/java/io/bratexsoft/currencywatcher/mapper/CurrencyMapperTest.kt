package io.bratexsoft.currencywatcher.mapper

import io.bratexsoft.currencywatcher.data.mapper.CurrencyMapper
import io.bratexsoft.currencywatcher.data.model.CurrencyDetailsRateResponseApi
import io.bratexsoft.currencywatcher.data.model.CurrencyDetailsResponseApi
import io.bratexsoft.currencywatcher.data.model.CurrencyRateResponseApi
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyCodeDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyNameDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.ExchangeRateDomain
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.collections.immutable.ImmutableList
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class CurrencyMapperTest {
	private lateinit var mapper: CurrencyMapper

	@Before
	fun setup() {
		mapper = CurrencyMapper()
	}

	@Test
	fun `mapToDomain should return valid CurrencyExchangeRateDomain when all fields are present`() {
		val input = CurrencyRateResponseApi(currency = "US Dollar", code = "USD", mid = 3.14)
		val result = mapper.mapToDomain(input)
		assertEquals(CurrencyNameDomain("US Dollar"), result?.name)
		assertEquals(CurrencyCodeDomain("USD"), result?.code)
		assertEquals(ExchangeRateDomain(3.14), result?.rate)
	}

	@Test
	fun `mapToDomain should return null when currency is missing in CurrencyRateResponseApi`() {
		val input = CurrencyRateResponseApi(currency = null, code = "USD", mid = 3.14)
		val result = mapper.mapToDomain(input)
		assertNull(result)
	}

	@Test
	fun `mapToDomain should return null when code is missing in CurrencyRateResponseApi`() {
		val input = CurrencyRateResponseApi(currency = "US Dollar", code = null, mid = 3.14)
		val result = mapper.mapToDomain(input)
		assertNull(result)
	}

	@Test
	fun `mapToDomain should return null when mid is missing in CurrencyRateResponseApi`() {
		val input = CurrencyRateResponseApi(currency = "US Dollar", code = "USD", mid = null)
		val result = mapper.mapToDomain(input)
		assertNull(result)
	}

	@Test
	fun `mapToDomain should return valid CurrencyDetailsDomain when all fields are present`() {
		val input =
			CurrencyDetailsResponseApi(
				table = "A",
				currency = "US Dollar",
				code = "USD",
				rates =
					listOf(
						CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-02", mid = 3.15, no = "1"),
						CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-01", mid = 3.14, no = "1"),
					),
			)
		val result = mapper.mapToDomain(input)
		assertEquals(CurrencyNameDomain("US Dollar"), result?.name)
		assertEquals(CurrencyCodeDomain("USD"), result?.code)
		assertTrue(result?.rates is ImmutableList)
		assertEquals(2, result?.rates?.size)
		assertEquals(LocalDate.of(2023, 1, 2), result?.rates?.get(0)?.date)
		assertEquals(ExchangeRateDomain(3.15), result?.rates?.get(0)?.value)
		assertEquals(LocalDate.of(2023, 1, 1), result?.rates?.get(1)?.date)
		assertEquals(ExchangeRateDomain(3.14), result?.rates?.get(1)?.value)
	}

	@Test
	fun `mapToDomain should return null when currency is missing in CurrencyDetailsResponseApi`() {
		val input =
			CurrencyDetailsResponseApi(
				table = "A",
				currency = null,
				code = "USD",
				rates = listOf(CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-01", mid = 3.14, no = "1")),
			)
		val result = mapper.mapToDomain(input)
		assertNull(result)
	}

	@Test
	fun `mapToDomain should return null when code is missing in CurrencyDetailsResponseApi`() {
		val input =
			CurrencyDetailsResponseApi(
				table = "A",
				currency = "US Dollar",
				code = null,
				rates = listOf(CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-01", mid = 3.14, no = "1")),
			)
		val result = mapper.mapToDomain(input)
		assertNull(result)
	}

	@Test
	fun `mapToDomain should return null when rates is missing in CurrencyDetailsResponseApi`() {
		val input =
			CurrencyDetailsResponseApi(
				table = "A",
				currency = "US Dollar",
				code = "USD",
				rates = null,
			)
		val result = mapper.mapToDomain(input)
		assertNull(result)
	}

	@Test
	fun `mapToDomain should skip invalid rates and return valid ones`() {
		val input =
			CurrencyDetailsResponseApi(
				table = "A",
				currency = "US Dollar",
				code = "USD",
				rates =
					listOf(
						CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-02", mid = 3.15, no = "1"),
						CurrencyDetailsRateResponseApi(effectiveDate = null, mid = 3.14, no = "1"),
						CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-01", mid = null, no = "1"),
					),
			)
		val result = mapper.mapToDomain(input)
		assertEquals(1, result?.rates?.size)
		assertEquals(LocalDate.of(2023, 1, 2), result?.rates?.get(0)?.date)
		assertEquals(ExchangeRateDomain(3.15), result?.rates?.get(0)?.value)
	}

	@Test
	fun `mapToDomain should sort rates in descending order`() {
		val input =
			CurrencyDetailsResponseApi(
				table = "A",
				currency = "US Dollar",
				code = "USD",
				rates =
					listOf(
						CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-01", mid = 3.14, no = "1"),
						CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-03", mid = 3.16, no = "1"),
						CurrencyDetailsRateResponseApi(effectiveDate = "2023-01-02", mid = 3.15, no = "1"),
					),
			)
		val result = mapper.mapToDomain(input)
		assertEquals(3, result?.rates?.size)
		assertEquals(LocalDate.of(2023, 1, 3), result?.rates?.get(0)?.date)
		assertEquals(LocalDate.of(2023, 1, 2), result?.rates?.get(1)?.date)
		assertEquals(LocalDate.of(2023, 1, 1), result?.rates?.get(2)?.date)
	}
}
