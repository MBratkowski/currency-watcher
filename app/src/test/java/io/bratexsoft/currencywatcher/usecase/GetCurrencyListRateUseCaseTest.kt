package io.bratexsoft.currencywatcher.usecase

import io.bratexsoft.currencywatcher.domain.model.CurrencyExchangeRateDomain
import io.bratexsoft.currencywatcher.domain.repository.CurrencyRepository
import io.bratexsoft.currencywatcher.domain.usecase.GetCurrencyListRateUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetCurrencyListRateUseCaseTest {

	private lateinit var mockCurrencyRepository: CurrencyRepository
	private lateinit var subject: GetCurrencyListRateUseCase

	@Before
	fun setup() {
		mockCurrencyRepository = mockk()
		subject = GetCurrencyListRateUseCase(mockCurrencyRepository)
	}

	@Test
	fun `invoke returns success result when repository returns success`() = runBlocking {
		val mockRates = listOf(
			mockk<CurrencyExchangeRateDomain>(),
			mockk<CurrencyExchangeRateDomain>()
		)
		val mockResult = Result.success(mockRates)

		coEvery { mockCurrencyRepository.getCurrencyRates() } returns mockResult

		val result = subject()

		assertTrue(result.isSuccess)
		assertEquals(mockRates, result.getOrNull())
		coVerify(exactly = 1) { mockCurrencyRepository.getCurrencyRates() }
	}

	@Test
	fun `invoke returns failure result when repository returns failure`() = runBlocking {
		val mockException = Exception("Test exception")
		val mockResult = Result.failure<List<CurrencyExchangeRateDomain>>(mockException)

		coEvery { mockCurrencyRepository.getCurrencyRates() } returns mockResult

		val result = subject()

		assertFalse(result.isSuccess)
		assertEquals(mockException, result.exceptionOrNull())
		coVerify(exactly = 1) { mockCurrencyRepository.getCurrencyRates() }
	}

	@Test
	fun `invoke returns empty list when repository returns empty list`() = runBlocking {
		val mockResult = Result.success(emptyList<CurrencyExchangeRateDomain>())

		coEvery { mockCurrencyRepository.getCurrencyRates() } returns mockResult

		val result = subject()

		assertTrue(result.isSuccess)
		assertTrue(result.getOrNull()?.isEmpty() == true)
		coVerify(exactly = 1) { mockCurrencyRepository.getCurrencyRates() }
	}
}
