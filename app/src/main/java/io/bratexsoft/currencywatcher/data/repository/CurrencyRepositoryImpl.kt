package io.bratexsoft.currencywatcher.data.repository

import io.bratexsoft.currencywatcher.data.api.CurrencyExchangeApiClient
import io.bratexsoft.currencywatcher.data.mapper.CurrencyMapper
import io.bratexsoft.currencywatcher.domain.model.CurrencyDetailsDomain
import io.bratexsoft.currencywatcher.domain.model.CurrencyExchangeRateDomain
import io.bratexsoft.currencywatcher.domain.repository.CurrencyRepository
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl
	@Inject
	constructor(
		private val currencyApi: CurrencyExchangeApiClient,
		private val currencyMapper: CurrencyMapper,
	) : CurrencyRepository {
		override suspend fun getCurrencyRates(): Result<List<CurrencyExchangeRateDomain>> =
			runCatching {
				val response = currencyApi.getCurrencyRates()
				response.flatMap { it.rates?.mapNotNull(currencyMapper::mapToDomain) ?: emptyList() }
			}.recoverCatching { exception ->
				throw mapToCurrencyError(exception)
			}

		override suspend fun getCurrencyDetails(
			code: String,
			startDate: LocalDate,
			endDate: LocalDate,
		): Result<CurrencyDetailsDomain> =
			runCatching {
				val response = currencyApi.getCurrencyDetails(code, startDate, endDate)
				currencyMapper.mapToDomain(response) ?: throw CurrencyError.DataMappingError
			}.recoverCatching { exception ->
				throw mapToCurrencyError(exception)
			}

		private fun mapToCurrencyError(exception: Throwable): CurrencyError =
			when (exception) {
				is retrofit2.HttpException -> CurrencyError.NetworkError(exception.code())
				is java.net.UnknownHostException -> CurrencyError.NoInternetConnection
				is java.net.SocketTimeoutException -> CurrencyError.TimeoutError
				is CurrencyError -> exception
				else -> CurrencyError.UnknownError(exception)
			}
	}

sealed class CurrencyError : Exception() {
	data object NoInternetConnection : CurrencyError()

	data object TimeoutError : CurrencyError()

	data object DataMappingError : CurrencyError()

	data class NetworkError(
		val code: Int,
	) : CurrencyError()

	data class UnknownError(
		val originalException: Throwable,
	) : CurrencyError()
}
