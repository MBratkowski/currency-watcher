package io.bratexsoft.currencywatcher.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyDetailsResponseApi(
	@SerializedName("table") val table: String?,
	@SerializedName("currency") val currency: String?,
	@SerializedName("code") val code: String?,
	@SerializedName("rates") val rates: List<CurrencyDetailsRateResponseApi>?,
)
