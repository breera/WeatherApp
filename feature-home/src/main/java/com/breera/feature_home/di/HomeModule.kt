package com.breera.feature_home.di

import com.breera.feature_home.data.local.DataStoreLocalCityDataSource
import com.breera.feature_home.data.local.LocalCityDataSource
import com.breera.feature_home.data.network.KtorRemoteCityDataSource
import com.breera.feature_home.data.network.RemoteCityDataSource
import com.breera.feature_home.data.repository.DefaultCityRepository
import com.breera.feature_home.domain.CityRepository
import com.breera.feature_home.presentation.CityWeatherVM
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    singleOf(::KtorRemoteCityDataSource).bind<RemoteCityDataSource>()
    single<CoroutineDispatcher> { Dispatchers.Default }
    singleOf(::DataStoreLocalCityDataSource).bind<LocalCityDataSource>()
    singleOf(::DefaultCityRepository).bind<CityRepository>()
    viewModelOf(::CityWeatherVM)
}