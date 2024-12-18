@file:OptIn(FlowPreview::class)

package com.breera.feature_home.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breera.core.domain.onError
import com.breera.core.domain.onSuccess
import com.breera.feature_home.domain.CityRepository
import com.breera.feature_home.domain.model.CityWeatherModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * CityWeatherVM is the ViewModel responsible for managing the state and business logic
 * of the city weather screen. It interacts with the CityRepository to fetch and store data.
 *
 * @property repository The CityRepository instance used for data operations.
 */
class CityWeatherVM(private val repository: CityRepository) : ViewModel() {
    private var searchJob: Job? = null

    // StateFlow to hold the current state of the city weather screen
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val _state = MutableStateFlow(CityWeatherStates())
    val state = _state.onStart {
        observeCityLocalStatus()
        observeSearchQuery()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
    )

    /**
     * Handles actions dispatched from the UI.
     *
     * @param action The CityWeatherAction to be handled.
     */
    fun onAction(action: CityWeatherAction) {
        when (action) {
            is CityWeatherAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is CityWeatherAction.OnDetailWeatherRequest -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        screenState = ScreenState.WEATHER_DETAIL,
                        searchResult = action.cityWeatherModel
                    )
                }
                repository.saveLocalCityData(action.cityWeatherModel)
            }
        }
    }

    /**
     * Observes changes in the search query and triggers search operations.
     */
    private suspend fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged() // Avoid duplicate API calls for the same query
            .debounce(1000L) // Debounce to wait for typing to stop
            .onEach {
                when {
                    it.isBlank() -> {
                        _state.update { state ->
                            state.copy(errorMessage = null)
                        }
                    }

                    it.length > 2 -> {
                        searchJob?.cancel()
                        searchJob = searchCity(it)
                    }
                }
            }.launchIn(viewModelScope)
    }

    /**
     * Initiates a search for city weather data based on the query.
     *
     * @param query The search query string.
     */
    private suspend fun searchCity(query: String) =
        viewModelScope.launch {
            _state.update {
                it.copy(screenState = ScreenState.LOADING)
            }
            repository.searchCity(query)
                .onSuccess { searchResult ->
                    _state.update {
                        it.copy(
                            screenState = ScreenState.WEATHER_INFO_CARD,
                            errorMessage = null,
                            searchResult = searchResult
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            screenState = ScreenState.ERROR,
                            errorMessage = error.toString(),
                            searchResult = CityWeatherModel()
                        )
                    }
                }
        }

    /**
     * Observes the local city data status and updates the state accordingly.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun observeCityLocalStatus() {
        repository.getLocalCityData()
            .onEach { city ->
                city?.let {
                    _state.update { state ->
                        state.copy(
                            searchResult = city,
                            screenState = ScreenState.WEATHER_DETAIL
                        )
                    }
                } ?: run {
                    _state.update {
                        it.copy(screenState = ScreenState.DEFAULT)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}