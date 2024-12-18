package com.breera.feature_home.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.breera.feature_home.R
import com.breera.feature_home.domain.model.CityWeatherModel
import com.breera.feature_home.presentation.component.CitySearchBar
import com.breera.feature_home.presentation.component.SearchedCityCard
import com.breera.feature_home.presentation.component.WeatherInfoCardComposable
import com.breera.theme.theme.WeatherAppTheme
import ir.kaaveh.sdpcompose.sdp
import org.koin.compose.viewmodel.koinViewModel

/**
 * Composable function to display the root of the City Weather Screen.
 * It sets up the theme and collects the state from the ViewModel.
 *
 * @param padding Modifier for setting padding.
 * @param viewModel The ViewModel instance providing the state and actions.
 */
@Composable
fun CityWeatherScreenRoot(
    padding: Modifier,
    viewModel: CityWeatherVM = koinViewModel()
) {
    WeatherAppTheme {
        val state by viewModel.state.collectAsStateWithLifecycle()
        CityWeatherScreen(
            state = state,
            onAction = {
                viewModel.onAction(it)
            }
        )
    }
}

/**
 * Composable function to display the City Weather Screen.
 * It handles different screen states and user interactions.
 *
 * @param state The current state of the screen.
 * @param onAction Callback for handling user actions.
 */
@Composable
fun CityWeatherScreen(state: CityWeatherStates, onAction: (CityWeatherAction) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
    ) {
        CitySearchBar(
            query = state.searchQuery,
            onQueryChange = {
                onAction(CityWeatherAction.OnSearchQueryChange(it))
                keyboardController?.hide()
            },
            onItemSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp)
        )
        Box(Modifier.weight(1f)) {
            when (state.screenState) {
                ScreenState.DEFAULT -> DefaultComposable()
                ScreenState.LOADING -> LoadingComposable()
                ScreenState.ERROR -> ErrorComposable(state.errorMessage)
                ScreenState.WEATHER_INFO_CARD -> SearchedCityCard(state.searchResult) {
                    onAction.invoke(CityWeatherAction.OnDetailWeatherRequest(state.searchResult))
                }

                ScreenState.WEATHER_DETAIL -> WeatherDetailComposable(state.searchResult)
            }
        }
    }
}

/**
 * Composable function to display detailed weather information.
 * It uses a ConstraintLayout to organize the UI components.
 *
 * @param searchResult The CityWeatherModel containing weather details.
 */
@Composable
fun WeatherDetailComposable(searchResult: CityWeatherModel) {
    val constraints = ConstraintSet {
        val image = createRefFor("image")
        val tvCityName = createRefFor("tvCityName")
        val tvTemperature = createRefFor("tvTemperature")
        val infoCard = createRefFor("infoCard")

        constrain(image) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(tvCityName) {
            top.linkTo(image.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(tvTemperature) {
            top.linkTo(tvCityName.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(infoCard) {
            top.linkTo(tvTemperature.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.sdp, vertical = 50.sdp),
        constraintSet = constraints
    ) {
        var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
        val painter = rememberAsyncImagePainter(
            model = searchResult.iconUrl,
            onSuccess = {
                imageLoadResult =
                    if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                        Result.success(it.painter)
                    } else {
                        Result.failure(Exception("Invalid image size"))
                    }
            },
            onError = {
                it.result.throwable.printStackTrace()
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )
        Box(
            modifier = Modifier
                .layoutId("image")
                .size(100.sdp),
            contentAlignment = Alignment.Center
        ) {
            when (val result = imageLoadResult) {
                null -> CircularProgressIndicator(color = Color.Black)
                else -> Image(
                    painter = if (result.isSuccess) painter else painterResource(R.drawable.baseline_sunny_24),
                    contentDescription = searchResult.cityName,
                    contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            modifier = Modifier
                .layoutId("tvCityName")
                .padding(top = 10.sdp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = searchResult.cityName ?: "",
                style = MaterialTheme.typography.headlineLarge
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow),
                contentDescription = "icon",
                tint = Color.Black,
                modifier = Modifier.padding(start = 5.sdp)
            )
        }

        Text(
            text = "${searchResult.temperature ?: ""}\u00B0",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .layoutId("tvTemperature")
                .padding(top = 10.sdp)
        )

        WeatherInfoCardComposable(
            modifier = Modifier
                .layoutId("infoCard")
                .padding(top = 10.sdp),
            humidityPercentage = searchResult.humidity,
            uvIndex = searchResult.ultraViolet,
            feelsLikeTemperature = searchResult.temperatureFeelsLike
        )
    }
}

/**
 * Composable function to display an error message.
 *
 * @param errorMessage The error message to display.
 */
@Composable
fun ErrorComposable(errorMessage: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.sdp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = errorMessage ?: run { stringResource(R.string.no_city_found) },
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = errorMessage?.let { stringResource(R.string.hint_error) }
                ?: run { stringResource(R.string.hint) },
            style = MaterialTheme.typography.labelSmall
        )
    }
}

/**
 * Composable function to display a loading indicator.
 */
@Composable
fun LoadingComposable() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}

/**
 * Composable function to display the default screen state.
 */
@Preview
@Composable
fun DefaultComposable() {
    WeatherAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.no_city_lable),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(top = 8.sdp),
                text = stringResource(R.string.please_select_city),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

/**
 * Preview function for the WeatherDetailComposable.
 */
@Preview
@Composable
fun WeatherDetailComposablePreview() {
    WeatherAppTheme {
        WeatherDetailComposable(
            searchResult = CityWeatherModel(
                iconUrl = null,
                cityName = "Lahore",
                temperature = "38",
                humidity = "3",
                ultraViolet = "4",
                temperatureFeelsLike = "30"
            )
        )
    }
}