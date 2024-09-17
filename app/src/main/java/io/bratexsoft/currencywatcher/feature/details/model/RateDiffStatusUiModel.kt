package io.bratexsoft.currencywatcher.feature.details.model

sealed interface RateDiffStatusUiModel {
	data object Normal : RateDiffStatusUiModel

	data object Significant : RateDiffStatusUiModel
}
