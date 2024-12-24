package com.breera.feature_home.data.network.mapper

import com.breera.feature_home.data.dto.CurrentWeatherDto
import com.breera.feature_home.domain.model.CityWeatherModel

fun CurrentWeatherDto.toCityWeatherModel(): CityWeatherModel {
    return CityWeatherModel(
        iconUrl = addHttpsToUrl(current?.condition?.icon),
        cityName = location?.name,
        temperature = current?.tempC.toString(),
        humidity = current?.humidity.toString(),
        ultraViolet = current?.uv.toString(),
        temperatureFeelsLike = current?.feelslikeC.toString()
    )
}


fun addHttpsToUrl(url: String?): String? {
    return if (url?.startsWith("//") == true) {
        "https:$url"
    } else {
        url // If the protocol is already there, return as is
    }
}

