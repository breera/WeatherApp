package com.breera.weatherapp

import android.app.Application
import com.breera.weatherapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

/**
 * The WeatherApplication class serves as the entry point for the application.
 * It extends the Android Application class and is responsible for initializing
 * global configurations and dependencies.
 */

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Koin for dependency injection
        initKoin {
            // Set up Android-specific logging for Koin
            androidLogger()
            // Provide the application context to Koin
            androidContext(this@WeatherApplication)
        }
    }
}