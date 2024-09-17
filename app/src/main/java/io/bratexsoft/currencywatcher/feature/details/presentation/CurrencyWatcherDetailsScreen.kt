package io.bratexsoft.currencywatcher.feature.details.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.bratexsoft.currencywatcher.feature.details.model.CurrencyDetailsRateUiModel
import io.bratexsoft.currencywatcher.feature.details.model.CurrencyDetailsUiModel
import io.bratexsoft.currencywatcher.feature.details.model.RateDiffStatusUiModel
import kotlinx.collections.immutable.ImmutableList
import java.util.Locale

@Composable
fun CurrencyWatcherDetailsScreen(
	details: CurrencyDetailsUiModel,
	onBackClick: () -> Unit,
) {
	Scaffold(
		topBar = { CurrencyWatcherDetailsTopBar(onBackClick) },
		content = {
			CurrencyDetailsContent(
				modifier = Modifier.padding(it),
				details = details,
			)
		},
	)
}

@Composable
private fun CurrencyDetailsContent(
	modifier: Modifier = Modifier,
	details: CurrencyDetailsUiModel,
) {
	Column(
		modifier =
		modifier
			.fillMaxSize()
			.padding(16.dp),
	) {
		Text(
			text = details.currencyCode,
			style = MaterialTheme.typography.headlineMedium,
			fontWeight = FontWeight.Bold,
		)
		Text(
			text = details.currencyName.uppercase(),
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.secondary,
		)
		Spacer(modifier = Modifier.height(16.dp))
		RatesList(rates = details.rates)
	}
}

@Composable
private fun RatesList(rates: ImmutableList<CurrencyDetailsRateUiModel>) {
	LazyColumn {
		items(rates) { rate ->
			RateItem(
				rate = rate,
			)
		}
	}
}

@Composable
private fun RateItem(rate: CurrencyDetailsRateUiModel) {
	val textColor =
		if (rate.diffStatus == RateDiffStatusUiModel.Significant) Color.Red else Color.Unspecified

	Row(
		modifier =
		Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
	) {
		Text(text = rate.date)
		Text(
			text = String.format(Locale.getDefault(), "%.4f", rate.value),
			color = textColor,
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyWatcherDetailsTopBar(onBackClick: () -> Unit) {
	TopAppBar(
		navigationIcon = {
			IconButton(
				onClick = onBackClick,
			) {
				Icon(
					imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
					contentDescription = "Back to previous screen",
				)
			}
		},
		title = { Text(text = "Details screen") },
	)
}
