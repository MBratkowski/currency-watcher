package io.bratexsoft.currencywatcher.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bratexsoft.currencywatcher.data.api.CurrencyExchangeApi
import io.bratexsoft.currencywatcher.data.api.CurrencyExchangeApiClient
import io.bratexsoft.currencywatcher.data.repository.CurrencyRepositoryImpl
import io.bratexsoft.currencywatcher.domain.repository.CurrencyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	@Provides
	@Singleton
	fun provideCurrencyExchangeApiService(currencyExchangeApiClient: CurrencyExchangeApiClient): CurrencyExchangeApi =
		currencyExchangeApiClient

	@Provides
	@Singleton
	fun provideCurrencyRepository(currencyRepository: CurrencyRepositoryImpl): CurrencyRepository = currencyRepository
}
