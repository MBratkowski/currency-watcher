plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.dagger.hilt)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.ktlint)
	alias(libs.plugins.detekt)
}

android {
	namespace = "io.bratexsoft.currencywatcher"
	compileSdk = 34

	defaultConfig {
		applicationId = "io.bratexsoft.currencywatcher"
		minSdk = 33
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	detekt {
		toolVersion = "1.23.4"
		config.setFrom(file("../config/detekt/detekt.yml"))
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro",
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.15"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}

}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(
		libs
			.androidx
			.lifecycle
			.runtime
			.ktx,
	)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(
		libs
			.androidx
			.ui
			.tooling
			.preview,
	)
	implementation(libs.androidx.compose.navigation)
	implementation(libs.androidx.material3)
	implementation(libs.gson)
	implementation(libs.dagger.hilt.android)
	implementation(libs.androidx.hilt.navigation)
	implementation(libs.retrofit2.core)
	implementation(libs.retrofit2.converter.gson)
	implementation(libs.okhttp3.core)
	implementation(libs.kotlin.immutable.collections)
	implementation(libs.timber)
	testImplementation(libs.jupiter)
	testImplementation(libs.kotest)
	ksp(
		libs
			.dagger
			.hilt
			.android
			.compiler,
	)
	testImplementation(libs.junit)
	testImplementation(libs.mockk)
	testImplementation(libs.kotlin.test)
	testImplementation(libs.kotlin.coroutines.test)
	testImplementation(libs.turbine)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(
		libs
			.androidx
			.ui
			.test
			.junit4,
	)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(
		libs
			.androidx
			.ui
			.test
			.manifest,
	)
}
