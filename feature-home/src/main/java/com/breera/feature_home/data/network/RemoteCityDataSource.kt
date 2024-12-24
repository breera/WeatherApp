package com.breera.feature_home.data.network

import com.breera.feature_home.data.dto.CurrentWeatherDto
import com.breera.core.domain.DataError
import com.breera.core.domain.Result

/**
 * RemoteCityDataSource defines the contract for fetching city weather data
 * from a remote source. Implementations of this interface are responsible
 * for making network requests to retrieve weather information.
 */
interface RemoteCityDataSource {

    /**
     * Fetches weather data for a given city query from a remote source.
     *
     * @param query The city query string used to search for weather data.
     * @return A Result containing either the CurrentWeatherDto on success or a DataError.Remote on failure.
     */
    suspend fun getCityWeatherData(
        query: String
    ): Result<CurrentWeatherDto, DataError.Remote>
}