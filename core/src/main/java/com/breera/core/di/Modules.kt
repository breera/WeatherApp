package com.breera.core.di

import com.breera.core.data.local.DataStorePref
import com.breera.core.data.local.DataStorePrefImpl
import com.breera.core.data.local.getDataStore
import com.breera.core.data.remote.HttpClientFactory
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    single<DataStorePref> { DataStorePrefImpl(get()) }
    single { getDataStore(androidContext()) }

}