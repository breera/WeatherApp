package com.breera.feature_home.presentation

import com.breera.feature_home.domain.model.CityWeatherModel

/**
 * CityWeatherAction represents the different actions or events
 * that can occur in the city weather presentation layer.
 * It is a sealed interface, allowing for a fixed set of action types.
 */
sealed interface CityWeatherAction {

    /**
     * Action representing a change in the search query.
     *
     * @property query The new search query entered by the user.
     */
    data class OnSearchQueryChange(val query: String) : CityWeatherAction

    /**
     * Action representing a request for detailed weather information.
     *
     * @property cityWeatherModel The CityWeatherModel containing detailed weather data.
     */
    data class OnDetailWeatherRequest(val cityWeatherModel: CityWeatherModel) : CityWeatherAction
}