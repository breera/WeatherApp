package com.breera.feature_home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    @SerialName("current")
    var current: Current? = Current(),
    @SerialName("location")
    var location: Location? = Location()
)

@Serializable
data class Current(
    @SerialName("cloud")
    var cloud: Int? = 0,
    @SerialName("condition")
    var condition: Condition? = Condition(),
    @SerialName("dewpoint_c")
    var dewpointC: Double? = 0.0,
    @SerialName("dewpoint_f")
    var dewpointF: Double? = 0.0,
    @SerialName("feelslike_c")
    var feelslikeC: Double? = 0.0,
    @SerialName("feelslike_f")
    var feelslikeF: Double? = 0.0,
    @SerialName("gust_kph")
    var gustKph: Double? = 0.0,
    @SerialName("gust_mph")
    var gustMph: Double? = 0.0,
    @SerialName("heatindex_c")
    var heatindexC: Double? = 0.0,
    @SerialName("heatindex_f")
    var heatindexF: Double? = 0.0,
    @SerialName("humidity")
    var humidity: Int? = 0,
    @SerialName("is_day")
    var isDay: Int? = 0,
    @SerialName("last_updated")
    var lastUpdated: String? = "",
    @SerialName("last_updated_epoch")
    var lastUpdatedEpoch: Int? = 0,
    @SerialName("precip_in")
    var precipIn: Double? = 0.0,
    @SerialName("precip_mm")
    var precipMm: Double? = 0.0,
    @SerialName("pressure_in")
    var pressureIn: Double? = 0.0,
    @SerialName("pressure_mb")
    var pressureMb: Double? = 0.0,
    @SerialName("temp_c")
    var tempC: Double? = 0.0,
    @SerialName("temp_f")
    var tempF: Double? = 0.0,
    @SerialName("uv")
    var uv: Double? = 0.0,
    @SerialName("vis_km")
    var visKm: Double? = 0.0,
    @SerialName("vis_miles")
    var visMiles: Double? = 0.0,
    @SerialName("wind_degree")
    var windDegree: Int? = 0,
    @SerialName("wind_dir")
    var windDir: String? = "",
    @SerialName("wind_kph")
    var windKph: Double? = 0.0,
    @SerialName("wind_mph")
    var windMph: Double? = 0.0,
    @SerialName("windchill_c")
    var windchillC: Double? = 0.0,
    @SerialName("windchill_f")
    var windchillF: Double? = 0.0
)

@Serializable
data class Condition(
    @SerialName("code")
    var code: Int? = 0,
    @SerialName("icon")
    var icon: String? = "",
    @SerialName("text")
    var text: String? = ""
)

@Serializable
data class Location(
    @SerialName("country")
    var country: String? = "",
    @SerialName("lat")
    var lat: Double? = 0.0,
    @SerialName("localtime")
    var localtime: String? = "",
    @SerialName("localtime_epoch")
    var localtimeEpoch: Int? = 0,
    @SerialName("lon")
    var lon: Double? = 0.0,
    @SerialName("name")
    var name: String? = "",
    @SerialName("region")
    var region: String? = "",
    @SerialName("tz_id")
    var tzId: String? = ""
)
