package com.breera.feature_home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.breera.feature_home.R
import com.breera.theme.theme.WeatherAppTheme
import ir.kaaveh.sdpcompose.sdp

/**
 *
 * A card component to display weather information such as humidity, UV index, and feels-like temperature.
 *
 * @param modifier Modifier for styling the card.
 * @param humidityPercentage The humidity level as a percentage.
 * @param uv The UV index value.
 * @param feelsLike The feels-like temperature.
 *
 */

@Composable
fun WeatherInfoCardComposable(
    modifier: Modifier = Modifier,
    humidityPercentage: String?,
    uvIndex: String?,
    feelsLikeTemperature: String?
) {
    // Define constraints for the layout
    val constraints = ConstraintSet {
        val humidity = createRefFor("humidity")
        val uv = createRefFor("uv")
        val feelsLike = createRefFor("feelsLike")
        createHorizontalChain(humidity, uv, feelsLike, chainStyle = ChainStyle.SpreadInside)
    }

    // Card container
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
        shape = RoundedCornerShape(16.sdp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.sdp, horizontal = 20.sdp)
    ) {
        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp)
        ) {
            // Humidity Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.layoutId("humidity")
            ) {
                Text(
                    text = stringResource(R.string.humidity),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    modifier = Modifier.padding(top = 8.sdp),
                    text = "${humidityPercentage ?: "-"}%",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // UV Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.layoutId("uv")
            ) {
                Text(
                    text = stringResource(R.string.uv_index),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    modifier = Modifier.padding(top = 8.sdp),
                    text = uvIndex ?: "-",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Feels Like Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.layoutId("feelsLike")
            ) {
                Text(
                    text = stringResource(R.string.feels_like),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    modifier = Modifier.padding(top = 8.sdp),
                    text = "$feelsLikeTemperature\u00B0",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherInfoCardPreview() {
    WeatherAppTheme {
        WeatherInfoCardComposable(
            modifier = Modifier,
            humidityPercentage = "20",
            uvIndex = "4",
            feelsLikeTemperature = "38"
        )
    }
}
