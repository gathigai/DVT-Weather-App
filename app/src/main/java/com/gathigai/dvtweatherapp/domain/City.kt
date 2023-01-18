package com.gathigai.dvtweatherapp.domain

import com.gathigai.dvtweatherapp.data.Coordinates
import com.gathigai.dvtweatherapp.data.database.models.CityEntity
import com.google.gson.annotations.SerializedName


data class City(
    val id: Long?,
    val isFavourite: Boolean?,
    val isCurrent: Boolean?,
    val name: String?,
    val coordinates: Coordinates?,
    val country: String?,
    val timezone: Int?,
    val sunrise: Int?,
    val sunset: Int?
)

fun City.asDataModel() = CityEntity(
    isFavourite = isFavourite?:false,
    isCurrent = isCurrent?:false,
    name = name,
    coordinates = coordinates,
    country = country,
    timezone = timezone,
    sunrise = sunrise,
    sunset = sunset,
    apiId = null,
    population = null
)