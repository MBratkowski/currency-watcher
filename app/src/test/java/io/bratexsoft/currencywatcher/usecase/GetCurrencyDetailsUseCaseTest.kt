package io.bratexsoft.currencywatcher.usecase

import io.bratexsoft.currencywatcher.domain.model.CurrencyDetailsDomain
import io.bratexsoft.currencywatcher.domain.repository.CurrencyRepository
import io.bratexsoft.currencywatcher.domain.usecase.GetCurrencyDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetCurrencyDetailsUseCaseTest {

	private lateinit var currencyRepository: CurrencyRepository
	private lateinit var subject: GetCurrencyDetailsUseCase

	@Before
	fun setup() {
		currencyRepository = mockk()
		subject = GetCurrencyDetailsUseCase(currencyRepository)
	}

	@Test
	fun `invoke returns success result when repository returns success`() = runBlocking {
		val code = "USD"
		val mockDomain = mockk<CurrencyDetailsDomain>()
		val mockResult = Result.success(mockDomain)

		coEvery {
			currencyRepository.getCurrencyDetails(
				code = any(),
				startDate = any(),
				endDate = any()
			)
		} returns mockResult

		val result = subject(code)

		assertTrue(result.isSuccess)
		assertEquals(mockDomain, result.getOrNull())
	}

	@Test
	fun `invoke returns failure result when repository returns failure`() = runBlocking {
		val code = "USD"
		val mockException = Exception("Test exception")
		val mockResult = Result.failure<CurrencyDetailsDomain>(mockException)

		coEvery {
			currencyRepository.getCurrencyDetails(
				code = any(),
				startDate = any(),
				endDate = any()
			)
		} returns mockResult

		val result = subject(code)

		assertFalse(result.isSuccess)
		assertEquals(mockException, result.exceptionOrNull())
	}

	@Test
	fun `invoke calculates correct date range`() = runBlocking {
		val code = "USD"
		val startDateSlot = slot<LocalDate>()
		val endDateSlot = slot<LocalDate>()

		coEvery {
			currencyRepository.getCurrencyDetails(
				code = code,
				startDate = capture(startDateSlot),
				endDate = capture(endDateSlot)
			)
		} returns Result.success(mockk())

		subject(code)

		val capturedStartDate = startDateSlot.captured
		val capturedEndDate = endDateSlot.captured
		assertEquals(14, ChronoUnit.DAYS.between(capturedStartDate, capturedEndDate))
	}

	@Test
	fun `invoke handles leap years correctly`() = runBlocking {
		val code = "USD"
		val startDateSlot = slot<LocalDate>()
		val endDateSlot = slot<LocalDate>()

		coEvery {
			currencyRepository.getCurrencyDetails(
				code = code,
				startDate = capture(startDateSlot),
				endDate = capture(endDateSlot)
			)
		} returns Result.success(mockk())

		subject(code)

		val capturedStartDate = startDateSlot.captured
		val capturedEndDate = endDateSlot.captured
		assertEquals(14, ChronoUnit.DAYS.between(capturedStartDate, capturedEndDate))
		assertEquals(capturedStartDate.isLeapYear, capturedEndDate.isLeapYear)
	}
}
