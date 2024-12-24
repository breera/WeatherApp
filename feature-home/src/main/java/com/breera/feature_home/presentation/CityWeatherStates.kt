package com.breera.feature_home.presentation

import com.breera.feature_home.domain.model.CityWeatherModel

/**
 * CityWeatherStates represents the state of the city weather screen.
 * It holds information about the current search query, search results,
 * loading status, error messages, and the current screen state.
 *
 * @property searchQuery The current search query entered by the user.
 * @property searchResult The result of the city weather search.
 * @property isLoading Indicates whether data is currently being loaded.
 * @property errorMessage An optional error message to display if an error occurs.
 * @property screenState The current state of the screen, represented by the ScreenState enum.
 */
data class CityWeatherStates(
    val searchQuery: String = "",
    val searchResult: CityWeatherModel = CityWeatherModel(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val screenState: ScreenState = ScreenState.LOADING
)

/**
 * ScreenState represents the different states the city weather screen can be in.
 */
enum class ScreenState {
    DEFAULT,            // The default state, typically when no search has been made.
    LOADING,            // Indicates that data is currently being loaded.
    ERROR,              // Indicates that an error has occurred.
    WEATHER_INFO_CARD,  // Displays a card with weather information.
    WEATHER_DETAIL      // Displays detailed weather information.
}