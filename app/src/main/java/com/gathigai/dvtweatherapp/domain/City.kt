package com.gathigai.dvtweatherapp.domain

import com.gathigai.dvtweatherapp.data.Coordinates
import com.gathigai.dvtweatherapp.data.database.models.CityEntity


data class City(
    var id: Long?,
    var isFavourite: Boolean?,
    var isCurrent: Boolean?,
    var name: String?,
    var coordinates: Coordinates?,
    var country: String?,
    var timezone: Int?,
    var sunrise: Int?,
    var sunset: Int?
) {
    constructor() :
            this(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
}

fun City.asDataModel() = CityEntity(
    isFavourite = isFavourite ?: false,
    isCurrent = isCurrent ?: false,
    name = name,
    coordinates = coordinates,
    country = country,
    timezone = timezone,
    sunrise = sunrise,
    sunset = sunset,
    apiId = null,
    population = null,
    id = id
)