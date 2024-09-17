package io.bratexsoft.currencywatcher.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyDetailsRateResponseApi(
	@SerializedName("no") val no: String?,
	@SerializedName("effectiveDate") val effectiveDate: String?,
	@SerializedName("mid") val mid: Double?,
)
