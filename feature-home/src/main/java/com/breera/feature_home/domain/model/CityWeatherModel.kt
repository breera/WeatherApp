package com.breera.feature_home.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CityWeatherModel(
    val iconUrl: String? = null,
    val cityName: String? = null,
    val temperature: String? = null,
    val humidity: String? = null,
    val ultraViolet: String? = null,
    val temperatureFeelsLike: String? = null
)
