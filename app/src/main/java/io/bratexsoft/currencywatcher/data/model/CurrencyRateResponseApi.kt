package io.bratexsoft.currencywatcher.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyRateResponseApi(
	@SerializedName("currency") val currency: String?,
	@SerializedName("code") val code: String?,
	@SerializedName("mid") val mid: Double?,
)
