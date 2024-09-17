package io.bratexsoft.currencywatcher.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponseApi(
	@SerializedName("table") val table: String?,
	@SerializedName("no") val no: String?,
	@SerializedName("effectiveDate") val effectiveDate: String?,
	@SerializedName("rates") val rates: List<CurrencyRateResponseApi>?,
)
