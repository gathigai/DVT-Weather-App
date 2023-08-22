package com.gathigai.dvtweatherapp.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gathigai.dvtweatherapp.data.Coordinates
import com.gathigai.dvtweatherapp.domain.City
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val isFavourite: Boolean?,
    @ColumnInfo(name = "is_current")
    val isCurrent: Boolean?,
    val apiId: Int?,
    val name: String?,
    @Embedded(prefix = "city")
    val coordinates: Coordinates?,
    val country: String?,
    val population: Int?,
    val timezone: Int?,
    val sunrise: Int?,
    val sunset: Int?
)

fun CityEntity?.asDomainModel() = City(
    id = this?.id,
    isFavourite = this?.isFavourite,
    isCurrent = this?.isCurrent,
    name = this?.name,
    coordinates = this?.coordinates,
    country = this?.country,
    timezone = this?.timezone,
    sunrise = this?.sunrise,
    sunset = this?.sunset
)