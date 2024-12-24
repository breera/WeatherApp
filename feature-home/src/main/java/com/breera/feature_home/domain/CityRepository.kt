package com.breera.feature_home.domain

import com.breera.core.domain.DataError
import com.breera.core.domain.EmptyResult
import com.breera.core.domain.Result
import com.breera.feature_home.domain.model.CityWeatherModel
import kotlinx.coroutines.flow.Flow

/**
 * CityRepository defines the contract for managing city weather data.
 * Implementations of this interface are responsible for handling operations
 * related to searching, retrieving, and saving city weather information.
 */
interface CityRepository {

    /**
     * Searches for city weather data using a remote data source.
     *
     * @param query The city query string used to search for weather data.
     * @return A Result containing either the CityWeatherModel on success or a DataError.Remote on failure.
     */
    suspend fun searchCity(
        query: String,
    ): Result<CityWeatherModel, DataError.Remote>

    /**
     * Retrieves city weather data from local storage as a Flow.
     *
     * @return A Flow emitting the CityWeatherModel if found, or null otherwise.
     */
    fun getLocalCityData(): Flow<CityWeatherModel?>

    /**
     * Saves city weather data to local storage.
     *
     * @param cityWeatherModel The CityWeatherModel to be saved.
     * @return An EmptyResult indicating success or a DataError.Local on failure.
     */
    fun saveLocalCityData(cityWeatherModel: CityWeatherModel): EmptyResult<DataError.Local>
}