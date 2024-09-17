package io.bratexsoft.currencywatcher.data.api

import io.bratexsoft.currencywatcher.data.model.CurrencyDetailsResponseApi
import io.bratexsoft.currencywatcher.data.model.CurrencyResponseApi
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyExchangeApiService {
	@GET("api/exchangerates/tables/a")
	suspend fun getCurrencyRates(): List<CurrencyResponseApi>

	@GET("api/exchangerates/rates/a/{code}/{startDate}/{endDate}/")
	suspend fun getCurrencyDetails(
		@Path("code") code: String,
		@Path("startDate") startDate: String,
		@Path("endDate") endDate: String,
	): CurrencyDetailsResponseApi
}
