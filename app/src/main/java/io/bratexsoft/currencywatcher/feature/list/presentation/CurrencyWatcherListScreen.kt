package io.bratexsoft.currencywatcher.feature.list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.bratexsoft.currencywatcher.domain.model.CurrencyExchangeRateDomain
import io.bratexsoft.currencywatcher.domain.model.wrapper.CurrencyCodeDomain
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CurrencyWatcherListScreen(
	currencyRatesList: ImmutableList<CurrencyExchangeRateDomain>,
	onItemClick: (currencyCode: CurrencyCodeDomain) -> Unit,
) {
	Scaffold(
		topBar = { CurrencyWatcherListTopBar() },
		content = {
			CurrencyWatcherListContent(
				modifier = Modifier.padding(it),
				currencyRatesList = currencyRatesList,
				onItemClick = onItemClick,
			)
		},
	)
}

@Composable
private fun CurrencyWatcherListContent(
	modifier: Modifier = Modifier,
	currencyRatesList: ImmutableList<CurrencyExchangeRateDomain>,
	onItemClick: (currencyCode: CurrencyCodeDomain) -> Unit,
) {
	LazyColumn(modifier = modifier) {
		items(
			key = { currencyItem -> currencyItem.code.value },
			items = currencyRatesList,
		) { currencyRate ->
			CurrencyWatcherListItem(
				currencyExchangeRateDomain = currencyRate,
				onItemClick = onItemClick,
			)
		}
	}
}

@Composable
private fun CurrencyWatcherListItem(
	currencyExchangeRateDomain: CurrencyExchangeRateDomain,
	onItemClick: (currencyCode: CurrencyCodeDomain) -> Unit,
) {
	Card(
		modifier =
			Modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp, horizontal = 16.dp)
				.clickable(onClick = { onItemClick(currencyExchangeRateDomain.code) }),
		elevation =
			CardDefaults.cardElevation(
				defaultElevation = 2.dp,
				pressedElevation = 8.dp,
			),
		shape = RoundedCornerShape(12.dp),
	) {
		Row(
			modifier =
				Modifier
					.padding(16.dp)
					.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			CurrencyInfo(
				code = currencyExchangeRateDomain.code.value,
				currency = currencyExchangeRateDomain.name.value,
				modifier = Modifier.weight(1f),
			)
			Spacer(modifier = Modifier.width(8.dp))
			ExchangeRateDisplay(rate = currencyExchangeRateDomain.rate.value)
		}
	}
}

@Composable
private fun CurrencyInfo(
	code: String,
	currency: String,
	modifier: Modifier = Modifier,
) {
	val upperCaseCurrency = remember(currency) { currency.uppercase() }

	Column(modifier = modifier) {
		Text(
			text = code,
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.primary,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
		)
		Text(
			text = upperCaseCurrency,
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
		)
	}
}

@Composable
private fun ExchangeRateDisplay(rate: Double) {
	val formattedRate = remember(rate) { "%.4f".format(rate) }
	Text(
		text = formattedRate,
		style = MaterialTheme.typography.titleMedium,
		fontWeight = FontWeight.SemiBold,
		textAlign = TextAlign.End,
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyWatcherListTopBar() {
	TopAppBar(
		title = { Text(text = "Currency Watcher") },
	)
}
