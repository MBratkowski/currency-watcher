package io.bratexsoft.currencywatcher.feature.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bratexsoft.currencywatcher.domain.usecase.GetCurrencyDetailsUseCase
import io.bratexsoft.currencywatcher.feature.details.mapper.CurrencyDetailsUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyWatcherDetailsViewModel @Inject constructor(
	private val getCurrencyDetailsUseCase: GetCurrencyDetailsUseCase,
	private val mapper: CurrencyDetailsUiMapper,
) : ViewModel() {
	private val _viewState =
		MutableStateFlow<CurrencyDetailsUiState>(CurrencyDetailsUiState.Loading)
	val viewState: StateFlow<CurrencyDetailsUiState> = _viewState

	fun loadCurrencyDetails(currencyCode: String) {
		viewModelScope.launch {
			getCurrencyDetailsUseCase(currencyCode).fold(
				onSuccess = {
					_viewState.value = CurrencyDetailsUiState.Success(mapper.mapToUiModel(it))
				},
				onFailure = {
					_viewState.value = CurrencyDetailsUiState.Error(throwable = it)
				},
			)
		}
	}
}
