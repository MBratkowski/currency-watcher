package io.bratexsoft.currencywatcher.presentation

import app.cash.turbine.test
import io.bratexsoft.currencywatcher.domain.model.CurrencyExchangeRateDomain
import io.bratexsoft.currencywatcher.domain.usecase.GetCurrencyListRateUseCase
import io.bratexsoft.currencywatcher.feature.list.presentation.CurrencyWatcherListUiState
import io.bratexsoft.currencywatcher.feature.list.presentation.CurrencyWatcherListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.internal.toImmutableList
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class CurrencyWatcherListViewModelTest {

	private lateinit var subject: CurrencyWatcherListViewModel
	private lateinit var mockGetCurrencyListRateUseCase: GetCurrencyListRateUseCase
	private val testDispatcher = StandardTestDispatcher()

	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		mockGetCurrencyListRateUseCase = mockk()
		coEvery { mockGetCurrencyListRateUseCase() } returns Result.success(emptyList())
		subject = CurrencyWatcherListViewModel(mockGetCurrencyListRateUseCase)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun `fetchCurrencyRates updates state to Success when use case returns success`() = runTest {
		val mockRates = listOf(mockk<CurrencyExchangeRateDomain>(), mockk<CurrencyExchangeRateDomain>())
		coEvery { mockGetCurrencyListRateUseCase() } returns Result.success(mockRates)

		subject = CurrencyWatcherListViewModel(mockGetCurrencyListRateUseCase)

		subject.viewState.test {
			assertEquals(CurrencyWatcherListUiState.Loading, awaitItem())
			val successState = awaitItem()
			assertTrue(successState is CurrencyWatcherListUiState.Success)
			assertEquals(mockRates.toImmutableList(), successState.currencyRates)
		}

		coVerify { mockGetCurrencyListRateUseCase() }
	}

	@Test
	fun `fetchCurrencyRates updates state to Error when use case returns failure`() = runTest {
		coEvery { mockGetCurrencyListRateUseCase() } returns Result.failure(Exception())

		subject = CurrencyWatcherListViewModel(mockGetCurrencyListRateUseCase)

		subject.viewState.test {
			assertEquals(CurrencyWatcherListUiState.Loading, awaitItem())
			val errorState = awaitItem()
			assertTrue(errorState is CurrencyWatcherListUiState.Error)
		}

		coVerify { mockGetCurrencyListRateUseCase() }
	}
}
