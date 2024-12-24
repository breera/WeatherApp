package com.breera.feature_home.data.network

import com.breera.core.data.remote.safeCall
import com.breera.core.domain.Result
import com.breera.feature_home.BuildConfig
import com.breera.feature_home.data.dto.CurrentWeatherDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import com.breera.core.domain.DataError

/**
 * KtorRemoteCityDataSource is responsible for fetching city weather data
 * from a remote API using the Ktor HTTP client.
 *
 * @property httpClient The HttpClient instance used for making network requests.
 */
class KtorRemoteCityDataSource(private val httpClient: HttpClient) : RemoteCityDataSource {

    /**
     * Fetches weather data for a given city query from the remote API.
     *
     * @param query The city query string used to search for weather data.
     * @return A Result containing either the CurrentWeatherDto on success or a DataError.Remote on failure.
     */
    override suspend fun getCityWeatherData(
        query: String
    ): Result<CurrentWeatherDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${BuildConfig.BASE_URL}/current.json?q=$query&key=${BuildConfig.API_KEY}")
        }
    }
}