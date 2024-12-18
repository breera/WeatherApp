package com.breera.weatherapp.di

import com.breera.core.di.coreModule
import com.breera.feature_home.di.homeModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config : KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(coreModule)
        modules(homeModule)
    }
}
