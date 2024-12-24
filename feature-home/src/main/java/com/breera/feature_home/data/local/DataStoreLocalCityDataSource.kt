package com.breera.feature_home.data.local

import com.breera.core.data.local.DataStorePref
import com.breera.feature_home.domain.model.CityWeatherModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * DataStoreLocalCityDataSource is responsible for handling local storage operations
 * related to city weather data using a DataStorePref implementation.
 *
 * @property dataStore The DataStorePref instance used for storing and retrieving data.
 * @property dispatcher The CoroutineDispatcher used for executing coroutines.
 */
class DataStoreLocalCityDataSource(
    private val dataStore: DataStorePref,
    dispatcher: CoroutineDispatcher
) : LocalCityDataSource {

    // CoroutineScope for launching coroutines with the provided dispatcher and NonCancellable context
    private val scope: CoroutineScope = CoroutineScope(dispatcher + NonCancellable)

    /**
     * Saves the given CityWeatherModel to local storage.
     *
     * @param cityWeatherModel The CityWeatherModel to be saved.
     */
    override fun saveCity(cityWeatherModel: CityWeatherModel) {
        scope.launch {
            dataStore.putObject(KEY_CITY_DATA, cityWeatherModel, CityWeatherModel::class)
        }
    }

    /**
     * Retrieves the CityWeatherModel from local storage.
     *
     * @return The CityWeatherModel if found, or null otherwise.
     */
    override suspend fun getCity(): CityWeatherModel? =
        withContext(scope.coroutineContext) {
            dataStore.getObject(
                KEY_CITY_DATA,
                CityWeatherModel::class
            )
        }

    // Companion object to hold constant keys used for data storage
    internal companion object {
        private const val KEY_CITY_DATA = "key_city_data" // Key for storing city data
    }
}