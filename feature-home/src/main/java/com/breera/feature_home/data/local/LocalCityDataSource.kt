package com.breera.feature_home.data.local

import com.breera.feature_home.domain.model.CityWeatherModel

/**
 * LocalCityDataSource defines the contract for local data operations
 * related to city weather data. Implementations of this interface
 * are responsible for storing and retrieving city weather information.
 */
interface LocalCityDataSource {

    /**
     * Saves the given CityWeatherModel to local storage.
     *
     * @param cityWeatherModel The CityWeatherModel to be saved.
     */
    fun saveCity(cityWeatherModel: CityWeatherModel)

    /**
     * Retrieves the CityWeatherModel from local storage.
     *
     * @return The CityWeatherModel if found, or null otherwise.
     */
    suspend fun getCity(): CityWeatherModel?
}