package io.bratexsoft.currencywatcher.feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.bratexsoft.currencywatcher.feature.details.presentation.CURRENCY_WATCHER_DETAILS_ROUTE
import io.bratexsoft.currencywatcher.feature.details.presentation.addCurrencyWatcherDetailsRouter
import io.bratexsoft.currencywatcher.feature.list.presentation.CURRENCY_WATCHER_LIST_ROUTE
import io.bratexsoft.currencywatcher.feature.list.presentation.addCurrencyWatcherListRouter

@Composable
fun CurrencyExchangeNavigation() {
	val navController = rememberNavController()
	NavHost(
		navController = navController,
		startDestination = CURRENCY_WATCHER_LIST_ROUTE,
	) {
		this.addCurrencyWatcherListRouter(
			onItemClick = { currencyCode ->
				navController.navigate(route = "$CURRENCY_WATCHER_DETAILS_ROUTE/$currencyCode")
			},
		)
		this.addCurrencyWatcherDetailsRouter(
			onBackClick = navController::popBackStack,
		)
	}
}
