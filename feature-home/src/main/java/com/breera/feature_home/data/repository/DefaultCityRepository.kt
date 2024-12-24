package com.breera.feature_home.data.repository

import android.database.sqlite.SQLiteException
import com.breera.feature_home.data.network.RemoteCityDataSource
import com.breera.feature_home.data.network.mapper.toCityWeatherModel
import com.breera.feature_home.data.local.LocalCityDataSource
import com.breera.feature_home.domain.CityRepository
import com.breera.feature_home.domain.model.CityWeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.breera.core.domain.DataError
import com.breera.core.domain.EmptyResult
import com.breera.core.domain.Result
import com.breera.core.domain.map

/**
 * DefaultCityRepository is the implementation of the CityRepository interface.
 * It manages city weather data by interacting with both remote and local data sources.
 *
 * @property remoteCityDataSource The data source for fetching weather data from a remote API.
 * @property localCityDataSource The data source for managing local storage of city weather data.
 */
class DefaultCityRepository(
    private val remoteCityDataSource: RemoteCityDataSource,
    private val localCityDataSource: LocalCityDataSource,
) : CityRepository {

    /**
     * Searches for city weather data using a remote data source.
     *
     * @param query The city query string used to search for weather data.
     * @return A Result containing either the CityWeatherModel on success or a DataError.Remote on failure.
     */
    override suspend fun searchCity(
        query: String,
    ): Result<CityWeatherModel, DataError.Remote> {
        return remoteCityDataSource
            .getCityWeatherData(query)
            .map { dto ->
                dto.toCityWeatherModel()
            }
    }

    /**
     * Retrieves city weather data from local storage as a Flow.
     *
     * @return A Flow emitting the CityWeatherModel if found, or null otherwise.
     */
    override fun getLocalCityData(): Flow<CityWeatherModel?> = flow {
        val cities = localCityDataSource.getCity()
        emit(cities)
    }

    /**
     * Saves city weather data to local storage.
     *
     * @param cityWeatherModel The CityWeatherModel to be saved.
     * @return An EmptyResult indicating success or a DataError.Local on failure.
     */
    override fun saveLocalCityData(cityWeatherModel: CityWeatherModel): EmptyResult<DataError.Local> {
        return try {
            localCityDataSource.saveCity(cityWeatherModel)
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}