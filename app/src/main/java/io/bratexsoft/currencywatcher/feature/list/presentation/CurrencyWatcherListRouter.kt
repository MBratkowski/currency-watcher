package io.bratexsoft.currencywatcher.feature.list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.bratexsoft.currencywatcher.core.ui.components.ErrorMessage
import io.bratexsoft.currencywatcher.core.ui.components.LoadingIndicator

const val CURRENCY_WATCHER_LIST_ROUTE = "currencyWatcherList"

@Composable
fun CurrencyWatcherListRouter(
	viewModel: CurrencyWatcherListViewModel = hiltViewModel(),
	onItemClick: (currencyCode: String) -> Unit,
) {
	val uiState = viewModel.viewState.collectAsState()

	LaunchedEffect(viewModel) {
		viewModel.viewAction.collect {
			when (it) {
				is CurrencyWatcherListUiAction.OpenDetails -> onItemClick(it.currencyCode.value)
			}
		}
	}

	when (val value = uiState.value) {
		is CurrencyWatcherListUiState.Loading -> LoadingIndicator()
		is CurrencyWatcherListUiState.Success ->
			CurrencyWatcherListScreen(
				currencyRatesList = value.currencyRates,
				onItemClick = viewModel::onItemClick,
			)

		is CurrencyWatcherListUiState.Error -> ErrorMessage(message = value.throwable.message ?: "")
	}
}

fun NavGraphBuilder.addCurrencyWatcherListRouter(onItemClick: (currencyCode: String) -> Unit) {
	this.composable(CURRENCY_WATCHER_LIST_ROUTE) {
		CurrencyWatcherListRouter(onItemClick = onItemClick)
	}
}
