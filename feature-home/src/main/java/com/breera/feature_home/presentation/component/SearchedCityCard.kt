package com.breera.feature_home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.breera.core.presentation.LoadImage
import com.breera.feature_home.R
import com.breera.feature_home.domain.model.CityWeatherModel
import com.breera.theme.theme.WeatherAppTheme
import ir.kaaveh.sdpcompose.sdp

/**
 *  A card component to display weather information for a searched city.
 *
 *  @param cityWeatherModel A model containing the city's weather data.
 */

@Composable
fun SearchedCityCard(
    cityWeatherModel: CityWeatherModel,
    onCardClick: () -> Unit = {}
) {
    WeatherAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.sdp)
                .clickable {
                    onCardClick()
                },
            shape = RoundedCornerShape(10.sdp),
            color = MaterialTheme.colorScheme.onSurface,
            // tonalElevation = 4.sdp
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp)
                    .background(MaterialTheme.colorScheme.onSurface)
            ) {
                // Define constraints
                val (cityText, temperatureText, temperatureSign, weatherIconContainer) = createRefs()

                // City Name Textview
                Text(
                    text = cityWeatherModel.cityName ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .constrainAs(cityText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(temperatureText.top)
                        }
                )

                // Temperature Value
                Text(
                    text = cityWeatherModel.temperature ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .constrainAs(temperatureText) {
                            top.linkTo(cityText.bottom)
                            start.linkTo(cityText.start)
                            bottom.linkTo(parent.bottom)
                        }
                )

                // Temperature Sign
                Text(
                    text = "\u00B0",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .constrainAs(temperatureSign) {
                            top.linkTo(temperatureText.top)
                            start.linkTo(temperatureText.end)
                        }
                        .padding(top = 5.sdp)
                )
                LoadImage(
                    imageUrl = cityWeatherModel.iconUrl,
                    contentDescription = cityWeatherModel.cityName,
                    modifier = Modifier
                        .constrainAs(weatherIconContainer) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .size(60.sdp),
                    placeholder = R.drawable.baseline_sunny_24,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchedCityCardPreview() {
    WeatherAppTheme {
        SearchedCityCard(
            cityWeatherModel = CityWeatherModel(
                iconUrl = "http://cdn.weatherapi.com/weather/64x64/day/143.png",
                cityName = "Lahore",
                temperature = "30",
                humidity = "20",
                ultraViolet = "10",
                temperatureFeelsLike = "30"
            )
        )
    }
}
