package io.bratexsoft.currencywatcher.feature.details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.bratexsoft.currencywatcher.core.ui.components.ErrorMessage
import io.bratexsoft.currencywatcher.core.ui.components.LoadingIndicator

const val CURRENCY_WATCHER_DETAILS_ROUTE = "currencyWatcherDetails"

@Composable
fun CurrencyWatcherDetailsRouter(
	viewModel: CurrencyWatcherDetailsViewModel = hiltViewModel(),
	currencyCode: String,
	onBackClick: () -> Unit,
) {
	val uiState = viewModel.viewState.collectAsState()

	LaunchedEffect(currencyCode) {
		viewModel.loadCurrencyDetails(currencyCode)
	}

	when (val state = uiState.value) {
		is CurrencyDetailsUiState.Loading -> LoadingIndicator()
		is CurrencyDetailsUiState.Success ->
			CurrencyWatcherDetailsScreen(
				details = state.details,
				onBackClick = onBackClick,
			)

		is CurrencyDetailsUiState.Error -> ErrorMessage(message = state.throwable.message ?: "")
	}
}

fun NavGraphBuilder.addCurrencyWatcherDetailsRouter(onBackClick: () -> Unit) {
	composable(
		route = "$CURRENCY_WATCHER_DETAILS_ROUTE/{currencyCode}",
		arguments = listOf(navArgument("currencyCode") { type = NavType.StringType }),
	) { backStackEntry ->
		val currencyCode = backStackEntry.arguments?.getString("currencyCode") ?: ""
		CurrencyWatcherDetailsRouter(
			currencyCode = currencyCode,
			onBackClick = onBackClick,
		)
	}
}
