package io.bratexsoft.currencywatcher.data.api

import io.bratexsoft.currencywatcher.data.model.CurrencyDetailsResponseApi
import io.bratexsoft.currencywatcher.data.model.CurrencyResponseApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

private const val API_URL = "https://api.nbp.pl/"

@Singleton
class CurrencyExchangeApiClient
	@Inject
	constructor() : CurrencyExchangeApi {
		private val retrofit =
			Retrofit
				.Builder()
				.baseUrl(API_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build()

		private val apiService = retrofit.create(CurrencyExchangeApiService::class.java)

		override suspend fun getCurrencyRates(): List<CurrencyResponseApi> = apiService.getCurrencyRates()

		override suspend fun getCurrencyDetails(
			code: String,
			startDate: LocalDate,
			endDate: LocalDate,
		): CurrencyDetailsResponseApi =
			apiService.getCurrencyDetails(
				code = code,
				startDate = startDate.toString(),
				endDate = endDate.toString(),
			)
	}
