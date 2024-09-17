package io.bratexsoft.currencywatcher.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import io.bratexsoft.currencywatcher.core.ui.theme.CurrencyWatcherTheme
import io.bratexsoft.currencywatcher.feature.navigation.CurrencyExchangeNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			CurrencyWatcherTheme {
				CurrencyExchangeNavigation()
			}
		}
	}
}
