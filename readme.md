# Currency Watcher

Currency Watcher is an Android application that allows users to monitor and track currency exchange rates. It provides up-to-date information on various currencies and their historical rates.

## Features

- View a list of current currency exchange rates
- See detailed information for each currency, including a two-week history of rates
- Highlight significant rate changes (more than 10% difference from the average)

## Project Structure

The project follows Clean Architecture principles and MVVM pattern:

- `data`: Contains the data layer (API clients, repositories implementations, mappers)
- `domain`: Contains the domain layer (use cases, repository interfaces, domain models)
- `presentation`: Contains the presentation layer (view models, UI models, composables)

## Technologies Used

- Kotlin
- Jetpack Compose for UI
- Retrofit for API calls
- Hilt for dependency injection
- Kotlin Coroutines and Flow for asynchronous programming
- JUnit and Mockito for unit testing

## Setup

1. Clone the repository:
	 ```
	 git clone https://github.com/yourusername/currency-watcher.git
	 ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.

## Running the App

1. Select an emulator or connect a physical device.
2. Click the "Run" button in Android Studio.

## Running Tests

To run unit tests, execute the following command in the terminal:

```
./gradlew test
```

## API

The app uses the National Bank of Poland's API for currency data:

- Base URL: `https://api.nbp.pl/api/`
- Endpoints used:
	- `/exchangerates/tables/b` (for currency list)
	- `/exchangerates/rates/a/{code}/{startDate}/{endDate}/` (for currency details)

## Key Components

- `CurrencyMapper`: Responsible for mapping API responses to domain models.
- `CurrencyRepository`: Handles data operations and acts as a single source of truth.
- `GetCurrencyRatesUseCase` and `GetCurrencyDetailsUseCase`: Encapsulate business logic.
- `CurrencyWatcherListViewModel` and `CurrencyWatcherDetailsViewModel`: Manage UI state and user interactions.

## Notes

- The project uses BigDecimal for currency calculations to ensure precision.
- Rate differences of more than 10% from the average are highlighted in the UI.
- The app supports offline mode by caching the latest data.
d under the MIT License - see the LICENSE.md file for details.
