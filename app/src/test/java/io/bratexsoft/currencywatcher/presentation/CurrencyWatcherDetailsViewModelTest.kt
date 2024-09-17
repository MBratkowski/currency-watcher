package io.bratexsoft.currencywatcher.presentation

import app.cash.turbine.test
import io.bratexsoft.currencywatcher.domain.model.CurrencyDetailsDomain
import io.bratexsoft.currencywatcher.domain.usecase.GetCurrencyDetailsUseCase
import io.bratexsoft.currencywatcher.feature.details.mapper.CurrencyDetailsUiMapper
import io.bratexsoft.currencywatcher.feature.details.model.CurrencyDetailsUiModel
import io.bratexsoft.currencywatcher.feature.details.presentation.CurrencyDetailsUiState
import io.bratexsoft.currencywatcher.feature.details.presentation.CurrencyWatcherDetailsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class CurrencyWatcherDetailsViewModelTest {

	private lateinit var subject: CurrencyWatcherDetailsViewModel
	private lateinit var mockGetCurrencyDetailsUseCase: GetCurrencyDetailsUseCase
	private lateinit var mockMapper: CurrencyDetailsUiMapper
	private val testDispatcher = StandardTestDispatcher()

	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		mockGetCurrencyDetailsUseCase = mockk()
		mockMapper = mockk()
		subject = CurrencyWatcherDetailsViewModel(mockGetCurrencyDetailsUseCase, mockMapper)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun `initial state is Loading`() = runBlockingTest {
		subject.viewState.test {
			assertEquals(CurrencyDetailsUiState.Loading, awaitItem())
		}
	}

	@Test
	fun `loadCurrencyDetails updates state to Success when use case returns success`() = runTest {
		val currencyCode = "USD"
		val domainModel = mockk<CurrencyDetailsDomain>()
		val uiModel = mockk<CurrencyDetailsUiModel>()
		coEvery { mockGetCurrencyDetailsUseCase(currencyCode) } returns Result.success(domainModel)
		every { mockMapper.mapToUiModel(domainModel) } returns uiModel

		subject.loadCurrencyDetails(currencyCode)

		subject.viewState.test {
			assertEquals(CurrencyDetailsUiState.Loading, awaitItem())
			assertEquals(CurrencyDetailsUiState.Success(uiModel), awaitItem())
		}

		coVerify { mockGetCurrencyDetailsUseCase(currencyCode) }
		verify { mockMapper.mapToUiModel(domainModel) }
	}

	@Test
	fun `loadCurrencyDetails updates state to Error when use case returns failure`() = runTest {
		val currencyCode = "USD"
		val mockException = mockk<Exception>()
		coEvery { mockGetCurrencyDetailsUseCase(currencyCode) } returns Result.failure(mockException)

		subject.loadCurrencyDetails(currencyCode)

		subject.viewState.test {
			assertEquals(CurrencyDetailsUiState.Loading, awaitItem())
			assertEquals(CurrencyDetailsUiState.Error(mockException), awaitItem())
		}

		coVerify { mockGetCurrencyDetailsUseCase(currencyCode) }
		verify(exactly = 0) { mockMapper.mapToUiModel(any()) }
	}
}
