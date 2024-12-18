# WeatherApp 📱☁️

Project Overview 🌦️

WeatherApp is designed with scalability and maintainability in mind. It is divided into multiple feature and core modules, encouraging a modularized and testable codebase.

## Features

- **Search for City Weather**: Enter a city name to get current weather information, including temperature, humidity, and more.
- **Detailed Weather View**: Dive into detailed weather data with an intuitive interface.
- **Local Data Storage**: Save and retrieve city weather data locally for offline access.
- **Modern UI**: Enjoy a clean and responsive design powered by Jetpack Compose.

## Architecture

The app follows a modular architecture pattern, separating concerns into distinct layers:

- **Presentation Layer**: Handles UI components using Jetpack Compose.
- **Domain Layer**: Contains business logic and repository interfaces.
- **Data Layer**: Manages data sources, including remote API calls and local storage.

## Tech Stack 🛠️

- **Kotlin**: The primary language for app development.
- **Jetpack Compose**: For building the UI.
- **Ktor**: For making network requests.
- **Koin**: For dependency injection.
- **Coroutines and Flow**: For asynchronous programming and state management.

## Application Heirarchy

app/                        # App module (Entry point)
├── build.gradle.kts
├── core/                   # Core module (Shared logic and utilities)
│   ├── manifests/
│   ├── kotlin+java/
│   │   └── com.breera.core
│   │       ├── data/       # Data layer
│   │       ├── di/         # Dependency injection
│   │       ├── domain/     # Business logic
│   │       └── presentation/ # UI shared components
│   ├── res/
│ 
│
├── feature-home/           # Home feature module
│   ├── manifests/
│   ├── kotlin+java/
│   │   └── com.breera.feature_home
│   │       ├── data/
│   │       │   ├── dto/    # Data Transfer Objects
│   │       │   ├── local/  # Local data sources
│   │       │   ├── network/# API calls and remote sources
│   │       │   └── repository/ # Repositories for data access
│   │       ├── di/         # Dependency injection
│   │       ├── domain/     # Business logic
│   │       └── presentation/
│   │           ├── component/  # UI components
│   │           ├── CityWeatherAction.kt
│   │           ├── CityWeatherScreen.kt
│   │           ├── CityWeatherStates.kt
│   │           └── CityWeatherVM.kt  # ViewModel
│   ├── res/
│   └── test/               # Unit tests for feature-home
│
├── theme/                  # Theme module (UI styles and design system)
│   ├── manifests/
│   ├── kotlin+java/
│   │   └── com.breera.theme.theme
│   │       ├── Color.kt    # Color palette definitions
│   │       ├── Theme.kt    # Theme definitions
│   │       └── Type.kt     # Typography definitions
│   ├── res/
│
│
└── settings.gradle.kts     # Includes modules in the project

  
